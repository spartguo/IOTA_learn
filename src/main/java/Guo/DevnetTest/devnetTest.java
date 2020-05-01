package Guo.DevnetTest;

import Guo.Common.generateTestAddress;
import Guo.Common.testTransferTime;
import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetTransactionsToApproveResponse;
import org.iota.jota.model.Input;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class devnetTest {
    public static void main(String []args) throws IOException {
//        for (int i = 0;i < 10;i++)
////            testMainTime(100);
        File f=new File("d:\\Users\\user\\Desktop\\Comout.txt");
        f.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        for(int i = 5; i < 15 ; i ++ ){
            for (int j = 0;j < 15 ;j ++) {
                try {
                    System.out.println("dept : " + i);
                    testTipSelect(i);
                }catch (Exception e){
                    System.out.println("----------illegalstateexception--------");
                }

            }
        }

    }


    /**
     * 通过getTransactiontoApprove进行tipselection，可以控制深度
     */
    public static void testTipSelect(int dept){
        String protocol = "https";
        String node = "nodes.comnet.thetangle.org";
        int port = 443;

//        testTransferTime.testTimeByTipSelection(protocol,node,port);
        IotaAPI api = testTransferTime.getApi(protocol,node,port);
        int tmp = 0;
        for (int i = 0;i < 10 ; i++) {
            GetTransactionsToApproveResponse txs = api.getTransactionsToApprove(dept, null);
            System.out.println("duration " + txs.getDuration() + " ms");
            tmp += txs.getDuration();
        }
        System.out.println(" dept = " + dept +"\n avg = " + tmp/10 + " ms");
    }



    /**
     * 主测试方法，通过seed给其他的seed地址发送交易
     */
    public static void testMainTime(int count){
        String protocol = "https";
        String node = "nodes.comnet.thetangle.org";
        int port = 443;

        //在操作前需要先给这个sendSeed充值，这里是seed1
        String sendSeed = "XHSY9DGBABQUZWGX9XNUXVWTBBYZRJFSOXXRIXYRPRMPKXDTKMGMJSUX9SWRJTWWZNWAYSILLRXGFUJW9";

        //这里是seed2
        String reciveSeed = "XFLBBFEPENMGVQKSZHLGUZRVDIMBVQHMMHPOIKJBBDKRHPPNVHSFTGGPLQTNKUJ9QBUULTECXYDWNXYBW";


        List<Input> list = new ArrayList<>();
        list.add(new Input("FWJXVPZAKUMUQGXDDYZVNHNHIHXZSS9KLWFBZCAIMJSZEENWGYTRKCJVKP9DATOGSOHFBODNTJREJZZTCKKZ9OVDCB"
        ,2311,0,2));

        //开始进行交易
        testTransferTime.testTimeByTransfer(protocol,node,port,sendSeed,reciveSeed,count,list);
    }

}
