/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
  public static int max = 0;
  public static void main(String[] args) throws MalformedURLException, IOException {

//    InputStream inputStream = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt").openStream();
//    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
//    Map<String, List<String>> mapAnagrams = bufferReader.lines().sorted().collect(Collectors.groupingBy(e ->{char[] chars = e.toCharArray();
//      Arrays.sort(chars);
//      return new String(chars);}));
//    mapAnagrams.forEach((e1, e2) -> {if(max < e2.size()) max = e2.size();});
//    Map<String, List<String>> resMap = mapAnagrams.entrySet().parallelStream().filter(e -> max == e.getValue().size()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    resMap.forEach((e1,e2) -> System.out.println(e1 + " = " +e2));

}
}
