package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.value.*;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;    // Keeps the original program for display, since exeStack is emptied during execution.

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IDictionary<StringValue, BufferedReader> fileTable, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        exeStack.push(program);
    }

    public static PrgState of(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new MyDictionary<>(), program);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }
    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public IList<IValue> getOut() {
        return out;
    }
    public IDictionary<StringValue, BufferedReader> getFileTable() {return fileTable;}
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setExeStack(IStack<IStmt> newExeStack) {
        exeStack = newExeStack;
    }
    public void setSymTable(IDictionary<String, IValue> newSymTable) {
        symTable = newSymTable;
    }
    public void setOut(IList<IValue> newOut) {
        out = newOut;
    }
    public void setFileTable(IDictionary<StringValue, BufferedReader> newFileTable) {fileTable = newFileTable;}

    @Override public String toString() {
        return String.format("%-90s | %-25s | %-25%s | %-25s ",exeStack, symTable, out, fileTable);
    }
}
