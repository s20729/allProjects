/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package Zad1;


public class Main {
  public static void main(String[] args) {
    String dirName = System.getProperty("user.home")+"/TPO1dir";
    System.out.println(dirName);
    String resultFileName = "TPO1res.txt";
    Futil.processDir(dirName, resultFileName);
  }
}
