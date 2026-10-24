package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class View {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        IStmt selectedProgram = null;

        while (running) {
            System.out.println("1. Select a program");
            System.out.println("2. Run the selected program");
            System.out.println("0. Exit");

            System.out.print("> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> {
                    System.out.println("1 - Ex1: int v; v = 2; print(v)");
                    System.out.println("2 - Ex2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)");
                    System.out.println("3 - Ex3: bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)");

                    System.out.print("> ");

                    switch (scanner.nextLine()) {
                        case "1" -> selectedProgram = Examples.EX1;
                        case "2" -> selectedProgram = Examples.EX2;
                        case "3" -> selectedProgram = Examples.EX3;
                        default -> System.out.println("Invalid program.");
                    }
                }

                case "2" -> {
                    if (selectedProgram == null) {
                        System.out.println("Select a program first.");
                        break;
                    }

                    PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProgram);

                    Controller controller = new Controller(new Repository(state), true);

                    try {
                        controller.allStep();
                        System.out.println(state.getOut());
                    } catch (MyException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
