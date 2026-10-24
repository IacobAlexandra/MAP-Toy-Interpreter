package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NopStmtTest {
    @Test
    void executeNopIsChill() throws MyException {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        NopStmt stmt = new NopStmt();

        PrgState state = new PrgState(exeStack, symtable, out, stmt);
        assertSame(state, stmt.execute(state));
    }
}
