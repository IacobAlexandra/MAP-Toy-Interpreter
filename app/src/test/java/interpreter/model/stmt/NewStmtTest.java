package interpreter.model.stmt;

import interpreter.exceptions.*;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NewStmtTest {

    // allocates and updates the variable;
    // variable not declared, not a reference, value of the wrong type throw

    @Test
    void newStmtAllocatesAndUpdatesVariable() throws MyException {

        // NOTE: First we need to declare the variable( defaultValue = 0 == null )
        //          and only then, separately, THROUGH UPDATE, we assign a value to it!

        VarDeclStmt vStmt = new VarDeclStmt("v",new RefType(new IntType())); // int* v
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state);

        NewStmt nStmt = new NewStmt("v",new ValueExp(new IntValue(1)));// v = address 1

        IHeap heap = state.getHeap();

        nStmt.execute(state);

        // first free address is 1 (nextAddress in MyHeap)
        assertTrue(heap.isDefined(1));

        // Heap stores the integer value
        assertEquals(new IntValue(1), heap.get(1));

        // The variable stores a reference to that heap address
        assertEquals(new RefValue(1, new IntType()),
                            state.getSymTable().lookup("v"));

    }

    @Test
    void variableNotDeclaredThrows(){
        NewStmt nStmt = new NewStmt("v",new ValueExp(new IntValue(1)));
        PrgState state = PrgState.of(nStmt);
        assertThrows(StatementException.class, () -> nStmt.execute(state));
    }

    @Test
    void variableNotAReferenceThrows() throws MyException {
        VarDeclStmt vStmt = new VarDeclStmt("v",new IntType()); // int v. (wrong. must've been int* v)
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state);

        NewStmt nStmt = new NewStmt("v",new ValueExp(new IntValue(1)));

        assertThrows(StatementException.class, () -> nStmt.execute(state));
    }

    @Test
    void valueTypeDifferentFromLocationTypeThrows() throws MyException {
        VarDeclStmt vStmt = new VarDeclStmt("v",new RefType(new IntType())); // int* v
        PrgState state = PrgState.of(vStmt);

        vStmt.execute(state);

        NewStmt nStmt = new NewStmt("v",
                        new ValueExp(new RefValue(1,new StringType())));

        assertThrows(StatementException.class, () -> nStmt.execute(state));
    }


}
