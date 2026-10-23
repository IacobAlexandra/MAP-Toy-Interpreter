package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

// Rule S3 - Type Id
public record VarDeclStmt(String name, IType type) implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException{
        // We just declare, no assignment yet, no "actual" value
        // So we'll initialise it with de default value for its type.
        //like saying "int i; => i = 0;" just a workaround, ig

        var symTable =  state.getSymTable();

        // Check that the name isn't already in use
        if(symTable.isDefined(name)){
            throw new StatementException("Variable " + name + " already declared.");
        }

        symTable.put(name, type.defaultValue());
        return state;
        //NOTE: I believe it's redundant to return the Prg State, right? Could've used void
            // cus we already make the changes in the original object
    }
}
