/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad4;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Server extends Thread{
    String host;
    int port;
    String id;
    Map<SelectionKey,String> mapLog = new HashMap<>();
    boolean serverIsRunning = true;
    private static Charset charset  = Charset.forName("ISO-8859-2");
    public StringBuilder log = new StringBuilder();
    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss:SSS");


    public Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void  startServer(){
        new Thread(this).start();
    }
    public void stopServer(){
        this.stop();
    }


    @Override
    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel ssc  = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(InetAddress.getByName(host), port));
            ssc.configureBlocking(false);
            SelectionKey sscKey;
            ssc.register(selector,SelectionKey.OP_ACCEPT);
            while (serverIsRunning){
                selector.select();
                Set keys = selector.selectedKeys();
                Iterator iter = keys.iterator();
                while (iter.hasNext()){
                     sscKey = (SelectionKey)iter.next();
                    iter.remove();
                    if (sscKey.isAcceptable()) {
                        SocketChannel cc = ssc.accept();
                        cc.configureBlocking(false);
                        cc.register(selector, SelectionKey.OP_READ);
                        continue;
                    }
                    if (sscKey.isReadable()) {
                        SocketChannel cc = (SocketChannel) sscKey.channel();
                        serviceRequest(cc, sscKey);    // obsluga zlecenia
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void serviceRequest(SocketChannel socketChannel,SelectionKey key){
        try {
            ByteBuffer bbuf = ByteBuffer.allocate(1024);
            socketChannel.read(bbuf);
            String result = new String(bbuf.array()).trim();
            if(result.contains("login")){
                id = result.split(" ")[1];
                log.append(id + " logged in at " + dateFormat.format(new Date()) + " \n");
                mapLog.put(key,"=== " + id + " log start === \nlogged in \n");
                socketChannel.write(charset.encode(CharBuffer.wrap("logged in \n")));
            }else if(result.equals("bye")){
                String getId = mapLog.get(key).split(" ")[1];
                log.append(getId + " logged out at " + dateFormat.format(new Date()));
                mapLog.put(key,mapLog.get(key) + id + " logged out at " + dateFormat.format(new Date()) + " \n");
                socketChannel.write(charset.encode(CharBuffer.wrap("logged out \n")));
            }else if(result.contains("-")){
                String getId = mapLog.get(key).split(" ")[1];
                log.append(getId + " request at " + dateFormat.format(new Date()) +": " + "\"" + result + "\"" + "\n");
                mapLog.put(key,mapLog.get(key) + "Request: " + result + "\n");
                String[] split = result.split(" ");
                mapLog.put(key,mapLog.get(key) + "Result: \n" + Time.passed(split[0],split[1]));
                socketChannel.write(charset.encode(CharBuffer.wrap(Time.passed(split[0],split[1]) + "\n")));
            }else if(result.contains("bye and log")){
                String getId = mapLog.get(key).split(" ")[1];
                log.append(getId + " logged out at " + dateFormat.format(new Date()) + "\n");
                mapLog.put(key,mapLog.get(key) + "logged out \n" + "=== " + getId + " log end ===");
                socketChannel.write(charset.encode(CharBuffer.wrap(mapLog.get(key))+"\n"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public String getServerLog(){
        return log.toString();
    }
}
