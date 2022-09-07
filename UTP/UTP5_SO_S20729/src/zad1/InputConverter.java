package zad1;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputConverter<T> {
    private T src;

    public InputConverter(T src) {
        this.src = src;
    }

    public <T,R> R convertBy(Function... func) {
        List fncs = new ArrayList();

        fncs.add(func[0].apply(src));
        for (int i = 1; i < func.length; i++) {
            fncs.add(func[i].apply(fncs.get(i-1)));
        }
        return (R) fncs.get(fncs.size() -1 );
    }
}