/**
 *
 *  @author Popichko Yehor S21268
 *
 */

package zad4;



import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server extends Thread {

    private String host;
    private int port;
    private InetAddress inetAddress;
    private Selector selector;
    private Thread thread;
    private String id;
    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss:SSS");
    private String logInfo = "";
    String logClient = "";
Map<SelectionKey,String> mapLog = new HashMap<>();



    public Server(String host, int port){
        this.host = host;
        this.port = port;
        try {
            this.inetAddress = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void startServer(){
         thread = new Thread(this);
        thread.start();

    }
    public void stopServer(){
            thread.stop();
    }


    @Override
    public void run() {

        try {
            this.selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(inetAddress,port));
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            SelectionKey key = null;
            while (true){
                if(selector.select() <= 0)
                    continue;
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    key = (SelectionKey)iterator.next();
                    iterator.remove();
                    if(key.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if(key.isReadable()){
                        SocketChannel sc = (SocketChannel) key.channel();
                       getRequest(sc,key);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    Charset charset = StandardCharsets.UTF_8;
    public void getRequest(SocketChannel socketChannel,SelectionKey key) {
        try {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            socketChannel.read(bb);
            String result = new String(bb.array()).trim();
            if(result.contains("login")){
                id = result.split(" ")[1];
                logInfo += id + " logged in at " + this.dateFormat.format(new Date()) + "\n";
                mapLog.put(key,"=== " + id + " log start ===\nlogged in\n");
                ByteBuffer bb1 = charset.encode(CharBuffer.wrap("logged in\n"));
                socketChannel.write(bb1);
            }else if(result.equals("bye")){
                String getId = mapLog.get(key).split(" ")[1];
                logInfo += getId + " logged out at " + this.dateFormat.format(new Date());
                mapLog.put(key,mapLog.get(key) + id + " logged out at " + dateFormat.format(new Date()) + "\n");
                ByteBuffer bb1 = charset.encode(CharBuffer.wrap("logged out\n"));
                socketChannel.write(bb1);
            }else if(result.contains("-")){
                String getId = mapLog.get(key).split(" ")[1];
                logInfo += getId + " request at " + this.dateFormat.format(new Date()) +": " + "\"" + result + "\"" + "\n";
                mapLog.put(key,mapLog.get(key) + "Request: " + result + "\n");
                String[] split = result.split(" ");
                mapLog.put(key,mapLog.get(key) + "Result:\n" + Time.passed(split[0],split[1]));
                ByteBuffer bb1 = charset.encode(CharBuffer.wrap(Time.passed(split[0],split[1]) + "\n"));
                socketChannel.write(bb1);
            }else if(result.contains("bye and log")){
                String getId = mapLog.get(key).split(" ")[1];
                logInfo += getId + " logged out at " + this.dateFormat.format(new Date()) + "\n";
                mapLog.put(key,mapLog.get(key) + "logged out\n" + "=== " + getId + " log end ===");
                ByteBuffer bb1 = charset.encode(CharBuffer.wrap(mapLog.get(key))+"\n");
                socketChannel.write(bb1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getServerLog(){
        return logInfo;
    }
}
