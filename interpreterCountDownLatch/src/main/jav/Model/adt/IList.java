package Model.adt;

import java.util.LinkedList;

public interface IList<T> {
    public T pop();
    void add(T v);
    LinkedList<T> getContent();
    String toString();
    boolean empty();
    void clear();
    int size();
    int get(T v);
    void remove(int pos);
}
