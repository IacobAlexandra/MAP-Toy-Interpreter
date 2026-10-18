package interpreter.model.adt;

import java.util.Deque;
import java.util.ArrayDeque;
import interpreter.exceptions.ADTException;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack = new ArrayDeque<>(); // java.util
    @Override public void push(T v) { stack.push(v); }
    @Override public T pop() throws ADTException {
        if (stack.isEmpty()) throw new ADTException("pop from an empty stack");
        return stack.pop();
    }
    @Override public boolean isEmpty() { return stack.isEmpty(); }
    @Override public String toString() { return stack.toString(); }
}