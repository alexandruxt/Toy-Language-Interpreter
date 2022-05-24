package Model.adt;

import Exceptions.AdtException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Stack<T> implements IStack<T> {
    final java.util.Stack<T> stack;

    public Stack(){this.stack = new java.util.Stack<>();}

    @Override
    public T pop() throws AdtException {
        if (stack.isEmpty()){
            throw new AdtException("Stack is empty");
        }
        return stack.pop();
    }

//    public T top() throws RuntimeException{
//        if (stack.isEmpty()) {
//            throw new RuntimeException("Stack is empty");
//        }
//        return stack;
//    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        //return this.stack.toString();
        StringBuilder s = new StringBuilder();
        ListIterator<T> stackIt = stack.listIterator(stack.size());
        while (stackIt.hasPrevious()) {
            s.append(stackIt.previous().toString()).append('\n');
        }
        return s.toString();
    }

    @Override
    public List<T> getContent() {
        return new ArrayList<>(stack);
    }

}
