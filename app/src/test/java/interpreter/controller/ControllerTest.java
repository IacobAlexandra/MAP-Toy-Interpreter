package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void emptyStack() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();

        var repo = new Repository(state, dir.resolve("log.txt").toString());
        Controller controller = new Controller(repo, false);
        controller.allStep();

        assertThrows(StatementException.class, () -> controller.oneStep(state));
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
    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }
    @Test
    void fileExampleWithEmptyFilePrintsZeros() throws Exception {
        Path input = dir.resolve("empty.in");
        Files.writeString(input, "");
        PrgState state = run(Examples.fileExample(input.toString()));
        assertEquals(2, state.getOut().size());
        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));

        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void example5PrintsFive() throws MyException{
        PrgState state = run(Examples.EX5);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void example6PrintsReferences() throws MyException {
        PrgState state = run(Examples.EX6);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(2, state.getOut().size());
        assertEquals(new RefValue(1, new IntType()), state.getOut().get(0));
        assertEquals(new RefValue(2, new RefType(new IntType())),
                state.getOut().get(1));
    }

    @Test
    void example7Prints20And25() throws MyException {
        PrgState state = run(Examples.EX7);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(2, state.getOut().size());
        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(new IntValue(25), state.getOut().get(1));
    }

    @Test
    void example8Prints20And35() throws MyException {
        PrgState state = run(Examples.EX8);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(2, state.getOut().size());
        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(new IntValue(35), state.getOut().get(1));
    }

    @Test
    void example9Prints20() throws MyException {
        PrgState state = run(Examples.EX9);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(20), state.getOut().get(0));
    }

    @Test
    void example10Prints4To0() throws MyException {
        PrgState state = run(Examples.EX10);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(5, state.getOut().size());

        for (int i = 0; i < 5; i++) {
            assertEquals(new IntValue(4 - i), state.getOut().get(i));
        }
    }


}