package interpreter.model.value;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringValueTest {
    @Test
    void stringVariableDefaultsToEmptyString() throws MyException {
        VarDeclStmt declaration = new VarDeclStmt("text", new StringType());

        PrgState state = PrgState.of(declaration);
        declaration.execute(state);

        assertEquals(new StringValue(""),state.getSymTable().lookup("text"));
    }

    @Test
    void equalStringsHaveEqualHashCodes() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}