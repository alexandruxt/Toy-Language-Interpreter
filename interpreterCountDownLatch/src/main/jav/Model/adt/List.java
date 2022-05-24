package Model.adt;

import java.util.LinkedList;

public class List<T> implements IList<T> {
    final LinkedList<T> list;

    public List(){list = new LinkedList<>();}

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T pop(){
        return list.pop();
    }

    public T getFirstElement() {return this.list.getFirst();}

    @Override
    public LinkedList<T> getContent() {
        return list;
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T elem: list) {
            if (elem != null) {
                s.append(elem.toString());
                s.append("\n");
            }
        }
        return s.toString();
    }

    @Override
    public int get(T v) {
        return list.indexOf(v);
        //+exceptie(poz invalida)
    }

    @Override
    public void remove(int pos)
    {
        list.remove(pos);
    }

}
