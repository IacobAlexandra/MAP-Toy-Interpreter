package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// reads the value; non-reference expression and address not in the heap throw
public class ReadHeapExpTest {

    @Test
    void readHeapReadsValue() throws MyException{
        VarDeclStmt vStmt = new VarDeclStmt("v",new RefType(new IntType())); // int* v; v = address value

        VarExp vExp = new VarExp("v");     // variable v

        NewStmt newStmt = new NewStmt("v",new ValueExp(new IntValue(30)));    // new(v, 30)  <==>  v = new int(30)
        PrgState state = PrgState.of(newStmt);

        vStmt.execute(state);  // add `int* v` to symTable
        newStmt.execute(state); // allocate 30 to heap

        ReadHeapExp rhExp = new ReadHeapExp(vExp);
        assertEquals( new IntValue(30), rhExp.eval(state.getSymTable(),state.getHeap()));
    }

    @Test
    void nonReferenceExpThrows() throws MyException {
        VarDeclStmt vStmt = new VarDeclStmt("v", new IntType()); // int v. Should've been RefValue
        VarExp vExp = new VarExp("v");     // variable v
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state);  // add `int v` to symTable

        ReadHeapExp rhExp = new ReadHeapExp(vExp);
        assertThrows(ExpressionException.class, () -> rhExp.eval(state.getSymTable(),state.getHeap()));
    }

    @Test
    void addressNotInHeapThrows() throws MyException{
        VarDeclStmt vStmt = new VarDeclStmt("v",new RefType(new IntType()));
        VarExp vExp = new VarExp("v");     // variable v

        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state);  // add `int* v` to symTable

        ReadHeapExp rhExp = new ReadHeapExp(vExp);
        assertThrows(ExpressionException.class, () -> rhExp.eval(state.getSymTable(),state.getHeap()));
    }

}
