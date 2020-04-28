package Guo.DevnetTest;

import org.iota.jota.IotaAPI;
import org.iota.jota.IotaAPICommand;
import org.iota.jota.IotaPoW;
import org.iota.jota.dto.request.IotaAttachToTangleRequest;
import org.iota.jota.dto.response.*;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Input;
import org.iota.jota.model.Transaction;
import org.iota.jota.model.Transfer;
import org.iota.jota.pow.SpongeFactory;
import org.iota.jota.utils.InputValidator;
import org.iota.jota.utils.StopWatch;

import java.util.ArrayList;
import java.util.List;

import static org.iota.jota.utils.Constants.INVALID_HASHES_INPUT_ERROR;
import static org.iota.jota.utils.Constants.INVALID_TRYTES_INPUT_ERROR;

/**
 * 此类主要包含一些测试方法
 */
public class testTransferTime {
    public static void main(String []args){

    }


    /**
     * 查看单纯的节点选择需要花费多少时间
     * @param sendSeed
     * @param reciveSeed
     * @param numOfAddress
     */
    public static void testTimeByTipSelection(String sendSeed,String reciveSeed,int numOfAddress){
        //得到接收token的address
        List<String> list = generateTestAddress.generateTenAddressOfSeed(reciveSeed,numOfAddress);
        System.out.println("-----------开始TipSelection时间测试---------");
        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

        int depth = 3;
        int minimumWeightMagnitude = 10;
        int securityLevel = 2;

        //为了方便，我们每次就传一个
        int value = 1;

        //设置一个交易集
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        //把东西搞好
        for (int i = 0;i < list.size();i++){
            Transfer Transaction = new Transfer(list.get(i), value);
            transfers.add(Transaction);
        }

        System.out.println("-----------开始测试tipSelection时间---------");
        try {

            //进行api.gettips
            GetTipsResponse getTipsResponse = api.getTips();

            System.out.println("Doing Tip Selection costs " +  getTipsResponse.getDuration() + "ms");
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }

        System.out.println("-----------TipSelection测试结束---------");
    }


    /**
     *
     * 根据一个bundle中address的个数不同来看看pow，valid，tip selecttion的时间占比
     * sendseed代表发送方，我会先通过官方提供的渠道给这个发送方一定数量的token，然后让他平均发给这些地址
     * @param sendSeed
     * @param reciveSeed
     * @param numOfAddress
     */
    public static void testTimeByTransfer(String sendSeed,String reciveSeed,int numOfAddress){
        //得到接收token的address
        List<String> list = generateTestAddress.generateTenAddressOfSeed(reciveSeed,numOfAddress);

        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();


        //为了方便，我们每次就传一个
        int value = 1;

        int depth = 3;
        int minimumWeightMagnitude = 10;
        int securityLevel = 2;

        //设置一个交易集
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        //把东西搞好
        for (int i = 0;i < list.size();i++){
            Transfer Transaction = new Transfer(list.get(i), value);
            transfers.add(Transaction);
        }

        System.out.println("-----------开始交易---------");

        try {
            System.out.println("------Sending 1 i to every address!-------");


            //这个sendTransfer负责的很多tip selection / remote proof of work / and sending the bundle to the node
            SendTransferResponse response = testTransferTime.mySendTransfer(api,sendSeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);
            System.out.println(response.getTransactions());
            System.out.println("all process cost " + response.getDuration() + "ms");
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------交易完成---------");

    }

    /**
     * 将IotaAPI中的方法提取出来
     * @param api
     * @param seed
     * @param security
     * @param depth
     * @param minWeightMagnitude
     * @param transfers
     * @param inputs
     * @param remainderAddress
     * @param validateInputs
     * @param validateInputAddresses
     * @param tips
     * @return
     * @throws ArgumentException
     */
    public static SendTransferResponse mySendTransfer(IotaAPI api, String seed, int security, int depth, int minWeightMagnitude,
                                                      List<Transfer> transfers, List<Input> inputs, String remainderAddress,
                                                      boolean validateInputs, boolean validateInputAddresses,
                                                      List<Transaction> tips) throws ArgumentException {

        StopWatch stopWatch = new StopWatch();

        //做前提准备，把交易转化为bundle然后转化为trytes
        long time1 = System.currentTimeMillis();
        List<String> trytes = api.prepareTransfers(seed, security, transfers, remainderAddress, inputs, tips, validateInputs);
        long time2 = System.currentTimeMillis();
        System.out.println("prepare bundle cost "+(time2-time1) +" ms");

        if (validateInputAddresses) {
            api.validateTransfersAddresses(seed, security, trytes);
        }

        String reference = tips != null && tips.size() > 0 ? tips.get(0).getHash(): null;

        //执行自己的方法方便输出那些东西的时间
        List<Transaction> trxs = testTransferTime.mySendTrytes(api,trytes.toArray(new String[0]), depth, minWeightMagnitude, reference);

        Boolean[] successful = new Boolean[trxs.size()];

        for (int i = 0; i < trxs.size(); i++) {
            final FindTransactionResponse response = api.findTransactionsByBundles(trxs.get(i).getBundle());
            successful[i] = response.getHashes().length != 0;
        }

        return SendTransferResponse.create(trxs, successful, stopWatch.getElapsedTimeMili());
    }

    public static List<Transaction> mySendTrytes(IotaAPI api,String[] trytes, int depth, int minWeightMagnitude, String reference) throws ArgumentException {
        GetTransactionsToApproveResponse txs = api.getTransactionsToApprove(depth, reference);
        System.out.println("Getting trunk/branch transation(TipSelection) and approve tip costs "+ txs.getDuration() + "ms");

        // attach to tangle - do pow
        GetAttachToTangleResponse res = api.attachToTangle(txs.getTrunkTransaction(), txs.getBranchTransaction(), minWeightMagnitude, trytes);
        System.out.println("remote Pow costs " + res.getDuration() +"ms");

        try {
            api.storeAndBroadcast(res.getTrytes());
        } catch (ArgumentException e) {
            return new ArrayList<>();
        }

        final List<Transaction> trx = new ArrayList<>();

        for (String tryte : res.getTrytes()) {
            trx.add(new Transaction(tryte, SpongeFactory.create(SpongeFactory.Mode.CURL_P81)));
        }

        return trx;
    }



}
