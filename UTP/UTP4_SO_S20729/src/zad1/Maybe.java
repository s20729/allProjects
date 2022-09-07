package zad1;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    T var;

    public Maybe(T var) {
        this.var = var;
    }
    public Maybe(){

    }

    public static <T> Maybe<T> of(T x) {
        return new Maybe<>(x);
    }

    public void ifPresent(Consumer<T> cons) {
        if(isPresent()){
            cons.accept(var);
        }
    }

    public boolean isPresent() {
        if(var == null){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(!isPresent()) {
            return "Maybe is empty";
        }else {
            try {
                this.get();
            }catch(NoSuchElementException e) {
                System.out.print("java.util.NoSuchElementException :maybe is empty");
            }
        }
        return "Maybe has value " + var;
    }
    public T get() {
        if(!isPresent()){
            throw new NoSuchElementException();
        }
        return var;
    }

    public <R> Maybe<R> map(Function<T, R> func) {
        if(isPresent()){
            R x = func.apply(this.var);
            return new Maybe<>(x);
        }
        return new Maybe<>();
    }

    public T orElse (T defVal) {
        if(isPresent()){
            return var;
        }

        return defVal;
    }

    public Maybe<T> filter(Predicate<T> pred) {
        if(isPresent()) {
            if(pred.test(var)){
                return this;
            }
        }
        return this;
    }


}