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
import org.iota.jota.model.Bundle;

import java.io.File;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * 创建交易
 * 1-创建CDA，制造交易地址
 * 2-制造交易
 */
public class makePayment {
    public static void main(String []args) throws ArgumentException,InterruptedException,ExecutionException{

        //连接到节点
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.comnet.thetangle.org")
                .port(443)
                .timeout(500)
                .build();


        //打印节点信息

        System.out.println("-------打印节点信息-------");
        GetNodeInfoResponse response = api.getNodeInfo();
        System.out.println(response);

        //自己的种子
        String mySeed = "TZXHPFAKIIAYOWDVWUNAJOHYSOIDPBVUVWZPVTLPXWEUQPZIRPCESAXMSJZK999F9MTOWBIYJFFE9XCPO";

        //得到存储账号（种子）信息状态
        File file = new File("F://iota_seed_info//seed-state-database.json");

        AccountStore store = new AccountFileStore(file);

        IotaAccount account = new IotaAccount.Builder(mySeed)
                // Connect to a node
                .api(api)
                // Connect to the database
                .store(store)
                // Set the minimum weight magnitude for the Devnet (default is 14)
                .mwm(10)
                // Set a security level for CDAs (default is 3)
                .securityLevel(2)
                .build();
        System.out.println("-------启动账户-------");
        account.start();

        long balance = account.availableBalance();

        System.out.print("Your balance is: " + Math.toIntExact(balance));

        String magnet = "iota://BWNYWGULIIAVRYOOFWZTSDFXFPRCFF9YEHGVBOORLGCPCJSKTHU9OKESUGZGWZXZZDLESFPPTGEHVKTTXG9BQLSIGP/?timeout_at=5174418337&multi_use=1&expected_amount=0";

        //通过磁力链接拿到CDA
        ConditionalDepositAddress cda = DepositFactory.get().parse(magnet, MagnetMethod.class);


        /*****************开始交易*****************/
        System.out.println("-------开始交易-------");

        Bundle bundle = account.send(
                cda.getDepositAddress().getHashCheckSum(),
        cda.getRequest().getExpectedAmount(),
                Optional.of("Thanks for the pizza"),
                Optional.of("ACCOUNTMODULETEST")).get();

        System.out.printf("Sent deposit to %s in the bundle with the following tail transaction hash %s\n",
                bundle.getTransactions().get(bundle.getLength() - 1).getAddress(), bundle.getTransactions().get(bundle.getLength() - 1).getHash());


        account.shutdown();
    }
}
