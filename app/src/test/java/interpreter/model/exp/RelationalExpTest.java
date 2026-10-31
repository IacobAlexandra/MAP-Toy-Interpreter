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

public class RelationalExpTest {

    @Test
    void evalRelationalOperations() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();

        ValueExp more = new ValueExp(new IntValue(8));
        ValueExp less = new ValueExp(new IntValue(4));

        // LT, LE, EQ, NE, GT, GE
        RelationalExp ltF = new RelationalExp(RelOp.LT, more, less);
        RelationalExp ltT = new RelationalExp(RelOp.LT, less, more);
        assertEquals(new BoolValue(false), ltF.eval(symTable));
        assertEquals(new BoolValue(true), ltT.eval(symTable));

        RelationalExp leF = new RelationalExp(RelOp.LE, more, less);
        RelationalExp leT = new RelationalExp(RelOp.LE, more, more);
        assertEquals(new BoolValue(false), leF.eval(symTable));
        assertEquals(new BoolValue(true), leT.eval(symTable));

        RelationalExp eqF = new RelationalExp(RelOp.EQ, more, less);
        RelationalExp eqT = new RelationalExp(RelOp.EQ, more, more);
        assertEquals(new BoolValue(true), eqT.eval(symTable));
        assertEquals(new BoolValue(false), eqF.eval(symTable));

        RelationalExp neT = new RelationalExp(RelOp.NE, more, less);
        RelationalExp neF = new RelationalExp(RelOp.NE, more, more);
        assertEquals(new BoolValue(true), neT.eval(symTable));
        assertEquals(new BoolValue(false), neF.eval(symTable));

        RelationalExp gtT = new RelationalExp(RelOp.GT, more, less);
        RelationalExp gtF = new RelationalExp(RelOp.GT, less, more);
        assertEquals(new BoolValue(true), gtT.eval(symTable));
        assertEquals(new BoolValue(false), gtF.eval(symTable));

        RelationalExp geT = new RelationalExp(RelOp.GE, more, less);
        RelationalExp geF = new RelationalExp(RelOp.GE, less, more);
        assertEquals(new BoolValue(true), geT.eval(symTable));
        assertEquals(new BoolValue(false), geF.eval(symTable));

    }

    @Test
    void nonIntegerOperandThrows()  {
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        ValueExp left = new ValueExp(new IntValue(8));
        ValueExp right = new ValueExp(new BoolValue(true));

        RelationalExp exp = new RelationalExp(RelOp.LT, right, left);
        assertThrows(ExpressionException.class, () -> exp.eval(symtable));
    }
}
