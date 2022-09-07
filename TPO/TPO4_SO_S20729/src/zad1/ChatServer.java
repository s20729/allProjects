/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatServer implements Runnable{
    private final Thread serverThread;
    private final InetSocketAddress inetSocketAddress;
    private final Lock lock = new ReentrantLock();
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private final Map<SocketChannel, String> clientsMap;
    private final StringBuilder log;

    public ChatServer(String host, int port) {
        inetSocketAddress = new InetSocketAddress(host, port);
        log = new StringBuilder();
        clientsMap = new HashMap<>();
        this.serverThread = serverThread();
    }

    private Thread serverThread() {

        return new Thread(this::run);

    }

    private StringBuilder requestHandler(SocketChannel clientSocket, String str) throws IOException {

        StringBuilder response = new StringBuilder();

        if (str.matches("log in .+")) {
            clientsMap.put(clientSocket, str.substring(7));

            log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ").append(str.substring(7)).append(" logged in").append("\n");
            response.append(str.substring(7)).append(" logged in").append("\n");

        } else if (str.matches("log out")) {
            log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ").append(clientsMap.get(clientSocket)).append(" logged out").append("\n");
            response.append(clientsMap.get(clientSocket)).append(" logged out").append("\n");

            ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(response.toString());
            clientSocket.write(byteBuffer);

            clientsMap.remove(clientSocket);

        } else {
            log.append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ").append(clientsMap.get(clientSocket)).append(": ").append(str).append("\n");
            response.append(clientsMap.get(clientSocket)).append(": ").append(str).append("\n");
        }

        return response;
    }

    public void startServer() {

        serverThread.start();

        System.out.println("Server started\n");
    }

    public void stopServer() {

        try {
            lock.lock();
            serverThread.interrupt();
            selector.close();
            serverSocketChannel.close();

            System.out.println("Server stopped");

        } catch (IOException exception) {
            exception.printStackTrace();

        }finally {
            lock.unlock();
        }
    }

    String getServerLog() {

        return log.toString();
    }

    @Override
    public void run() {
        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, serverSocketChannel.validOps(), null);

            while (!serverThread.isInterrupted()) {
                selector.select();

                if (serverThread.isInterrupted()) {
                    break;
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel clientSocket = serverSocketChannel.accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    }

                    if (key.isReadable()) {
                        SocketChannel clientSocket = (SocketChannel) key.channel();

                        int capacity = 1024;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);

                        StringBuilder clientRequest = new StringBuilder();

                        int readBytes = 0;
                        do try {
                            lock.lock();

                            readBytes = clientSocket.read(buffer);
                            buffer.flip();
                            clientRequest.append(StandardCharsets.UTF_8.decode(buffer).toString());
                            buffer.clear();
                            readBytes = clientSocket.read(buffer);

                        } catch (Exception exception) {

                            exception.printStackTrace();
                        } finally {
                            lock.unlock();
                        } while (readBytes != 0);

                        String[] parts = clientRequest.toString().split("#");

                        for (String req : parts) {
                            String clientResponse = ChatServer.this.requestHandler(clientSocket, req).toString();

                            for (Map.Entry<SocketChannel, String> entry : clientsMap.entrySet()) {
                                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(clientResponse);
                                entry.getKey().write(byteBuffer);
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
