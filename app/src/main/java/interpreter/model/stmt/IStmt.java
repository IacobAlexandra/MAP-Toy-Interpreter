package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

// Each statement implements execute, which applies its rule to the program state and returns the state
//(later, when the language gets threads, the returned state will be used).

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    // why do we return PrgState, doesnt it returnthe oriinal object anyways?
}