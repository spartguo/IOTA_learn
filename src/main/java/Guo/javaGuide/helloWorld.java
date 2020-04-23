package Guo.javaGuide;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;
import org.iota.jota.utils.TrytesConverter;

import java.util.ArrayList;

public class helloWorld {
    public static void main(String []args) throws ArgumentException {

        System.out.println("-----------连接节点---------");
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.devnet.thetangle.org")
                .port(443)
                .build();

        //获得节点信息
        GetNodeInfoResponse response = api.getNodeInfo();
        System.out.println(response);

        System.out.println("----------做各种准备工作---------");

        //设置节点信息
        int depth = 3;
        int minimumWeightMagnitude = 9;

        //想要发送的账户地址
        String address = "ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDXNYVAPLZAW";

        //自己生成一个种子作为发送方
        String myRandomSeed = SeedRandomGenerator.generateNewSeed();

        int securityLevel = 2;

        //吧自己要发送的信息转化为Trytes格式
        //这个函数只支持普通的ascll，不支持其他的什么带几声几声符号的那种字母
        String message = TrytesConverter.asciiToTrytes("Hello world");

        //todo
        //这个我也不知道为啥要
        String tag = "HELLOWORLD";

        //传输的价值
        int value = 0;

        //创建交易对象
        Transfer zeroValueTransaction = new Transfer(address, value, message, tag);

        //搞个list然后把transfer加入，搞list好像是为了发送多个交易，传入的函数sendTansfer的参数就是这么限定的
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        transfers.add(zeroValueTransaction);

        System.out.println("----------准备完成，开始发送0值交易---------");

        try {
            //开始发送交易
            SendTransferResponse transferResponse = api.sendTransfer(myRandomSeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);
            System.out.println("----------输出交易信息---------");
            System.out.println(transferResponse.getTransactions());
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("----------完成---------");

    }
}
