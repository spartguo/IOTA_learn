package Guo.javaGuide;

import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.error.ArgumentException;

import java.util.ArrayList;
import java.util.List;

public class checkBalance {
    public static void main(String []args){
        System.out.println("-----------连接节点---------");

        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

//        IotaAPI api = new IotaAPI.Builder()
//                .protocol("http")
//                .host("134.175.210.190")
//                .port(14256)
//                .build();


        List<String> addresses = new ArrayList<String>();
        //将我们要查询的 address加入
        addresses.add("UNVHADHYCFU9YFGFQOLFCXNTQVVLIKQYXRLLBELZSAYCOCVKHOBDOUWSJ9GEYRRRORVJM9RYXANAQLLX9A9GRAJEPZ");


        System.out.println("-----------开始查询---------");
        try {
            long balance = api.getBalance(100, addresses.get(0));
            System.out.printf("Your balance is: %s\n", balance);
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------查询结束---------");
    }
}
