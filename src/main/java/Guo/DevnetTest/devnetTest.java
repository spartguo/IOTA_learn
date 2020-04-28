package Guo.DevnetTest;

import Guo.Common.generateTestAddress;
import Guo.Common.testTransferTime;

import java.util.List;

public class devnetTest {
    public static void main(String []args){
        testTipSelect();


    }

    public static void testTipSelect(){
        String protocol = "https";
        String node = "nodes.iota.cafe";
        int port = 443;

        testTransferTime.testTimeByTipSelection(protocol,node,port);
    }



    /**
     * 主测试方法
     */
    public static void testMainTime(){
        String protocol = "https";
        String node = "nodes.comnet.thetangle.org";
        int port = 443;

        //在操作前需要先给这个sendSeed充值，这里是seed1
        String sendSeed = "XHSY9DGBABQUZWGX9XNUXVWTBBYZRJFSOXXRIXYRPRMPKXDTKMGMJSUX9SWRJTWWZNWAYSILLRXGFUJW9";

        //这里是seed2
        String reciveSeed = "XFLBBFEPENMGVQKSZHLGUZRVDIMBVQHMMHPOIKJBBDKRHPPNVHSFTGGPLQTNKUJ9QBUULTECXYDWNXYBW";

        //开始进行交易
        testTransferTime.testTimeByTransfer(protocol,node,port,sendSeed,reciveSeed,10);
    }

}
