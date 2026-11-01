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

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
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

    public void setFileTable(IDictionary<StringValue, BufferedReader> newFileTable) {
        fileTable = newFileTable;
    }

    // Note: the file is opened in APPEND MODE, so running the program again adds to the old log. Delete the log
    //  files before a demonstration.
    public String toLogString() {
        var sb = new StringBuilder();
        sb.append("ExeStack:\n");
        exeStack.toList().forEach(s -> sb.append(s).append('\n'));
        sb.append("SymTable:\n");
        symTable.toMap().forEach((k, v) ->
                sb.append(k).append(" --> ").append(v).append('\n'));
        sb.append("Out:\n");
        out.toList().forEach(v -> sb.append(v).append('\n'));
        sb.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(f -> sb.append(f.val()).append('\n'));
        return sb.toString();
    }

    @Override
    public String toString() {
        String stackText = exeStack.toString();
        StringBuilder result = new StringBuilder();
        int maxWidth = 90;

        // exp for 205 chars: [0-90] [90-180] [180-205]
        for (int start = 0; start < stackText.length(); start += maxWidth) {
            int end = Math.min(start + maxWidth, stackText.length());
            String part = stackText.substring(start, end);

            if (start == 0) {
                result.append(String.format("%-90s | %-20s | %-12s | %s", part, symTable, out, fileTable));
            } else {
                result.append(System.lineSeparator());
                result.append(String.format("%-90s | %-20s | %-12s | %s", part, "", "", ""));
            }
        }

        result.append(System.lineSeparator());

        return result.toString(); // convert StringBuilder to String
    }
}
