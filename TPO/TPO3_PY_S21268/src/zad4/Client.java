/**
 *
 *  @author Popichko Yehor S21268
 *
 */

package zad4;


import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    public String id;
    private boolean isConnected = false;
    public String host;
    public int port;
    private InetSocketAddress address;
    private Selector selector;
    private SocketChannel socketChannel;

    public Client(String host, int port, String id) {
        this.id = id;
        this.host = host;
        this.port = port;
        try {
            this.address = new InetSocketAddress(InetAddress.getByName(host), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(address);
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String send(String req) {
        String result = "";
        try {

            if(selector.select() > 0){
                this.isConnected = true;
            }
            if(isConnected){
    Set readySet = selector.selectedKeys();
    SelectionKey key = null;
    Iterator iterator = null;
    iterator = readySet.iterator();
    while (iterator.hasNext()){

        key = (SelectionKey) iterator.next();
        iterator.remove();
    }
    if(key.isConnectable()){
        Boolean connected = processConnect(key);
    }
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.wrap(req.getBytes());
        socketChannel.write(byteBuffer);
        Thread.sleep(100);
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        sc.read(bb);
          result = new String(bb.array()).trim();
          if(result.contains("logged out")){
              socketChannel.close();
          }

}

        } catch (Exception e) {
            e.printStackTrace();
        }

return result;
    }
    public Boolean processConnect(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            while (sc.isConnectionPending()) {

                sc.finishConnect();
            }
        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

