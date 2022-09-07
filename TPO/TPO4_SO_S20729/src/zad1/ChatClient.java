/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatClient {
    private final InetSocketAddress inetSocketAddress;
    private SocketChannel channel;
    private final Thread receivingThread = new Thread(this::run);
    private final StringBuilder clientView;
    private final String id;

    private final Lock lock = new ReentrantLock();

    public ChatClient(String host, int port, String id) {
        inetSocketAddress = new InetSocketAddress(host, port);
        this.id = id;

        clientView = new StringBuilder("=== " + id + " chat view \n");
    }

    private void run() {

        int size = 1024;
        ByteBuffer buffer = ByteBuffer.allocateDirect(size);
        int bytesRead = 0;

        while (!receivingThread.isInterrupted()) {
            do try {
                lock.lock();
                bytesRead = channel.read(buffer);

            } catch (Exception exception) {
                exception.printStackTrace();

            } finally {
                lock.unlock();
            } while (bytesRead == 0 && !receivingThread.isInterrupted());

            String response = StandardCharsets.UTF_8.decode(buffer.flip()).toString();
            clientView.append(response);
            buffer.clear();
        }
    }

    public void login() {

        try {
            channel = SocketChannel.open(inetSocketAddress);
            channel.configureBlocking(false);

            send("log in " + id);

            receivingThread.start();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void logout() {

        send("log out" + "#");

        try {
            lock.lock();
            receivingThread.interrupt();

        } catch (Exception exception) {
            exception.printStackTrace();

        } finally {
            lock.unlock();
        }
    }

    public void send(String req) {

        try {
            Thread.sleep(30);
            channel.write(StandardCharsets.UTF_8.encode(req + "#"));
            Thread.sleep(30);

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public String getChatView() {

        return clientView.toString();
    }


}
