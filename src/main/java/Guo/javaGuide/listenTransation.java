package Guo.javaGuide;

import org.zeromq.ZMQ;

/**
 * 利用ZMQ来监听这个节点的Tangle的更新情况
 */
public class listenTransation {
    public static void main(String []args){

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.SUB);

        System.out.println("-----------连接节点ZMQ---------");
        //开始连接
        socket.connect("tcp://zmq.nodes.comnet.thetangle.org:5556");

        System.out.println("-----------添加订阅---------");
        //全部transation
        socket.subscribe("tx");

        //已经确认的transation
        socket.subscribe("sn");

        System.out.println("-----------开始监听---------");
        while(true) {
            byte[] reply = socket.recv(0);
            String[] data = (new String(reply).split(" "));

            if(data[0].equals("tx")) System.out.println("NEW TRANSACTION" + "\n" + "Transaction hash: " + data[1] + "\n" + "Address: " + data[2] + "\n" + "Value: " + data[3] + "\n" + "Tag: " + data[4] + "\n");
            if(data[0].equals("sn")) System.out.println("CONFIRMED" + "\n" + "Transaction hash: " + data[2] + "\n" + "Address: " + data[3] + "\n");
        }


    }
}
