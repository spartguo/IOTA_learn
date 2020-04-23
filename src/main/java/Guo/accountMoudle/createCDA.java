package Guo.accountMoudle;

import org.iota.jota.IotaAPI;
import org.iota.jota.IotaAccount;
import org.iota.jota.account.AccountStore;
import org.iota.jota.account.deposits.ConditionalDepositAddress;
import org.iota.jota.account.deposits.methods.DepositFactory;
import org.iota.jota.account.deposits.methods.MagnetMethod;
import org.iota.jota.account.store.AccountFileStore;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.error.ArgumentException;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * 创建自己的 CDA
 */
public class createCDA {
    public static void main(String []args) throws ArgumentException,InterruptedException, ExecutionException {
        //连接到节点
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .timeout(500)
                .build();


        System.out.println("-------打印节点信息-------");
        GetNodeInfoResponse response = api.getNodeInfo();
        System.out.println(response);

        //自己的种子
        String mySeed = "TZXHPFAKIIAYOWDVWUNAJOHYSOIDPBVUVWZPVTLPXWEUQPZIRPCESAXMSJZK999F9MTOWBIYJFFE9XCPO";

        //得到存储账号（种子）信息状态
        File file = new File("F://iota_seed_info//seed-state-database.json");

        AccountStore store = new AccountFileStore(file);


        //目前不知道是不是每次都得build一下
        //todo
        System.out.println("-------获得账户-------");
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

        System.out.println("-------启动账户-------");
        account.start();

        System.out.println("Your balance is: " + Math.toIntExact(account.availableBalance()));


        /************开始创建CDA***************/

        System.out.println("-------开始创建CDA-------");

        // Define the same time tomorrow
        //设置过期时间为明天
        Date timeoutAt = new Date(System.currentTimeMillis() + 24000 * 60 * 60);

        // Generate the CDA
        ConditionalDepositAddress cda = account.newDepositAddress(timeoutAt, false,0).get();

        //生成磁力链接
        String magnet = (String) DepositFactory.get().build(cda, MagnetMethod.class);

        System.out.println("-------打印磁力链接-------");
        //打印出来让大伙瞧瞧
        System.out.println(magnet);

        System.out.println("-------关闭账户连接-------");
        account.shutdown();
    }
}
