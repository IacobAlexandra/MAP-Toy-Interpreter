package interpreter.model.stmt;
import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.*;
import interpreter.model.PrgState;
import interpreter.model.exp.*;

import interpreter.model.type.*;
import interpreter.model.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;

class FileStmtTest {
    @TempDir
    Path dir; // a new empty folder for every test
    private PrgState state;
    private ValueExp file,fileFail; // the expression "<dir>/numbers.in"
    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Path fFail = dir.resolve("numbersFail.in");
        Files.writeString(f, "7\n");
        Files.writeString(fFail, "a\n");

        file = new ValueExp(new StringValue(f.toString()));
        fileFail = new ValueExp(new StringValue(fFail.toString()));

        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }
    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined((StringValue) file.eval(state.getSymTable(),state.getHeap())));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
        new CloseRFileStmt(file).execute(state);
    }
    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
        new CloseRFileStmt(file).execute(state);
    }
    @Test
    void openingNonStringExpThrows() {
        ValueExp integer = new ValueExp(new IntValue(3));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(integer).execute(state));
    }

    @Test
    void openingNonExistingFileThrows() {
        ValueExp fakeFile = new ValueExp(new StringValue("fakefile"));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(fakeFile).execute(state));
    }

    @Test
    void readInNonDeclaredVariableThrows() throws MyException{
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "not_declared").execute(state));
        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void readInNonIntVariableThrows() throws MyException{
        new VarDeclStmt("stringVar", new StringType()).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "stringVar").execute(state));
    }

    @Test
    void readFileNotOpenedThrows() throws Exception{
        assertThrows(StatementException.class, () -> new ReadFileStmt(fileFail, "x").execute(state));
    }
    @Test
    void readNonIntegerLineThrows() throws Exception{
        new OpenRFileStmt(fileFail).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(fileFail, "x").execute(state));
        new CloseRFileStmt(fileFail).execute(state);
    }

    @Test
    void closingFileRemovesFromFileTable() throws MyException{
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        assertFalse(state.getFileTable().isDefined((StringValue) file.eval(state.getSymTable(),state.getHeap())));
    }
    @Test
    void closingFileNotOpenedThrows() throws MyException{
        assertThrows(StatementException.class, () -> new CloseRFileStmt(fileFail).execute(state));
    }
}
