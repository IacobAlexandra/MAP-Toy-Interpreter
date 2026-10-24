package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// put / lookup / update, lookup of a missing key throws
public class MyDictionaryTest{

    @Test
    void putAndUpdateInDictionary() throws ADTException {
        MyDictionary<String,IntValue> dict= new MyDictionary<>();
        IntValue val1 = new IntValue(5);
        IntValue val2 = new IntValue(10);

        dict.put("woop",val1);

        assertEquals(val1, dict.lookup("woop"));

        dict.update("woop",val2);

        assertEquals(val2, dict.lookup("woop"));
    }

    @Test
    void lookupMissingKeyDictThrows() {
        MyDictionary<String,IntValue> dict = new MyDictionary<>();
        assertThrows(ADTException.class, () -> dict.lookup("toptop"));
    }
}
