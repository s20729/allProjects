/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;

public interface Selector <T> { // Uwaga: interfejs musi być sparametrtyzowany
         <T> boolean select(T var);
}
