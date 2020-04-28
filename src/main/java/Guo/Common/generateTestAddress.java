package Guo.Common;

import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.GetNewAddressResponse;
import org.iota.jota.error.ArgumentException;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;
import java.util.List;

public class generateTestAddress {
    public static void main(String []args){

    }

    /**
     * 根据种子生成address
     * 传入需要生成address的seed和地址个数
     * 返回所有的address
     * @param seed
     * @param n
     */
    public static List<String> generateNumsOfAddressOfSeed(String protocol,String node,int port,String seed,int n){
        System.out.println("-----------连接节点---------");

        //https://comnet.thetangle.org/也是
        IotaAPI api = new IotaAPI.Builder()
                .protocol(protocol)
                .host(node)
                .port(port)
                .build();

        int securityLevel = 2;

        String mySeed = seed;

        System.out.println("----------开始生成新的地址----------");

        List<String> list = new ArrayList<String>();

        try {
            //在amount里面设置生成的地址数n
            GetNewAddressResponse response = api.generateNewAddresses(new AddressRequest.Builder(mySeed, securityLevel).amount(n).checksum(true).build());
            list = response.getAddresses();
            System.out.printf("Your address is %s \n",response.getAddresses());
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }

        System.out.println("-----------生成完毕---------");

        return list;

    }
}
