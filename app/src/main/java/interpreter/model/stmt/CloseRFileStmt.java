package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

// S10
/*

SymTable:
varf --> "test.in"
varc --> 0

FileTable:
{"test.in" -> reader}
 */
public record CloseRFileStmt(IExp exp) implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(exp.eval(state.getSymTable(),state.getHeap()) instanceof StringValue fileName))
            throw new StatementException("closeRFile: " + exp + " is not a string");

        var fileTable = state.getFileTable();

        try{
            BufferedReader reader = state.getFileTable().lookup(fileName);
            reader.close();
            fileTable.remove(fileName);
        }
        catch (ADTException e) {
            throw new StatementException(e.getMessage());
        }
        catch (IOException e) {
            throw new StatementException("closeRFile: cannot close " + fileName  + ": " + e.getMessage());
        }

        return state;
    }
    @Override
    public String toString(){
        return "closeRFile(" + exp + ")"; // closeRFile(varf)
    }
}