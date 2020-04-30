package Guo.PrivateTangleTest;

import Guo.Common.generateTestAddress;
import Guo.Common.testTransferTime;
import Guo.Common.tmpTransferToken;
import org.iota.jota.IotaAPI;
import org.iota.jota.IotaPoW;
import org.iota.jota.dto.response.GetTransactionsToApproveResponse;
import org.iota.jota.model.Input;

import java.util.ArrayList;
import java.util.List;

public class privateTest {
    //
    public static void main(String []args){

//        tmpGetToken(10000,
//                "FAMGRQIQFJFINFFDBXGCEZEKTCLFRQBQFZFL9YVQCGUPXXEQYMEEWDDLAWJZPOEJMUHNBHONYJWGCOXIW");
//        tmpGetAddress();

//        testMainTime(1);

        tipSelection();

    }


    public static void tipSelection(){

        System.out.println("----------设置私人网络参数---------");
        String protocol = "http";
        String node = "134.175.210.190";
        int port = 14265;

//        testTransferTime.testTimeByTipSelection(protocol,node,port);

        IotaAPI api = testTransferTime.getApi(protocol,node,port);
        for (int i = 0;i < 100 ; i++) {
            GetTransactionsToApproveResponse txs = api.getTransactionsToApprove(4, null);
            System.out.println("duration " + txs.getDuration() + " ms");
        }
    }



    //从大佬seed那里拿点钱过来用
    public static void tmpGetToken(int token,String address){
        System.out.println("----------设置私人网络参数---------");
        String protocol = "http";
        String node = "134.175.210.190";
        int port = 14265;

        String seed = "RJIGWIXUFYFXL9ILKAQGHMAJWCGTHJB9RMZZZMGLPQKZJTBHHOCGZGQJUFOQQBQVLIYWGJ9QAMEWEUFMH";

        tmpTransferToken.tmpTransferTokenByAmount(protocol,node,port,seed,address,token);

        System.out.println("----------从大哥那边拿到钱了，开始飘了---------");


    }

    public static void tmpGetAddress(){

        System.out.println("----------设置私人网络参数---------");
        String protocol = "http";
        String node = "134.175.210.190";
        int port = 14265;


        //要创建地址seed1
        String seed = "RJIGWIXUFYFXL9ILKAQGHMAJWCGTHJB9RMZZZMGLPQKZJTBHHOCGZGQJUFOQQBQVLIYWGJ9QAMEWEUFMH";

        System.out.println("----------创建地址并输出---------");
        //输出地址
        generateTestAddress.generateNumsOfAddressOfSeed(protocol,node,port,seed,1).get(0);

        System.out.println("----------结束---------");

    }


    public static void testMainTime(int count){
        String protocol = "http";
        String node = "134.175.210.190";
        int port = 14265;

        //在操作前需要先给这个sendSeed充值，这里是seed1
        String sendSeed = "YSCF9HBOEPTDRRR9CYXUQCMDSAMQCFKHCWEJR9GYDLNCVLSUFRDAIBRZXIGVIJWHVFQXGUQLLCVHZVAQT";

        //这里是seed2
        String reciveSeed = "RJIGWIXUFYFXL9ILKAQGHMAJWCGTHJB9RMZZZMGLPQKZJTBHHOCGZGQJUFOQQBQVLIYWGJ9QAMEWEUFMH";

//        String reciveAddress = generateTestAddress.generateNumsOfAddressOfSeed(protocol,node,port,"RJIGWIXUFYFXL9ILKAQGHMAJWCGTHJB9RMZZZMGLPQKZJTBHHOCGZGQJUFOQQBQVLIYWGJ9QAMEWEUFMH",1).get(0);
//
//        List<Input> list = new ArrayList<>();
//        list.add(new Input("9PZOTANLFHTUSXMKQSDIXQFDGVBEFMLYKKGJHFZRAMQPKDW9NMJZQVGWVOIVASEKDBCNKTLJCXEQEWOUZ"
//                ,0,0,1));
//
////        //开始进行交易
//        testTransferTime.testTimeByTransfer(protocol,node,port,sendSeed,reciveSeed,count,list);
        testTransferTime.testTimeByTransferNoInput(protocol,node,port,sendSeed,reciveSeed,count);

//        tmpTransferToken.tmpTransferTokenZeroValue(protocol,node,port,sendSeed,reciveAddress);

    }
}
