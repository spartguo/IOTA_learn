package Guo.accountMoudle;

import org.iota.jota.IotaAPI;
import org.iota.jota.IotaAccount;
import org.iota.jota.account.AccountStore;
import org.iota.jota.account.store.AccountFileStore;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.error.ArgumentException;

import java.io.File;


public class createAccount {
    public static void main(String []args) throws ArgumentException {
        // Create a new instance of the API object
        // and specify which node to connect to
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .timeout(500)
                .build();


        // Call the `getNodeInfo()` method for information about the node and the Tangle
        GetNodeInfoResponse response = api.getNodeInfo();
        System.out.println("---------输出节点信息-------");
        System.out.println(response);

        //创建文件存储账号（种子）信息状态
        File file = new File("F://iota_seed_info//seed-state-database.json");

        AccountStore store = new AccountFileStore(file);

        String mySeed = "XFLBBFEPENMGVQKSZHLGUZRVDIMBVQHMMHPOIKJBBDKRHPPNVHSFTGGPLQTNKUJ9QBUULTECXYDWNXYBW";

        System.out.println("---------创建账户-------");

        IotaAccount account = new IotaAccount.Builder(mySeed)
                // Connect to a node
                .api(api)
                // Connect to the database
                .store(store)
                // Set the minimum weight magnitude for the Devnet (default is 14)
                .mwm(9)
                // Set a security level for CDAs (default is 3)
                .securityLevel(2)
                .build();

        System.out.println("---------启动账户-------");
        account.start();

        long balance = account.availableBalance();

        System.out.println("Your balance is: " + Math.toIntExact(balance));

        account.shutdown();
        System.out.println("---------关闭账户-------");
    }

}
