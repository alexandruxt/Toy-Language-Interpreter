package Model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Heap<T> implements IHeap<T>{
    AtomicInteger freeVal;
    Map<Integer, T> heap;


    public Heap() {
        this.heap = new HashMap<>();
        this.freeVal = new AtomicInteger(0);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: heap.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(heap.get(elem).toString()).append('\n');
        }
        return s.toString();
    }

    @Override
    public int allocate(T value) {
        heap.put(freeVal.incrementAndGet(), value);
        return freeVal.get();
    }

    @Override
    public T get(int address) {
        return heap.get(address);
    }

    @Override
    public void put(int address, T value) {
        heap.put(address, value);
    }

    @Override
    public T deallocate(int address) {
        return heap.remove(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return heap;
    }

    @Override
    public boolean exists(int address) {
        return heap.containsKey(address);
    }

    @Override
    public void setContent(Map<Integer, T> map) {
        heap = map;
    }
}
