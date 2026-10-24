package interpreter.view;

import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;

public class Examples {

    //Ex1: int v; v = 2; print(v)
    public static final IStmt EX1 =
            new CompStmt(new VarDeclStmt("v", new IntType()), // int v;

            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), // v = 2;

            new PrintStmt(new VarExp("v")))); // print(v);

    // Ex2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)
    public static final IStmt EX2 =

    new CompStmt(new VarDeclStmt("a", new IntType()), // int a;

    new CompStmt(new VarDeclStmt("b", new IntType()), // int b;

    new CompStmt(new AssignStmt("a", // a = ...
        new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)), // a = 2 + ..
            new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), // a = 2 + 3 * ...
                new ValueExp(new IntValue(5))))), // a = 2 + 3 * 5;
                    new CompStmt(new AssignStmt("b", // b =...

            new ArithExp(ArithOp.ADD, new VarExp("a"), //b = a + ..
                    new ValueExp(new IntValue(1)))), //b = a + 1 ;
                        new PrintStmt(new VarExp("b")))))); //print(b)


    // Ex3: bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)
    public static final IStmt EX3 =

    new CompStmt(new VarDeclStmt("a", new BoolType()), // bool a;

    new CompStmt(new VarDeclStmt("v", new IntType()), //int v;

    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), // a = true;

    new CompStmt(new IfStmt(new VarExp("a"), //(if a ...
        new AssignStmt("v", new ValueExp(new IntValue(2))), // (if a then v ...
            new AssignStmt("v", new ValueExp(new IntValue(3)))), //(if a then v = 2 else v = 3)
                new PrintStmt(new VarExp("v")))))); // print(v)
}