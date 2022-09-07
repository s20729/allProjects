/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad4;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable{
    static Client client;
    public static List<String> listaReqs;
    static boolean showSendRes;
    public static InputStream inputStream;
    public static String log;

    public ClientTask(Client c, List<String> reqs, boolean showSendRes) {
        client = c;
        listaReqs = reqs;
        this.showSendRes= showSendRes;
    }

    public static ClientTask create(Client c, List<String> r, boolean s){
        try (Socket socket = new Socket(InetAddress.getByName(c.host), c.port)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("login" + c.id);
            Thread.sleep(300);
            r.forEach(e-> printWriter.println(e));
            printWriter.println("bye and log transfer");
            inputStream = socket.getInputStream();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ClientTask(c, r, showSendRes);
    }

    @Override
    public void run() {
    }

    public String get() throws InterruptedException, ExecutionException {
        return log;
    }
}
