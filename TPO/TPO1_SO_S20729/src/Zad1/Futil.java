package Zad1;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Futil {
    static void  processDir(String dir, String rez){
        try{
        Files.walkFileTree(Paths.get(dir), new MFileVizitor(Paths.get(rez)));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
