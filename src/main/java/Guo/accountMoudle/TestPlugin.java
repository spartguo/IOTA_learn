package Guo.accountMoudle;

import org.iota.jota.IotaAccount;
import org.iota.jota.account.Account;
import org.iota.jota.account.event.AccountEvent;
import org.iota.jota.account.event.events.EventPromotion;
import org.iota.jota.account.event.events.EventTransferConfirmed;
import org.iota.jota.account.plugins.Plugin;

/**
 * 插件来的，这个插件可以附加到个人用户中，比如设置一些交易的通知啥的。
 */
public class TestPlugin implements Plugin {

    private Account account;

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public String name() {
        return "Spartguo_Plugin";
    }

    @Override
    public void load() throws Exception {
        //not programe yet
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @AccountEvent
    public void confirmed(EventTransferConfirmed e) {
        System.out.println("account: " + account.getId());
        System.out.println("confimed: " + e.getBundle().getBundleHash());
    }

    @AccountEvent
    public void promoted(EventPromotion e) {
        System.out.println("account: " + account.getId());
        System.out.println("promoted: " + e.getPromotedBundle());
    }

//    public static void main(String []args){
//        //可以通过这种方式将插件附加上去
//        Plugin myPlugin = new TestPlugin();
//        IotaAccount account = new IotaAccount.Builder(SEED)
//                .plugin(myPlugin)
//                .build();
//    }
}
