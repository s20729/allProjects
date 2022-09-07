/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad4;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    public String id;

    public boolean isConnected = false;
    public String host;
    public int port;

    private Selector selector;

    public Client(String host, int port, String id) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public void connect() {
        try {
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(InetAddress.getByName(host), port));
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String send(String req) {
        String res = "";
        ByteBuffer bbuf = ByteBuffer.allocate(1024);
        try {

            if(selector.select() > 0){
                isConnected = true;
            }
            if(isConnected){
                Set setOfKeys = selector.selectedKeys();
                SelectionKey key = null;
                Iterator iterator = setOfKeys.iterator();

                while (iterator.hasNext()){
                    key = (SelectionKey) iterator.next();
                    iterator.remove();
                }

                SocketChannel socketChannel = (SocketChannel) key.channel();
                if (socketChannel.isConnectionPending()){
                        socketChannel.finishConnect();
                }

                ByteBuffer byteBuffer = ByteBuffer.wrap(req.getBytes());
                socketChannel.write(byteBuffer);
                Thread.sleep(10);
                socketChannel.read(bbuf);
                res = new String(bbuf.array()).trim();

                if(res.contains("logged out"))
                    socketChannel.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
