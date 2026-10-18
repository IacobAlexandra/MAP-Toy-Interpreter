package interpreter.model.adt;

import interpreter.exceptions.ADTException;

// IList<T> / MyList<T>: add, get, size (wraps an ArrayList)
public interface IList<T> {
    void add(T value);
    T get(int index) throws ADTException;
    int size();

}
