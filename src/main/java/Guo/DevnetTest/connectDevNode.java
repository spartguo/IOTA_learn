package Guo.DevnetTest;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetNodeInfoResponse;

public class connectDevNode {
    public static void main(String []args){

        System.out.println("----------连接官方测试节点-----------");
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

        GetNodeInfoResponse response = api.getNodeInfo();

        System.out.println("----------打印官方测试节点信息-----------");
        System.out.println(response);

        System.out.println("----------结束-----------");


    }
}
