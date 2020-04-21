package Guo;


import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.error.ArgumentException;

public class ConnectToNode {
    public  static void main(String []args) throws ArgumentException {
        // Create a new instance of the API object
        // and specify which node to connect to
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .build();

        // Call the `getNodeInfo()` method for information about the node and the Tangle
        GetNodeInfoResponse response = api.getNodeInfo();
        System.out.println(response);

    }
}
