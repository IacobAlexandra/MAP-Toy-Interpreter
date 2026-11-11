package interpreter.controller;

import interpreter.exceptions.*;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

import static interpreter.controller.GarbageCollector.*;

public class Controller {
    private final IRepository repo;
    private boolean displayFlag;

    // Constructor overloading, defaults displayFlag to true when omitted
    public Controller(IRepository repo) {
        this(repo, true);
    }

    public Controller(IRepository repo, boolean displayFlag){
        this.repo = repo;
        this.displayFlag = displayFlag; // for displaying the execution trace
    }

    private void display(PrgState prg){
        if (displayFlag) {
            System.out.println(prg);
        }
    }

    //one-step execution – executes the statement on top of the ExeStack (one rule S1–S6)
    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();

        if (stack.isEmpty()) throw new StatementException("program state stack is empty");

        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }
    // complete execution – executes until the ExeStack is empty;
    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        repo.logPrgStateExec();

        if (displayFlag)
            System.out.printf(
                    "%n%-50s | %-22s | %-20s | %-12s | %-24s%n",
                    "ExeStack", "SymTable", "Out", "FileTable", "Heap");

        display(prg);

        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec();
            prg.getHeap().setContent(safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().toMap().values()),
                    prg.getHeap().getContent()));
            repo.logPrgStateExec();
            display(prg);
        }
    }

}
