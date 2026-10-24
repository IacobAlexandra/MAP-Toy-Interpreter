package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;



public class MyListTest {

    @Test
    void add2InList() throws ADTException {
        MyList<IntValue> list = new MyList<>();
        IntValue val = new IntValue(5);

        list.add(val);

        assertEquals(1,list.size());
        assertEquals(val, list.get(0));
    }

    @Test
    void getFromEmptyListThrows() {
        MyList<IntValue> list = new MyList<>();

        assertThrows(ADTException.class, () -> list.get(0));
    }
}
