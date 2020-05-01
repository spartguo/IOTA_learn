package Guo.Common;

import org.iota.jota.IotaAPI;
import org.iota.jota.IotaPoW;
import org.iota.jota.builder.ApiBuilder;
import org.iota.jota.dto.response.*;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Input;
import org.iota.jota.model.Transaction;
import org.iota.jota.model.Transfer;
import org.iota.jota.pow.SpongeFactory;
import org.iota.jota.utils.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类主要包含一些测试的方法
 */
public class testTransferTime {
    public static void main(String []args){

    }


    /**
     * 得到连接状态
     * @param protocol
     * @param node
     * @param port
     * @return
     */
    public static IotaAPI getApi(String protocol,String node,int port){
        System.out.println("-----------连接节点---------");
        IotaAPI api = new IotaAPI.Builder()
                .protocol(protocol)
                .host(node)
                .port(port)
                .build();
        return api;
    }


    /**
     * 得到一个seed的含有的token数量
     * @param seed
     * @param protocol
     * @param node
     * @param port
     */
    public static void getBalanceOfSeed(String seed,String protocol,String node,int port){

        System.out.println("-----------开始连接节点---------");
        IotaAPI api = getApi(protocol,node,port);

        String address = generateTestAddress.generateNumsOfAddressOfSeed(protocol,node,port,seed,1).get(0);

        System.out.println("值为 " + api.getBalance(0,address) + " token");
    }


    /**
     * ---------此处调用的TipSelectionAPI---------
     * 查看单纯的节点选择需要花费多少时间
     * tip选择和address无关
     *
     * @param protocol
     * @param node
     * @param port
     */
    public static void testTimeByTipSelection(String protocol,String node,int port){


        System.out.println("-----------开始TipSelection时间测试---------");

        IotaAPI api = getApi(protocol,node,port);


        System.out.println("-----------开始测试tipSelection时间---------");
        try {
            //测试100组数据
            for (int i = 0;i< 100;i++) {
                //进行api.gettips
                GetTipsResponse getTipsResponse = api.getTips();

                System.out.println("Doing Tip Selection costs " + getTipsResponse.getDuration() + " ms");
            }
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }

        System.out.println("-----------TipSelection测试结束---------");
    }

    /**
     *--------测试单个交易执行时间------不需要输入发送address的版本------
     *本方法主要是测试单个交易测试时间
     * 输入：连接的节点信息、要发送是seed，要接受的seed，bundle中交易的数量
     * @param protocol
     * @param node
     * @param port
     * @param sendSeed
     * @param reciveSeed
     * @param numOfAddress
     */
    public static void testTimeByTransferNoInput(String protocol,String node,int port,String sendSeed,String reciveSeed,int numOfAddress){

        //得到接收token的address
        List<String> list = generateTestAddress.generateNumsOfAddressOfSeed(protocol,node,port,reciveSeed,numOfAddress);

        IotaAPI api = getApi(protocol,node,port);


        //为了方便，我们每次就传一个
        int value = 0;

        int depth = 3;
        int minimumWeightMagnitude =9;
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
            System.out.println("------Sending 1 i to a address!-------");

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
     *--------测试单个交易执行时间-------
     *本方法主要是测试单个交易测试时间
     * 输入：连接的节点信息、要发送是seed，要接受的seed，bundle中交易的数量、发送放seed的一个有token的地址
     *
     * 根据一个bundle中address的个数不同来看看pow，valid，tip selecttion的时间占比
     * sendseed代表发送方，我会先通过官方提供的渠道给这个发送方一定数量的token，然后让他平均发给这些地址
     * @param sendSeed
     * @param reciveSeed
     * @param numOfAddress
     */
    public static void testTimeByTransfer(String protocol,String node,int port,String sendSeed,String reciveSeed,int numOfAddress,List<Input> inputs){

        //得到接收token的address
        List<String> list = generateTestAddress.generateNumsOfAddressOfSeed(protocol,node,port,reciveSeed,numOfAddress);

        IotaAPI api = getApi(protocol,node,port);


        //为了方便，我们每次就传一个
        int value = 0;

        int depth = 3;
        int minimumWeightMagnitude = 9;
        int securityLevel = 1;

        //设置一个交易集
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        //把东西搞好
        for (int i = 0;i < list.size();i++){
            Transfer Transaction = new Transfer(list.get(i), value);
            transfers.add(Transaction);
        }

        System.out.println("-----------开始交易---------");

        try {
            System.out.println("------Sending 1 i to a address!-------");

            //这个sendTransfer负责的很多tip selection / remote proof of work / and sending the bundle to the node
            SendTransferResponse response = testTransferTime.mySendTransfer(api,sendSeed, securityLevel, depth, minimumWeightMagnitude, transfers, inputs, null, false, false, null);
            System.out.println(response.getTransactions());
            System.out.println("all process cost " + response.getDuration() + "ms");
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------交易完成---------");

    }

    /**
     * 将IotaAPI中的进行单次交易的方法提取出来,方便我自己加东西
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

    /**
     * 将得到的trytes进行getTipSelection操作，Pow，然后获得交易成功的bundle内容
     * @param api
     * @param trytes
     * @param depth
     * @param minWeightMagnitude
     * @param reference
     * @return
     * @throws ArgumentException
     */
    public static List<Transaction> mySendTrytes(IotaAPI api,String[] trytes, int depth, int minWeightMagnitude, String reference) throws ArgumentException {
        long tt1 = System.currentTimeMillis();
        GetTransactionsToApproveResponse txs = api.getTransactionsToApprove(depth, reference);
        long tt2 = System.currentTimeMillis();
        System.out.println("duration = "+ txs.getDuration() + "ms");
        System.out.println("Getting trunk/branch transation(TipSelection) and approve tip costs "+ (tt2-tt1) + " ms");

        // attach to tangle - do pow
        tt1 = System.currentTimeMillis();
        GetAttachToTangleResponse res = api.attachToTangle(txs.getTrunkTransaction(), txs.getBranchTransaction(),minWeightMagnitude , trytes);
        tt2 = System.currentTimeMillis();
        System.out.println("duration " + res.getDuration() +" ms");
        System.out.println("remote Pow costs " + (tt2-tt1) +" ms");

        try {
            //将得到的trytes发送节点
            api.storeAndBroadcast(res.getTrytes());
        } catch (ArgumentException e) {
            return new ArrayList<>();
        }

        //设置一个list用来放一个bundle中的交易
        final List<Transaction> trx = new ArrayList<>();

        //加入bundle中的交易
        for (String tryte : res.getTrytes()) {
            trx.add(new Transaction(tryte, SpongeFactory.create(SpongeFactory.Mode.CURL_P81)));
        }

        return trx;
    }



}
