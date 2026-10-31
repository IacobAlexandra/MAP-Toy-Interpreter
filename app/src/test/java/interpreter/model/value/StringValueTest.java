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

        PrgState state = new PrgState(new interpreter.model.adt.MyStack<>(), new interpreter.model.adt.MyDictionary<>(),
                new interpreter.model.adt.MyList<>(), declaration
        );

        declaration.execute(state);

        assertEquals(state.getSymTable().lookup("text"),new StringValue(""));
    }

    @Test
    void equalStringsHaveEqualHashCodes() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}