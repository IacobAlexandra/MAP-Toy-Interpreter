package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

// varName = the identifier for where we want to SAVE the number ( it should've already been declared)

public record NewStmt(String varName, IExp exp) implements IStmt { // S11
    @Override
    public PrgState execute(PrgState state) throws MyException {

        // Check that:
        //     the variable exists
        //     the variable contains a RefValue
        //     the expression's value == the reference's(variable's) locationType

        var symTable = state.getSymTable();

        if (!symTable.isDefined(varName))
            throw new StatementException("new: " + varName + " is not declared");

        if (!(symTable.lookup(varName) instanceof RefValue ref))
            throw new StatementException("new: " + varName + " is not a reference");

        // get the value t
        IValue value = exp.eval(symTable, state.getHeap());

        if (!value.getType().equals(ref.locationType()))
            throw new StatementException("new: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());

        // "allocate heap memory"
        int address = state.getHeap().allocate(value);

        // First we need to declare the variable( defaultValue = 0 == null )
        //          and only then, separately, THROUGH UPDATE, we assign a value to it!
        //1. VarDeclStmt v... -> nothing by itself (it "doesnt exist" in the program without execute(), it's only an object "in the air")
        //2. v.execute() -> adds it to the symtable, sets its defaultValue: int* v = null (0);
        //3. NewStmt allocates a heap location
        //4. HERE!! symtable.update()  -> change defaultValue to the real Address
        symTable.update(varName, new RefValue(address, ref.locationType()));
        return state;
    }

    @Override public String toString() { return "new(" + varName + ", " + exp + ")"; }
}