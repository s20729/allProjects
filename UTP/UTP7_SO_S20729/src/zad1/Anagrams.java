/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Anagrams {
    private List<String> listaSlowek;
    private List<List> listy;

    public Anagrams(String slowki) {
        listaSlowek = new ArrayList<>();
        BufferedReader br;
        String tmpLine;
        try {
            br = new BufferedReader(new FileReader(new File(slowki)));
            while ((tmpLine = br.readLine()) != null) {
                String[] words = tmpLine.split(" ");
                for (int i = 0; i < words.length; i++) {
                    listaSlowek.add(words[i]);
                }
            }
        } catch (FileNotFoundException ex1) {
            System.err.println("Anagrams konstruktor FileNotFoundException !");
        } catch (IOException ex2) {
            System.err.println("Anagrams konstruktor IOException !");
        }
    }

    public List<List> getSortedByAnQty() {
        listy = new ArrayList<List>();
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < listaSlowek.size(); i++) {
            if (!lista.contains(listaSlowek.get(i))) {
                List<String> tmp = new ArrayList<>();
                for (int j = 0; j < listaSlowek.size(); j++) {
                    if (isAnagram(listaSlowek.get(i), listaSlowek.get(j))) {
                        lista.add(listaSlowek.get(j));
                        tmp.add(listaSlowek.get(j));
                    }
                }
                listy.add(tmp);
            }
        }
        listy.sort((o1, o2) -> o2.size() - o1.size());
        return listy;
    }

    public String getAnagramsFor(String next) {
        for (int i = 0; i < listy.size(); i++) {
            List<String> tmp = new ArrayList<>(listy.get(i));
            for (int j = 0; j < tmp.size(); j++) {
                if(tmp.get(j).equals(next)) {
                    tmp.remove(j);
                    return next+": " + tmp;
                }
            }
        }
        return "";
    }

    public boolean isAnagram(String str1, String str2) {
        char[] word1 = str1.replaceAll("[\\s]", "").toCharArray();
        char[] word2 = str2.replaceAll("[\\s]", "").toCharArray();
        Arrays.sort(word1);
        Arrays.sort(word2);
        return Arrays.equals(word1, word2);
    }
}  
