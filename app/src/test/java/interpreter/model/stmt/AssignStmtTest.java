package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssignStmtTest {
    @Test
    void executeAssignAssigns() throws MyException {
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        VarDeclStmt declStmt = new VarDeclStmt("woop",new IntType());
        ValueExp exp = new ValueExp(new IntValue(5));
        AssignStmt assignStmt = new AssignStmt("woop",exp);

        PrgState state = new PrgState(exeStack,symtable,out,declStmt);

        declStmt.execute(state);
        assignStmt.execute(state);

        assertEquals(new IntValue(5),symtable.lookup("woop"));
    }

    @Test
    void undeclaredVariableAssignThrows(){
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        ValueExp exp = new ValueExp(new IntValue(5));
        AssignStmt assignStmt = new AssignStmt("woop",exp);

        PrgState state = new PrgState(exeStack,symtable,out,assignStmt);

        assertThrows(StatementException.class, () -> assignStmt.execute(state));
    }

    @Test
    void typesNotMatchAssignThrows() throws MyException{
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        VarDeclStmt declStmt = new VarDeclStmt("woop",new BoolType());
        ValueExp exp = new ValueExp(new IntValue(5));
        AssignStmt assignStmt = new AssignStmt("woop",exp);

        PrgState state = new PrgState(exeStack,symtable,out,declStmt);

        declStmt.execute(state);
        assertThrows(StatementException.class, () -> assignStmt.execute(state));
    }
}
