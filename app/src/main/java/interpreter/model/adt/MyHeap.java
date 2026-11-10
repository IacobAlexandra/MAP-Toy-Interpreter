package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;
import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IHeap {
    // <Integer = address = key, IValue = the value stored at that address(5, "text",true, another address)>
    private Map<Integer, IValue> heap = new HashMap<>();
    private int nextFree = 1; // address 0 means null

    @Override
    public int allocate(IValue value) {
        int address = nextFree++;
        heap.put(address, value);

        return address;
    }

    @Override
    public IValue get(int address) throws ADTException{
        if(!heap.containsKey(address)){
            throw new ADTException("No value with given address");
        }
        return heap.get(address);
    }

    @Override
    // update oldValue at address `address` with newValue (IValue value)
    public void update(int address, IValue value) throws ADTException{
        if(!heap.containsKey(address)){
            throw new ADTException("No value with given address");
        }
        // The key exists, so put() only updates its value!, it doesn't add a new entry
        heap.put(address, value);
    }

    @Override
    public boolean isDefined(int address){ return heap.containsKey(address);}

    @Override
    // getContent (Map.copyOf(heap))
    public Map<Integer, IValue> getContent(){ return Map.copyOf(heap);}

    // NOTE: the heap in MyHeap is NOT final
    @Override
    public void setContent(Map<Integer, IValue> content) { heap = new HashMap<>(content);}

    @Override public String toString() { return heap.toString(); }

}