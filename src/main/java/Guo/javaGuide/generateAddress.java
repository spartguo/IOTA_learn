package Guo.javaGuide;

import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.GetNewAddressResponse;
import org.iota.jota.error.ArgumentException;

public class generateAddress {
    public static void main(String []args){
        System.out.println("-----------连接节点---------");

        //https://comnet.thetangle.org/也是
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

        int securityLevel = 2;

        //输入seed，人家通过seed来生成address
        String mySeed = "XFLBBFEPENMGVQKSZHLGUZRVDIMBVQHMMHPOIKJBBDKRHPPNVHSFTGGPLQTNKUJ9QBUULTECXYDWNXYBW";

        System.out.println("----------开始生成新的地址----------");
        try {
            GetNewAddressResponse response = api.generateNewAddresses(new AddressRequest.Builder(mySeed, securityLevel).amount(1).checksum(true).build());
            System.out.printf("Your address is %s \n", response.getAddresses());
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println("-----------生成完毕---------");

    }
}
