package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;


// S5 - if Exp then Stmt1 else Stmt2
public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {

    @Override public PrgState execute(PrgState state) throws MyException {

        //NOTE: in C/C++, yes, if(3) is true, if(n), where n=3 is true
        //  BUT, in Java, if(3) doesn't compile. it MUST be a boolean
        // So, we MUST to verify that it's a bool, otherwise we throw an exception
        // NOTE: instanceof does 2 things here:
        //  1. Verifies that its a bool
        //  2. extracts the value in b

        if (!(cond.eval(state.getSymTable()) instanceof BoolValue(boolean b))){
            throw new StatementException("Condition is not a boolean.");
        }

        // NOTE: we don't execute now, we just add it on the stack
        if(b){
            state.getExeStack().push(thenS);
        }
        else{
            state.getExeStack().push(elseS);
        }
        return state;
    }

}
