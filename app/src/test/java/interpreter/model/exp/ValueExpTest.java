package interpreter.model.exp;

import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueExpTest {

    @Test
    void evalValReturnsConstant(){
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp exp = new ValueExp(new IntValue(5));
        assertEquals(new IntValue(5), exp.eval(symtable));
    }
}
