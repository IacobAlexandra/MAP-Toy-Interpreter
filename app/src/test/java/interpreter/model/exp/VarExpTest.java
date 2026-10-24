package interpreter.model.exp;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VarExpTest {

    @Test
    void evalVarReturnsValue() throws MyException {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        symtable.put("woop",new IntValue(5));
        VarExp exp = new VarExp("woop");
        assertEquals(new IntValue(5), exp.eval(symtable));
    }

    @Test
    void undefinedVariableThrows() {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        VarExp exp = new VarExp("woop");
        assertThrows(ADTException.class, () -> exp.eval(symtable));
    }
}
