package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
// Rule S6 - nop
// No operation statement, my fav
public record NopStmt() implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
    }
}
