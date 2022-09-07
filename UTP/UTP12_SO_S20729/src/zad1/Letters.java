package zad1;

import java.util.ArrayList;

public class Letters extends Thread {
    ArrayList<Thread> threads = new ArrayList<Thread>();

    public Letters(String string){
        for(int i = 0; i < string.length(); i++){
            String lt = string.substring(i, i + 1);
            threads.add(new Thread("Thread " + lt){
                public void run(){
                    while(true){
                        try{
                            System.out.print(lt);
                            Thread.sleep(1000);
                        } catch (InterruptedException exception){
                            break;
                        }
                    }
                }
            });
        }
    }

    public ArrayList<Thread> getThreads(){
        return threads;
    }

    public void run(){
        while(true){
            try{
                System.out.print(this.getName());
                Thread.sleep(1000);
            } catch (InterruptedException exception){
                System.out.println("Wątek przerwany");
            }
        }

    }

}
