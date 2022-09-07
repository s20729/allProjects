package zad2;

import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface Flines<T, R> extends Function<T, R> {

    R call(T input) throws IOException;

    @Override
    default R apply(T input) {
        try {
            return call(input);
        } catch (IOException e) {
            System.out.println("Flines: Błąd odczytu pliku LamComFile.txt : NoSuchFileException");
        }
        return null;
    }
}