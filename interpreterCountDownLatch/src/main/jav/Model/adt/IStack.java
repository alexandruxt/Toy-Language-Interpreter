package Model.adt;
import Exceptions.AdtException;

public interface IStack<T> {

    T pop() throws AdtException;
    void push(T v);
    boolean isEmpty();
//    T top() throws RuntimeException;
    int size();
    String toString();
    java.util.List<T> getContent();

}

