package Guo.javaGuide;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;

import java.util.ArrayList;

public class transferTestToken {
    public static void main(String []args){
        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

        int depth = 3;
        int minimumWeightMagnitude = 10;
        int securityLevel = 2;

        //有钱的地址的seed，人家会通过seed来看你哪个地址有钱,这个是测试seed1
        String mySeed = "XHSY9DGBABQUZWGX9XNUXVWTBBYZRJFSOXXRIXYRPRMPKXDTKMGMJSUX9SWRJTWWZNWAYSILLRXGFUJW9";

        //要发送的地址，这里是seed2生成的地址
        String address = "UNVHADHYCFU9YFGFQOLFCXNTQVVLIKQYXRLLBELZSAYCOCVKHOBDOUWSJ9GEYRRRORVJM9RYXANAQLLX9A9GRAJEPZ";

        int value = 1;

        Transfer Transaction = new Transfer(address, value);

        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        transfers.add(Transaction);

        System.out.println("-----------开始交易---------");

        try {
            System.out.printf("Sending 1 i to %s\n", address);

            //这个sendTransfer负责的很多tip selection / remote proof of work / and sending the bundle to the node
            SendTransferResponse response = api.sendTransfer(mySeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);
            System.out.println(response.getTransactions());
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------交易完成---------");

    }
}
