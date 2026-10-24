package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintStmtTest{

    @Test
    void printsInOut() throws MyException {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        PrintStmt stmt = new PrintStmt(new ValueExp(new IntValue(5)));

        PrgState state = new PrgState(exeStack,symtable,out,stmt);

        stmt.execute(state);

        assertEquals(1, out.size());
    }

}
