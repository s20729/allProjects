package zad1;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
public class ListCreator <T>{
    List<T> list;
    List<T> tmp;

    public ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> from){ return new ListCreator<T>(from); }

    public ListCreator<T> when (Predicate<T> predicate){
        tmp = new ArrayList<T>();

        for (T var:list) {
            if (predicate.test(var)){
                tmp.add(var);
            }
        }
        list = tmp;
        return this;
    }

    public <S> List<T> mapEvery(Function<T,S> function){
        tmp = new ArrayList<T>();
        for (T var : list) {
            tmp.add((T) function.apply(var));
        }
        return tmp;
    }
}
