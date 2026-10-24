package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArithExpTest {

    @Test
    void evalArithOperations() throws MyException {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp left = new ValueExp(new IntValue(8));
        ValueExp right = new ValueExp(new IntValue(4));

        ArithExp exp = new ArithExp(ArithOp.ADD,left,right);
        assertEquals(new IntValue(12),exp.eval(symtable));

        exp = new ArithExp(ArithOp.MUL,left,right);
        assertEquals(new IntValue(32),exp.eval(symtable));

        exp = new ArithExp(ArithOp.SUB,left,right);
        assertEquals(new IntValue(4),exp.eval(symtable));

        exp = new ArithExp(ArithOp.DIV,left,right);
        assertEquals(new IntValue(2),exp.eval(symtable));

    }
    @Test
    void nonIntegerOperandThrows(){
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp left = new ValueExp(new IntValue(8));
        ValueExp right = new ValueExp(new BoolValue(true));

        ArithExp exp = new ArithExp(ArithOp.ADD,left,right);

        assertThrows(ExpressionException.class, () -> exp.eval(symtable));

    }

    @Test
    void divisionByZeroThrows() {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp left = new ValueExp(new IntValue(8));
        ValueExp right = new ValueExp(new IntValue(0));

        ArithExp exp = new ArithExp(ArithOp.DIV,left,right);
        assertThrows(ExpressionException.class, () -> exp.eval(symtable));
    }

}
