package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static interpreter.model.exp.ArithOp.ADD;
import static interpreter.model.exp.ArithOp.SUB;
import static interpreter.model.exp.RelOp.GT;
import static org.junit.jupiter.api.Assertions.*;

public class WhileStmtTest {
    @TempDir
    Path dir;

    @Test
    void whileWithFalseConditionDoesNotExecuteBody() throws MyException {

        BoolValue bVal = new BoolValue(false);
        ValueExp cond = new ValueExp(bVal);
        NopStmt body = new NopStmt();

        WhileStmt whileStmt = new WhileStmt(cond,body);
        PrgState state = PrgState.of(whileStmt);    // Automatically pushes `whileStmt` onto the exeSTack => 1st exec
                                                    //                  ("exeStack.push(program); in PrgState's constructor)"

        state.getExeStack().pop(); // simulate the controller removing("executing") the whileStmt

        whileStmt.execute(state);   // if `cond` is true: adds itself AGAIN to the exeStack, then the `body`

        assertTrue(state.getExeStack().isEmpty());

    }

    @Test
    // v = 1, (while (v > 0)  v = v - 1))
    void whileExecutesBodyOnce() throws MyException{

        VarDeclStmt varStmt = new VarDeclStmt("v",new IntType()); // int v;
        AssignStmt assignStmt = new AssignStmt("v", new ValueExp(new IntValue(1))); // v = 1

        VarExp left = new VarExp("v");
        ValueExp right0 = new ValueExp(new IntValue(0));
        ValueExp right1 = new ValueExp(new IntValue(1));

        ArithExp arithExp = new ArithExp(SUB, left, right1);  // // v - 1
        RelationalExp cond = new RelationalExp(GT, left, right0);  // v > 0
        AssignStmt body =  new AssignStmt("v", arithExp);     // v = v  - 1

        WhileStmt whileStmt = new WhileStmt(cond,body);

        PrgState state = PrgState.of(whileStmt); // exeStack = [ whileStmt ]

        varStmt.execute(state);
        assignStmt.execute(state);

        Repository repo = new Repository(state, dir.resolve("log.txt").toString());
        Controller controller = new Controller(repo,false);

        controller.oneStep(state); //  pop whileStmt, whileStmt.execute() --> while ( 1 > 0) {} true =>
                                    //                  exeStack = [whileStmt(again), body -> TOP OF STACK]

        //NOTE: WhileStmt consists of 2 Stmts pushed onto the exeStack!! one is the WhileStmt as an idk, container, and the other one is the body
        controller.oneStep(state); // pop body, body.execute() --> v = v-1
                                    //                  exeStack = [whileStmt]

        controller.oneStep(state); // pop while, whileStmt.execute() --> while ( 0 > 0) {} -> does nothing
                                    //                  exeStack = []

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    // v = 1, count = 0 (while (v > 0)  v = v - 1 ; count = count +1 ))
    void whileExecutesBodySeveralTimes() throws MyException {

        VarDeclStmt vVarStmt = new VarDeclStmt("v",new IntType()); // int v;
        AssignStmt vAssignStmt = new AssignStmt("v", new ValueExp(new IntValue(5))); // v = 1

        VarDeclStmt countVarStmt = new VarDeclStmt("count",new IntType()); // int count = 0; (defaults to 0)

        VarExp vLeft = new VarExp("v");
        VarExp countLeft = new VarExp("count");

        ValueExp condRight = new ValueExp(new IntValue(0));
        ValueExp right1 = new ValueExp(new IntValue(1));

        ArithExp subArithExp = new ArithExp(SUB, vLeft, right1);  // v - 1
        ArithExp addArithExp = new ArithExp(ADD, countLeft, right1); // count + 1

        RelationalExp cond = new RelationalExp(GT, vLeft, condRight);  // v > 0
        AssignStmt vDecrementStmt =  new AssignStmt("v", subArithExp);     // v = v  - 1
        AssignStmt countIncrementStmt =  new AssignStmt("count", addArithExp); // count = count +  1`

        CompStmt body = new CompStmt(countIncrementStmt, vDecrementStmt); //  v = v  - 1; count = count +  1`

        WhileStmt whileStmt = new WhileStmt(cond,body);

        PrgState state = PrgState.of(whileStmt); // exeStack = [ whileStmt ]

        // execute -> add to symtable
        vVarStmt.execute(state);
        countVarStmt.execute(state);
        vAssignStmt.execute(state);

        Repository repo = new Repository(state, dir.resolve("log.txt").toString());
        Controller controller = new Controller(repo,false);

        controller.allStep();  // while v = 5,4,3,2,1,0 ...

        VarExp count = new VarExp("count");
        assertEquals(new IntValue(5), count.eval(state.getSymTable(),state.getHeap()));
    }

    @Test
    void whileWithANonBooleanConditionThrows() {
        var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());

        PrgState state = PrgState.of(loop);

        assertThrows(StatementException.class, () -> loop.execute(state));
    }

}
