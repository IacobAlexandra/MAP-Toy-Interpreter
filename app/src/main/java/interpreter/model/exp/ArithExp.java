package interpreter.model.exp;

import interpreter.exceptions.*;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {

        // NOTE: instanceof does 2 things here:
        //  1. Verifies if left is an int
        //  2. extract the value in n1
        //(use left.eval(symTable) instanceof IntValue if u only want the verification)
        if (!(left.eval(symTable) instanceof IntValue(int n1)))
            throw new ExpressionException("first operand is not an integer");

        if (!(right.eval(symTable) instanceof IntValue(int n2)))
            throw new ExpressionException("second operand is not an integer");

        return switch (op) {
            case ADD -> new IntValue(n1 + n2);
            case SUB -> new IntValue(n1 - n2);
            case MUL -> new IntValue(n1 * n2);
            case DIV -> {
                if (n2 == 0) throw new ExpressionException("division by zero");
                yield new IntValue(n1 / n2);
            }
        };
    }
    @Override
    public String toString() { return "(" + left + " " + op + " " + right + ")"; }
}