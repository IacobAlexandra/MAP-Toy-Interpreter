package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompStmtTest {
    @Test
    void executeCompStmtPushesOnStack() throws ADTException { //semnat, sefa la numit teste
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        PrintStmt first = new PrintStmt(new ValueExp(new IntValue(5)));
        PrintStmt second = new PrintStmt(new ValueExp(new IntValue(2)));
        CompStmt stmt = new CompStmt(first, second);

        PrgState state = new PrgState(exeStack,symtable,out,stmt);

        stmt.execute(state);

        assertEquals(first,exeStack.pop());
        assertEquals(second,exeStack.pop());

    }
}

