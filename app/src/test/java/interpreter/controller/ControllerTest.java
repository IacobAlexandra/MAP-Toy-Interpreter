package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//NOTE:  Run all tests with ./gradlew test; ./gradlew build compiles and tests.
// The HTML report is in app/build/reports/tests/test/index.html.
class ControllerTest {
    private static PrgState run(IStmt program) throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), program);
        new Controller(new Repository(state), false).allStep(); // display off
        return state;
    }

    @Test
    void emptyStack() throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                            new MyList<>(), new NopStmt());

        //NOTE: the PrgState constructor automatically adds the
        //  program on the stack ( exeStack.push(program); )

        // Remove the program
        state.getExeStack().pop();

        Controller controller = new Controller(new Repository(state), false);
        assertThrows(StatementException.class, () -> controller.oneStep(state));
        // Syntax is: assertThrows(ExceptionType.class, () -> operation());
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }
}