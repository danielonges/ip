package todobeast.commands;

import todobeast.TaskList;
import todobeast.Ui;
import todobeast.exceptions.ToDoBeastException;
import todobeast.tasks.Task;

import java.util.List;
import java.util.stream.Collectors;

public class FindCommand extends Command {

    String regex;

    public FindCommand(String regex) {
        this.regex = regex;
    }

    public void execute(TaskList taskList, Ui ui) throws ToDoBeastException {
        if (taskList == null) {
            throw new ToDoBeastException("Task list cannot be found.");
        }
        List<Task> tasks = taskList.getTaskList();
        List<Task> filteredList = tasks.stream().filter(task -> task.containsStringInDesc(regex)).collect(Collectors.toList());
        ui.findTasks();
        ui.printTaskList(filteredList);
    }
}