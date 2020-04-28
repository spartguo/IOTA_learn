package Guo.tmpTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class outputTest {
    public static void main(String []args) throws InterruptedException{
        int i = 10;
        String j = "hhh";
        System.out.printf("hhhhhh %d jkdj %s",i,j);

        long time = System.currentTimeMillis();

        Thread.sleep(100);
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time);
    }
}
