package zad2;

import java.util.function.Function;

public interface CollectInts<T, R> extends Function<T, R>{

    R call(T input) throws NullPointerException;

    @Override
    default R apply(T input) {
        try {
            return call(input);
        } catch (NullPointerException e) {
            System.out.println("CollectInts: Pusta wartosc pointera : NullPointerException");
        }
        return null;
    }
}

