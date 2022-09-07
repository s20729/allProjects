/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;


public interface Mapper <T, S>{ // Uwaga: interfejs musi być sparametrtyzowany
    <S> Integer map (T var);

}  
