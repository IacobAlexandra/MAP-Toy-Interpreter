package interpreter;

import interpreter.model.stmt.*;
import interpreter.model.*;
import java.util.List;
import interpreter.controller.Controller;
import interpreter.view.*;
import interpreter.repository.*;

public class Main {
    public static void main(String[] args) {
        List<IStmt> programs = List.of(Examples.EX1, Examples.EX2, Examples.EX3,
                Examples.EX4, Examples.EX5, Examples.EX6, Examples.EX7, Examples.EX8, Examples.EX9, Examples.EX10);

        TextMenu menu = new TextMenu();

        for (int i = 0; i < programs.size(); i++) {
            IStmt program = programs.get(i);
            var repo = new Repository(PrgState.of(program), "log" + (i + 1) + ".txt");
            var controller = new Controller(repo, true);

            menu.addCommand(new RunExampleCommand(String.valueOf(i + 1),
                    program.toString(), controller));
        }

        menu.addCommand(new ExitCommand("0", "exit"));

        menu.show();
    }
}

// .\gradlew build
// .\gradlew run --console=plain
// git switch --detach A3 ( nu uita `git switch main` la final)