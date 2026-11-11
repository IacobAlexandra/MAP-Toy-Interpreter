package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.value.*;

import java.io.BufferedReader;
import java.util.TreeMap;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;    // Keeps the original program for display, since exeStack is emptied during execution.
    private final IHeap heap;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IDictionary<StringValue,BufferedReader> fileTable, IHeap heap, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = program;
        exeStack.push(program);
    }

    public static PrgState of(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new MyDictionary<>(), new MyHeap(), program);
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

    public IHeap getHeap() {return heap;}

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
        sb.append("Heap:\n");
        // "Sort the addresses, so that two logs of the same program are identical"
        new TreeMap<>(heap.getContent()).forEach((address, value) ->
                sb.append(address).append(" --> ").append(value).append('\n'));

        return sb.toString();
    }

    // Extract the part of a column that belongs on the given row
    private String columnPart(String text, int width, int row) {
        int start = row * width; // width 50: row 0 starts at 0, row 1 at 50 ...

        if (start >= text.length()) {
            return "";
        }

        int end = Math.min(start + width, text.length());   // Avoid going past the end of the text on the last row

        return text.substring(start, end); // [start, end)
    }

    @Override
    public String toString() {
        String[] columns = {
                exeStack.toString(),
                symTable.toString(),
                out.toString(),
                fileTable.toString(),
                heap.toString()
        };

        int[] maxWidths = {50, 22, 20, 12, 24}; // = max chars per row for each column

        // Find how many rows the longest column needs
        int rows = 1;
        for (int i = 0; i < columns.length; i++) {
            int neededRows = (columns[i].length() + maxWidths[i] - 1) / maxWidths[i];
            rows = Math.max(rows, neededRows);
        }

        StringBuilder result = new StringBuilder();

        for (int row = 0; row < rows; row++) {
            // Extract each column's part for this row
            // %-50s = left-aligned + padded with spaces to at least 50 chars
            // %n = newline, at the end of the row
            result.append(String.format(
                    "%-50s | %-22s | %-20s | %-12s | %-24s%n",
                    columnPart(columns[0], maxWidths[0], row),
                    columnPart(columns[1], maxWidths[1], row),
                    columnPart(columns[2], maxWidths[2], row),
                    columnPart(columns[3], maxWidths[3], row),
                    columnPart(columns[4], maxWidths[4], row)));
        }

        return result.toString();
    }
}
