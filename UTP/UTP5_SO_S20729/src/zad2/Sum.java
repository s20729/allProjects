package zad2;


import java.util.function.Function;

public interface Sum<T, R> extends Function<T, R>{

    R call(T input) throws NullPointerException;

    @Override
    default R apply(T input) {
        try {
            return call(input);
        } catch (NullPointerException e) {
            System.out.println("Sum: Pusta wartosc pointera ! : NullPointerException");
            System.exit(1);
        }
        return null;

    }
}
