package Guo.accountMoudle;


import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.error.ArgumentException;

/**
 * 尝试连接到一个节点
 */
public class ConnectToNode {
    public  static void main(String []args) throws ArgumentException {
        // Create a new instance of the API object
        // and specify which node to connect to
        //默认（nodes.comnet.thetangle.org）
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("134.175.210.190")
                .port(443)
                .build();

        // Call the `getNodeInfo()` method for information about the node and the Tangle
        GetNodeInfoResponse response = api.getNodeInfo();
        System.out.println(response);

    }
}
