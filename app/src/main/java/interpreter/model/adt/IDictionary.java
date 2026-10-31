package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.Map;

// IDictionary<K, V> / MyDictionary<K, V>: isDefined, lookup, put, update
// (wraps a HashMap)

public interface IDictionary<K,V> {

    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void put(K key, V value);
    void update(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;
    Map<K, V> toMap();
}
