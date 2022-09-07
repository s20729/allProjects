package Zad1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Futil {

    public static void processDir(String dirName, String result){
        Visit visit = new Visit(result);
        try {
            Files.walkFileTree(Paths.get(dirName),visit);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
