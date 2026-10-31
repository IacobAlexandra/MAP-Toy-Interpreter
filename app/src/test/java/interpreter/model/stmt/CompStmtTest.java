package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompStmtTest {
    @Test
    void executeCompStmtPushesOnStack() throws ADTException {
        PrintStmt first = new PrintStmt(new ValueExp(new IntValue(5)));
        PrintStmt second = new PrintStmt(new ValueExp(new IntValue(2)));
        CompStmt stmt = new CompStmt(first, second);

        PrgState state = PrgState.of(stmt);

        stmt.execute(state);

        assertEquals(first,state.getExeStack().pop());
        assertEquals(second,state.getExeStack().pop());

    }
}

