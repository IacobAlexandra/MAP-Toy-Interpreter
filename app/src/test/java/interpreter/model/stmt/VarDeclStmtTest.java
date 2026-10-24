package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VarDeclStmtTest {
    @Test
    void executeVarDeclDeclares() throws MyException { //:^)
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        VarDeclStmt stmt = new VarDeclStmt("woop", new IntType());

        PrgState state = new PrgState(exeStack, symtable, out, stmt);
        stmt.execute(state);

        assertEquals(new IntValue(0), symtable.lookup("woop"));
    }

    @Test
    void alreadyDeclaredVariableThrows () throws MyException{
        MyStack<IStmt> exeStack = new MyStack<>();
        MyDictionary<String, IValue> symtable = new MyDictionary<>();
        MyList<IValue> out = new MyList<>();

        VarDeclStmt stmt1 = new VarDeclStmt("woop",new IntType());
        VarDeclStmt stmt2 = new VarDeclStmt("woop",new IntType());

        PrgState state = new PrgState(exeStack,symtable,out,stmt1);

        stmt1.execute(state);

        assertThrows(StatementException.class, () -> stmt2.execute(state));
    }
}
