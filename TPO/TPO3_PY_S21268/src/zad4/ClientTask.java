/**
 *
 *  @author Popichko Yehor S21268
 *
 */

package zad4;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable{
    private Client c;
    private List<String> reqs;
    private boolean showSendRes;
    private String log;
    private Socket socket;
    private PrintWriter printWriter;
    private InputStream inputStream;



    public ClientTask(Client c, List<String> reqs, boolean showSendRes) {
        this.c = c;
        this.reqs = reqs;
        this.showSendRes = showSendRes;
        try {
            socket = new Socket(InetAddress.getByName(c.host), c.port);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("login " + c.id);
            Thread.sleep(10);
            for (String req :
                    this.reqs) {
                printWriter.println(req);
                Thread.sleep(20);
            }
            printWriter.println("bye and log transfer");
            inputStream = socket.getInputStream();
            byte[] log = new byte[5000];
            inputStream.read(log);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ClientTask(c, reqs,showSendRes);
    }

    @Override
    public void run() {
try {

    byte[] log = new byte[5000];
    inputStream.read(log);
    String s = new String(log);
    this.log = s;
    inputStream.close();
    socket.close();
}catch (Exception e){
    e.printStackTrace();
}
    }
    public String get() throws InterruptedException, ExecutionException {
        return this.log;
    }
}
