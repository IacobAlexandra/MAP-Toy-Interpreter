package interpreter.model.exp;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VarExpTest {

    @Test
    void evalVarReturnsValue() throws MyException {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyHeap heap  =new MyHeap();
        symtable.put("woop",new IntValue(5));
        VarExp exp = new VarExp("woop");
        assertEquals(new IntValue(5), exp.eval(symtable,heap));
    }

    @Test
    void undefinedVariableThrows() {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyHeap heap  =new MyHeap();
        VarExp exp = new VarExp("woop");
        assertThrows(ADTException.class, () -> exp.eval(symtable,heap));
    }
}
