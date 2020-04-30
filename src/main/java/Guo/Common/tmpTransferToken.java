package Guo.Common;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;

import java.util.ArrayList;

public class tmpTransferToken {
    public static void main(String []args){}

    public static void tmpTransferTokenZeroValue(String protocol,String node,int port,String sendSeed,String reciveAddress){
        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol(protocol)
                .host(node)
                .port(port)
                .build();

        int depth = 3;
        int minimumWeightMagnitude = 9;
        //private的安全级别搞低一点
        int securityLevel = 1;

        //有钱的地址的seed，人家会通过seed来看看哪里有钱
        String mySeed = sendSeed;

        //要发送的地址，即将要发送的地址
        String address = reciveAddress;

        int value = 0;

        Transfer Transaction = new Transfer(address, value);

        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        transfers.add(Transaction);

        System.out.println("-----------开始交易---------");

        try {
            System.out.printf("Sending  i to %s\n", address);

            //这个sendTransfer负责的很多tip selection / remote proof of work / and sending the bundle to the node
            SendTransferResponse response = api.sendTransfer(mySeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);
            System.out.println(response.getTransactions());
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------交易完成---------");
    }

    public static void tmpTransferTokenByAmount(String protocol,String node,int port,String sendSeed,String reciveAddress,int numsOfToken){
        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol(protocol)
                .host(node)
                .port(port)
                .build();

        int depth = 3;
        int minimumWeightMagnitude = 9;
        //private的安全级别搞低一点
        int securityLevel = 1;

        //有钱的地址的seed，人家会通过seed来看看哪里有钱
        String mySeed = sendSeed;

        //要发送的地址，即将要发送的地址
        String address = reciveAddress;

        int value = numsOfToken;

        Transfer Transaction = new Transfer(address, value);

        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        transfers.add(Transaction);

        System.out.println("-----------开始交易---------");

        try {
            System.out.printf("Sending %d i to %s\n",numsOfToken, address);

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
