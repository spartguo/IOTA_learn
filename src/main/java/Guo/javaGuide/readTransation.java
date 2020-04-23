package Guo.javaGuide;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.utils.TrytesConverter;

public class readTransation {
    public static void main(String []args) throws ArgumentException{
        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

        //准备
        System.out.println("-----------准备hash---------");
//        String tailTransactionHash = "GUQXLBNYXSEVNAPNJ9L9ADZIHBHRVEH9VZURPUHGQEVAFCWIDKQWTKYPOFALYZNOWGRJQURNQBGFGQDM9";

        //这个东西最好是respone里面的hash，不知道为啥要这样
        //todo
        String tailTransactionHash = "FDROJFXFSTIWLONDFVJHCWT9SZLM9HV9ZTWKNNLBIJBBAEYSNWKHDCJYBSW9HNCQN9UPHKKTWBJDKXFJW";
        System.out.println("----------开始获得bundle的交易然后输出里面的信息---------");
        try {
            //用getBundle拿到这个bundle所有的交易
            GetBundleResponse response = api.getBundle(tailTransactionHash);
            System.out.println(TrytesConverter.trytesToAscii(response.getTransactions().get(0).getSignatureFragments().substring(0,2186)));
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------完成查询---------");
    }

}
