package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WriteHeapStmtTest {

    @Test
    void writeHeapUpdatesLocation() throws MyException {
        // 1. "create" reference
        VarDeclStmt vStmt = new VarDeclStmt("v", new RefType(new IntType())); // int* v
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state); // add `int* v` to symTable

        //2. Allocate something in the heap ("initialize")
        NewStmt newStmt = new NewStmt("v", new ValueExp(new IntValue(5)));    // automatically allocates at address 1 (nextFree from MyHeap)
        newStmt.execute(state); // allocate

        //3. Update variable in heap at location
        WriteHeapStmt wh = new WriteHeapStmt("v",new ValueExp(new IntValue(10)));
        wh.execute(state);

        ReadHeapExp rhExp = new ReadHeapExp(new VarExp("v"));

        assertEquals(new IntValue(10), rhExp.eval(state.getSymTable(),state.getHeap()) );
    }

    @Test
    void variableNotAReferenceThrows() throws MyException{

        VarDeclStmt vStmt = new VarDeclStmt("v", new IntType()); // int v, should've been RefType
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state); // add `int* v` to symTable


        WriteHeapStmt wh = new WriteHeapStmt("v",new ValueExp(new IntValue(10)));

        assertThrows(StatementException.class, () -> wh.execute(state));
    }

    @Test
    void addressNotInHeapThrows() throws MyException {

        // 1. "create" reference
        VarDeclStmt vStmt = new VarDeclStmt("v", new RefType(new IntType())); // int* v
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state); // add `int* v` to symTable

        //3. Update variable in heap at location
        WriteHeapStmt wh = new WriteHeapStmt("v",new ValueExp(new IntValue(10)));

        assertThrows(StatementException.class, () -> wh.execute(state));
    }

    @Test
    void valueOfWrongTypeThrows() throws MyException{
        // 1. "create" reference
        VarDeclStmt vStmt = new VarDeclStmt("v", new RefType(new StringType())); // string* v
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state); // add `string* v` to symTable

        //2. Allocate something in the heap ("initialize")
        // NOTE: In our ToyLanguage, the initial locationType is FINAL
        NewStmt newStmt = new NewStmt("v", new ValueExp(new StringValue("aaa")));  //  init locationType with String
        newStmt.execute(state); // allocate

        //3. Update variable in heap at location
        WriteHeapStmt wh = new WriteHeapStmt("v",new ValueExp(new IntValue(10)));   // try to update with int

        assertThrows(StatementException.class, () -> wh.execute(state));
    }
}
