package interpreter.controller;

public class Controller {

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty()) throw new StatementException("program state stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }
    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        display(prg); // only if the display flag is on
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            display(prg);
        }
    }
}
