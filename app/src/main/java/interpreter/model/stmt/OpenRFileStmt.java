package interpreter.model.stmt;

import interpreter.exceptions.*;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.value.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* f = Eval(Exp);

   open the file named f with a BufferedReader;
   add {f -> reader} to FileTable

 */
public record OpenRFileStmt(IExp exp) implements IStmt { // S8
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(exp.eval(state.getSymTable(),state.getHeap()) instanceof StringValue fileName))
            throw new StatementException("openRFile: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (fileTable.isDefined(fileName))
            throw new StatementException("openRFile: " + fileName + " is already open");
        try {
            // no try-with-resources: the file must stay open until closeRFile
            var reader = new BufferedReader(new FileReader(fileName.val()));
            fileTable.put(fileName, reader);
        } catch (IOException e) {
            throw new StatementException("openRFile: cannot open " + fileName + ": " + e.getMessage());
        }
        return state;
    }
    @Override public String toString() { return "openRFile(" + exp + ")"; }
}

