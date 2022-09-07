/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;

public class ListCreator <T>{ // Uwaga: klasa musi być sparametrtyzowana
    List<T> list;
    List<T> tmpList;

    public ListCreator(List<T> list){
        this.list = list;
    }

    public static <T> ListCreator <T> collectFrom(List<T> from){
        return new ListCreator<T>(from);
    }

    public ListCreator<T> when(Selector<T> selector){
       tmpList = new ArrayList<>();
        for (T var :list) {
            if (selector.select(var))
                tmpList.add(var);
        }
       list =tmpList;
       return this;
    }

    public List <T> mapEvery(Mapper mappper){
        tmpList = new ArrayList<>();
        for (T var: list) {
            tmpList.add((T) mappper.map(var));
        }
        return tmpList;
    }
}  
