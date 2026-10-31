package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IfStmtTest {

    @Test
    void ifCondPushesWhatItShouldPush() throws MyException {
        ValueExp exp = new ValueExp(new BoolValue(true));
        ValueExp p1 = new ValueExp(new IntValue(5));
        ValueExp p2 = new ValueExp(new IntValue(1));

        PrintStmt pStmt1 = new PrintStmt(p1);
        PrintStmt pStmt2 = new PrintStmt(p2);

        IfStmt stmt = new IfStmt(exp,pStmt1,pStmt2);

        PrgState state = PrgState.of(stmt);
        stmt.execute(state);

        assertEquals(pStmt1, state.getExeStack().pop());
    }

    @Test
    void condNonBooleanThrows(){
        ValueExp exp = new ValueExp(new IntValue(0));
        ValueExp p1 = new ValueExp(new IntValue(5));
        ValueExp p2 = new ValueExp(new IntValue(1));

        PrintStmt pStmt1 = new PrintStmt(p1);
        PrintStmt pStmt2 = new PrintStmt(p2);

        IfStmt stmt = new IfStmt(exp,pStmt1,pStmt2);

        PrgState state = PrgState.of(stmt);
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}
