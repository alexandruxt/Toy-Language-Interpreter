package Model.adt;

import java.util.Map;

public interface IHeap<T> {
    int allocate(T value);
    T get(int address);
    void put(int address, T value);
    T deallocate(int address);
    Map<Integer, T> getContent();
    boolean exists(int address);
    void setContent(Map<Integer, T> map);

}
