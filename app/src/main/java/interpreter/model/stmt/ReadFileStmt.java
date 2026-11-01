package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

// S9

/*
string varf; varf = "test.in"; openRFile(varf); int varc;
readFile(varf, varc);

SymTable:
varf --> "test.in"
varc --> 0

FileTable:
test.in
(new ReadFileStmt(new VarExp("varf"), "varc")

 */
public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        IValue id;

        //SYMTABLE
        // 1. varName = the identifier for where we want to SAVE the number ( it should've already been declared)
        try{
            id = symTable.lookup(varName);
            if (!(id instanceof IntValue))
                throw new StatementException("ReadFile: " + varName + " id is not an integer");
        }
        catch (ADTException e){
            throw new StatementException(e.getMessage());
        }

        // 2. get fileName
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("ReadFile: " + exp + " is not a string");

        // FILETABLE
        try{
            BufferedReader reader = state.getFileTable().lookup(fileName);
            String line = reader.readLine();
            IntValue n;

            if (line == null) {
                n = new IntValue(0);
            } else {
                n = new IntValue(Integer.parseInt(line.strip()));
            }

            symTable.update(varName, n);
        }
        // 1. if file "fileName" hasn't been been opened yet ( aka if it's in the File Table or not)
        catch (ADTException e) {
            throw new StatementException(e.getMessage());
        }
        catch (IOException e) {
            throw new StatementException("ReadFile: cannot read " + fileName  + ": " + e.getMessage());
        }
        catch (NumberFormatException e){
            throw new StatementException("ReadFile: file line is not a valid integer: " + e.getMessage());
        }

        return state;
    }
    @Override
    public String toString(){
        return "readFile(" + exp + ", " + varName + ")"; // readFile(varf, varc)
    }
}

