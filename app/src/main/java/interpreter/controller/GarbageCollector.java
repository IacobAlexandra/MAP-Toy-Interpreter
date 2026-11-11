package interpreter.controller;

// "A heap location is garbage when the program can no longer reach it. "

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// UNSAFE: meaning it deletes TOO MUCH
//      There are cases when in the heap there is allocated a value, but because of some .update() we lost all pointers to it
//          the Unsafe Garbage Collector will mistakenly remove that value jut bcs it doesn't have a variable that is a pointer to it
//                      aka it doesn't appear in the SymTable
// SAFE: ...
public final class GarbageCollector {
    private GarbageCollector() {
    }

    /**
     * The addresses of all the RefValues from the SymTable.
     */
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        /*
                ~ Collection<T> = a Java interface representing a group of elements of type T.
                      It provides common operations such as add(), remove(), size() and iteration.

                      List and Set extend this interface: a List keeps elements in order and allows duplicates
                                                            while a Set does not allow duplicates.

                ~ stream() =  Process the collection's elements through a sequence of operations (filter, map, collect) (naturally, it also works for List and Set)

                ~ Stream<T> = an object which allows to traverse and process elements (it's NOT a Collection, List or Set. You can't display its elements nicely)
         */

        // exp: [IntValue(5), RefValue(1, int), RefValue(1, int)] → {1}
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)     // keep only reference values (ignore ints, booleans, strings)
                //  Stream<IValue>, produces -> RefValue(1, int), RefValue(1, int)]
                .map(v -> ((RefValue) v).address())     // replace each reference value with its address
                //  Stream<Integer>, produces -> 1,1
                .collect(Collectors.toSet());                 // collect the addresses into a Set (=  removes duplicates)
        // Set<Integer>, contains ->  {1}
    }

    /**
     * UNSAFE: keeps only the heap entries referred to directly from the SymTable.
     */
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr,
                                                              Map<Integer, IValue> heap) {

        //entrySet() = Java method for Map. return a Set with all <key,pair> values from the Map
        //                  Set<Map.Entry<Integer, IValue>>
        //                  ~ Entry = a single <key,pair> value from the Map
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey())) // keep only heap(map) entries whose address(key)
                //      is contained in a reference from the SymTable

                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // collect the entries in a Map<address, value>
    }

    // static means the method belongs to the class, so we call it without creating an object. They only need the given parameters, without the GarbageCollector's own parameters
    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {

        //* NOTE: full example at the end *

        // roots = the set of all the addresses from the SymTable ( == the set returned by getAddrFromSymTable())

        // We need to create a copy of `roots` bcs otherwise we'd directly modify `roots`, the address Set
        //      we need to keep `roots` in its original form to do our repetition thing

        // Set is an interface, it cannot be instantiated.
        //      so, for that we use HashSet, a concrete implementation of Set (TreeSet would've work just as good, it literally doesn't matter)

        Set<Integer> reachable = new HashSet<>(roots);  // start as exact copy of roots
        boolean changed = true;

        while (changed) {
            changed = false;


            // Start with addresses from SymTable, track them in the heap through the "Reference Chain"
            //      ~repeat same process~ until no new address is found
            for (int address : new HashSet<>(reachable)) {
                IValue value = heap.get(address);  // .add() in a Set returns true if the new element was not a duplicate, i.e it was added succesfully into the Set

                if (value instanceof RefValue ref) {
                    if (reachable.add(ref.address())) {
                        changed = true;
                    }
                }
            }
        }

        return reachable;
    }


    /**
     * SAFE: also keeps the locations reachable through the heap.
     */
    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> symTableAddr,
                                                     Map<Integer, IValue> heap) {

        Set<Integer> reachable = reachableAddresses(symTableAddr, heap);

        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
/*
reachableAddress() EXAMPLE:

SymTable:
a -> RefValue(2, Ref int)
v -> RefValue(3, int)

Heap:
1 -> IntValue(20)
2 -> RefValue(1, int)
3 -> IntValue(30)
4 -> IntValue(99)

reachableAddress():
Set<> reachable = Set<> roots = {2,3}
changed = true

1st Iteration:  changed = false;
                    value = heap.get(2) = RefValue(1, int) => reachable.add(1) returns TRUE => {1,2,3}
                            changed = true
                    value = heap.get(3) = IntValue(30) => skip
                changed = true;

2nd Iteration: changed = false;
                   value = heap.get(1) = IntValue(20) => skip
                   value = heap.get(2) = RefValue(1, int) => reachable.add(1) returns FALSE (duplicate)
                   value = heap.get(3) = IntValue(30) => skip
               changed = false; stop.
 */
