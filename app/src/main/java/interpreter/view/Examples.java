package interpreter.view;

import com.sun.jdi.Value;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;

import javax.swing.plaf.basic.ComboPopup;

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
        new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)), // a = 2 + ...
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


    /* EX4/fileExample:

    tring varf; varf = filename; openRFile(varf);
    int varc; readFile(varf, varc); print(varc)
    readFile(varf, varc); print(varc);
    closeRFile(varf);
     */
    public static IStmt fileExample(String fileName) {
        return new CompStmt(new VarDeclStmt("varf", new StringType()),  // string varf

         new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(fileName))),     // varf = filename

         new CompStmt(new OpenRFileStmt(new VarExp("varf")),     // openRFile(varf)

         new CompStmt(new VarDeclStmt("varc", new IntType()),    // int varc;

         new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),  // readFile(varf, varc)

         new CompStmt(new PrintStmt(new VarExp("varc")), // print(varc)

         new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),  // readFile(varf, varc)

         new CompStmt(new PrintStmt(new VarExp("varc")),     // print(varc)

         new CloseRFileStmt(new VarExp("varf")   // closeRFile(varf)

         )))))))));
    }
    public static final IStmt EX4 = fileExample("test.in");

    // EX5: int a; int b; a = 5; b = 7; (if a < b then print(a) else print(b))
    public static final IStmt EX5 =
            new CompStmt(
                new CompStmt(new VarDeclStmt("a",new IntType()),
                    new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a",new ValueExp(new IntValue(5))),
                                            new AssignStmt("b",new ValueExp(new IntValue(7)))))),
                new IfStmt(new RelationalExp(RelOp.LT,new VarExp("a"), new VarExp("b")),
                                new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))));


    // EX6: Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)
    //          C++: int* v = nullptr; v = new int(20); int** a = nullptr; a = new int*(v); ; print(v); print(a)
    public static final IStmt EX6 =
            // Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)
            new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                // new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)
                new CompStmt( new NewStmt("v", new ValueExp(new IntValue(20))),       // v -> address 1
                    // Ref Ref int a; new(a, v); print(v); print(a)
                    new CompStmt( new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        // new(a, v); print(v); print(a)
                        new CompStmt( new NewStmt("a", new VarExp("v")),       // a -> address 2
                            // print(v); print(a)
                            new CompStmt(new PrintStmt(new VarExp("v")),
                                    new PrintStmt(new VarExp("a")))))))
    ;

    // EX7: Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
    // Output:
    public static final IStmt EX7 =
            // Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
            new CompStmt( new VarDeclStmt("v", new RefType(new IntType())),
                // new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
                new CompStmt( new NewStmt("v", new ValueExp(new IntValue(20))),
                    // Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
                    new CompStmt( new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        // new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
                        new CompStmt(new NewStmt("a", new VarExp("v")),
                                        // print(rH(v)); print(rH(rH(a)) + 5)
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                        new PrintStmt(new ArithExp(
                                                                                    ArithOp.ADD,
                                                                                    new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                                                    new ValueExp(new IntValue(5)))))))))
    ;

    // EX8: Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5)
    // Output:
    public static final IStmt EX8 =
            // Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5)
            new CompStmt( new VarDeclStmt("v", new RefType(new IntType())),
                // new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5)
                new CompStmt( new NewStmt("v", new ValueExp(new IntValue(20))),
                    // print(rH(v)); wH(v, 30); print(rH(v) + 5)
                    new CompStmt( new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                        // wH(v, 30); print(rH(v) + 5)
                        new CompStmt( new WriteHeapStmt("v",new ValueExp(new IntValue(30))),
                            new PrintStmt(new ArithExp(
                                                      ArithOp.ADD,
                                                      new ReadHeapExp(new VarExp("v")),
                                                      new ValueExp(new IntValue(5))))))))
    ;

    // EX9: Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
    public static final IStmt EX9 =
            // Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
            new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                // new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                    // Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        // new(a, v); new(v, 30); print(rH(rH(a)))
                        new CompStmt( new NewStmt("a", new VarExp("v")),
                            // new(v, 30); print(rH(rH(a)))
                            new CompStmt( new NewStmt("v", new ValueExp(new IntValue(30))),
                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))))
    ;

    // EX10: int v; v = 4; (while (v > 0) (print(v); v = v - 1)); print(v)
    public static final IStmt EX10 =
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                            new CompStmt(new WhileStmt(
                                    new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp(ArithOp.SUB,
                                                    new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                    new PrintStmt(new VarExp("v")))))
    ;

}

