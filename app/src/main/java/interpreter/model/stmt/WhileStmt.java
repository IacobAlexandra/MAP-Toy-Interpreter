package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

//A loop needs no Java loop: WhileStmt puts itself back on the stack,
//  under its body (sTaCK), as long as the condition is true.

// Syntax: (while (v > 0) (print(v); v = v - 1))
// Method Call Exp: WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());

//EFFECT:
// if Eval(exp) is true:
//      push while (exp) s,
//      then push s;
//  if false: nothing (the loop ends)

public record WhileStmt(IExp cond, IStmt body) implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue value = cond.eval(state.getSymTable(), state.getHeap());
        IStack<IStmt> exeStack = state.getExeStack();

        if(!(value instanceof BoolValue boolValue)){
            throw new StatementException("WhileStmt: Condition "+ cond + " is not a boolean.");
        }

        if(boolValue.val()) {
            exeStack.push(this); // this = current object = this WhileStmt object
            exeStack.push(body);
        }

        return state;
    }

    @Override public String toString() { return "(while (" + cond + ") " + body + ")"; }
}
