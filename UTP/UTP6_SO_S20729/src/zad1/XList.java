package zad1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class XList <T> extends ArrayList<T> {
    private static Pattern pattern;
    private static Matcher matcher;

    public XList(T...args) {
        super(Arrays.asList(args));
    }

    public XList(Collection<T> c) {
        super(c);
    }

    public static<T> XList<T> of(T... args) {
        return new XList<T>(args);
    }

    public static<T> XList<T> of(Collection<T> c) {
        return new XList<T>(c);
    }

    public static <T> XList<T> charsOf(String string) {
        List returnList  = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            returnList.add(String.valueOf(string.charAt(i)));
        }
        return new XList<T>(returnList);
    }

    public static <T> XList<T> tokensOf(String... string) {
        List returnList = null;
        if(string.length == 1) {
            returnList = new ArrayList<String>(Arrays.asList(string[0].split("\\s")));
        }else {
            returnList = new ArrayList<String>(Arrays.asList(string[0].split(string[string.length - 1])));
        }
        return new XList<T>(returnList);
    }

    public XList<T> union(T... args) {
        List<T> returnList = this;
        returnList.addAll(Arrays.asList(args));
        return new XList(returnList);
    }

    public XList<T> union(Collection<T> c) {
        List<T> returnList = this;
        returnList.addAll(c);
        return new XList(returnList);
    }

    public XList<T> diff(Collection<T> c) {
        List<T> returnList = this;
        this.removeAll(c);
        return new XList<T>(returnList);
    }

    public XList<T> unique() {
        List<T> returnList = this.stream().distinct().collect(Collectors.toList());
        return new XList<T>(returnList);
    }

    public XList<T> combine() {
        List<T> returnList = new ArrayList<>();
        T comb;
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                comb = (T) ((this.get(i).toString()) + (this.get(j).toString()));
                returnList.add(comb);
            }
        }
        return new XList<T>(returnList);
    }


    public <R> XList<R> collect(Function<T, R> f) {
        List<R> returnList = new ArrayList<R>();
        for (R r : returnList) {
            returnList.add(f.apply((T) r));
        }
        return new XList<R>(returnList);
    }

    public String join(String string) {
        StringBuilder sB = new StringBuilder();
        for (T t : this) {
            sB.append(t.toString() + string);
        }
        return sB.toString();
    }

    public String join() {
        StringBuilder sB = new StringBuilder();
        for (T t : this) {
            sB.append(t.toString() + " ");
        }
        return sB.toString();
    }

    public void forEachWithIndex(BiConsumer<T,Integer> c) {
        for (int i = 0; i < this.size(); i++) {
            c.accept(this.get(i), i);
        }
    }
}
