package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssignStmtTest {
    @Test
    void executeAssignAssigns() throws MyException {
        VarDeclStmt declStmt = new VarDeclStmt("woop",new IntType());
        ValueExp exp = new ValueExp(new IntValue(5));
        AssignStmt assignStmt = new AssignStmt("woop",exp);

        PrgState state = PrgState.of(declStmt);

        declStmt.execute(state);
        assignStmt.execute(state);

        assertEquals(new IntValue(5),state.getSymTable().lookup("woop"));
    }

    @Test
    void undeclaredVariableAssignThrows(){
        ValueExp exp = new ValueExp(new IntValue(5));
        AssignStmt assignStmt = new AssignStmt("woop",exp);

        PrgState state = PrgState.of(assignStmt);

        assertThrows(StatementException.class, () -> assignStmt.execute(state));
    }

    @Test
    void typesNotMatchAssignThrows() throws MyException{
        VarDeclStmt declStmt = new VarDeclStmt("woop",new BoolType());
        ValueExp exp = new ValueExp(new IntValue(5));
        AssignStmt assignStmt = new AssignStmt("woop",exp);

        PrgState state = PrgState.of(assignStmt);

        declStmt.execute(state);
        assertThrows(StatementException.class, () -> assignStmt.execute(state));
    }
}
