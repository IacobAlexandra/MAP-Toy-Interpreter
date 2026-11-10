package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//allocate returns 1, 2, 3 …; get and update of a missing address throw
public class MyHeapTest {

    @Test
    void adressesAreAllocatedIncrementally(){
        MyHeap heap = new MyHeap();
        int address1 = heap.allocate(new IntValue(1));
        int address2 = heap.allocate(new IntValue(2));
        int address3 = heap.allocate(new IntValue(3));

        assertEquals(1,address1);
        assertEquals(2,address2);
        assertEquals(3,address3);
    }

    @Test // no value with given address
    void getHeapThrows(){
        MyHeap heap = new MyHeap();
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void updateHeapThrows(){
        MyHeap heap = new MyHeap();
        assertThrows(ADTException.class, () -> heap.update(1,new IntValue(1)));
    }

}
