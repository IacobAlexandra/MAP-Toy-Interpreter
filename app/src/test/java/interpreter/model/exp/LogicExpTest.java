package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicExpTest {
    @Test
    void evalLogicReturnsValue() throws MyException {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp left = new ValueExp(new BoolValue(true));
        ValueExp right = new ValueExp(new BoolValue(false));

        LogicExp exp = new LogicExp(LogicOp.OR, left, right);
        assertEquals(new BoolValue(true), exp.eval(symtable));

        exp = new LogicExp(LogicOp.AND, left, right);
        assertEquals(new BoolValue(false), exp.eval(symtable));
    }

    @Test
    void nonBooleanOperandThrows(){
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp left = new ValueExp(new BoolValue(true));
        ValueExp right = new ValueExp(new IntValue(1));

        LogicExp exp = new LogicExp(LogicOp.OR, left, right);

        assertThrows(ExpressionException.class, () -> exp.eval(symtable));
    }

}
