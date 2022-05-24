package Model.adt;
import Exceptions.AdtException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<>();
    }


    @Override
    public void add(T1 v1, T2 v2) throws AdtException {
         if (dictionary.containsKey(v1))
             throw new AdtException("Element already exists");
         this.dictionary.put(v1, v2);

    }

    @Override
    public void update(T1 v1, T2 v2) throws AdtException {
        if (!dictionary.containsKey(v1)){
            throw new AdtException("Element does not exist!");
        }
        this.dictionary.replace(v1,v2);
    }

    @Override
    public Map<T1, T2> getContent() {return dictionary;}

    @Override
    public T2 lookup(T1 id) {
        return dictionary.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: dictionary.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(dictionary.get(elem).toString()).append('\n');
        }
        return s.toString();
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public Collection<T2> values() {
        return this.dictionary.values();
    }
    @Override
    public void remove(T1 id) throws AdtException {
        if (dictionary.containsKey(id))
            this.dictionary.remove(id);
        else
            throw new AdtException("Element does not exost!");
    }

}
