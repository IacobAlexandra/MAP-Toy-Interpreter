package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    // Reminder 1 : Constructor - Java automatically generates a constructor with no parameters if u dont write ur custom constructor (the HashMap is created internally, we don't pass it as parameter, so that's why we don't need it in the constructor
    // Reminder 2 : Final : final prevents a field being reassigned, so we'll have the same instance of a HashMap. We can just change it's contents, but not the hashMap itself
    private final HashMap<K, V> dictionary = new HashMap<>();

    @Override public boolean isDefined(K key){ return dictionary.containsKey(key);}

    @Override public V lookup(K key) throws ADTException{
        if(!dictionary.containsKey(key)){
            throw new ADTException("No dictionary entry with given key");
        }
        return dictionary.get(key);
    }

    @Override public void put(K key, V value){ dictionary.put(key, value); }

    @Override public void update(K key, V value) throws ADTException{
        if(!dictionary.containsKey(key)){
            throw new ADTException("No dictionary entry with given key");
        }
        // The key exists, so put() only updates its value. It doesn't add a new entry
        dictionary.put(key, value);

    }
    @Override public String toString() {return dictionary.toString();}
}
