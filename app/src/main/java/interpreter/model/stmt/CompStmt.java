package interpreter.model.stmt;

import interpreter.model.PrgState;

// Rule S1 - Stmt1 ; Stmt2

//COMPOUND COMPOUND COMPOUND Statement
public record CompStmt(IStmt first, IStmt second) implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return state;
    }
    @Override public String toString() { return "(" + first + "; " + second + ")"; }
}