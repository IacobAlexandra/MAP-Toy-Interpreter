package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NopStmtTest {
    @Test
    void executeNopIsChill() throws MyException {
        NopStmt stmt = new NopStmt();

        PrgState state = PrgState.of(stmt);
        assertSame(state, stmt.execute(state));
    }
}
