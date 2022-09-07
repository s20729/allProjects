/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;
import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test2(src2));

  }

  public List<Integer> test1(List<Integer> src) {
  Selector sel = new Selector<Boolean>() { /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
    @Override
    public <T> boolean select(T var) {
      return (Integer)var <10;
    }
  };
  Mapper<Integer, Integer> map = new Mapper<Integer, Integer>() {
    @Override
    public <S> Integer map(Integer var) {
      return var+10;
    }
  };
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);  /*<-- zwrot wynikuuzyskanego przez wywołanie statycznej metody klasy ListCreator:*/
  }

  public List<Integer> test2(List<String> src) {
    Selector sel = new Selector() {   /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
      @Override
      public boolean select(Object var) {
        return var.toString().length() >3;
      }
    };
    Mapper<String, Integer> map = new Mapper<String, Integer>() {
      @Override
      public <S> Integer map(String var) {
        return var.length()+10;
      }
    };
    return  ListCreator.collectFrom(src).when(sel).mapEvery(map); /*<-- zwrot wyniku uzyskanego przez wywołanie statycznej metody klasy ListCreator:*/  
  }

  public static void main(String[] args) {
    new Main();
  }

}
