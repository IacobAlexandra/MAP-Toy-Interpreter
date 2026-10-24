package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.value.*;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;    // Keeps the original program for display, since exeStack is emptied during execution.

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        exeStack.push(program);
    }

    // To ask: De ce facem si settere? Le vom folosi in urmatoarele assignment-uri sau ni s-a cerut
    //sa le cream doar ptc e good practice sa fie acolo?...

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

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

    @Override public String toString() {
        return String.format("%-90s | %-25s | %s",exeStack, symTable, out);
    }
}
