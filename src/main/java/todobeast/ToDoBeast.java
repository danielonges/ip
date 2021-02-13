package todobeast;

import todobeast.commands.Command;
import todobeast.exceptions.ToDoBeastException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main client class for the application.
 */
public class ToDoBeast {

    private Storage storage;
    private Ui ui;
    private TaskList taskList;

    private static final String DATA_DIRECTORY_NAME = "./data/";
    private static final String DATA_FILE_NAME = "data.txt";

    public ToDoBeast() {
        storage = new Storage(DATA_DIRECTORY_NAME, DATA_FILE_NAME);
        ui = new Ui();
        try {
            ui.showLoading();
            taskList = new TaskList(storage.loadTasks());
            ui.showLoadingSuccess();
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            taskList = new TaskList(new ArrayList<>());
        }
    }

    String getResponse(String fullCommand) {
        try {
//            String fullCommand = ui.readCommand();
//            ui.addToResponseOutput(ui.showLine());
            Command command = Parser.parse(fullCommand);
            assert command != null : "null generated from command!";
            command.execute(taskList, ui);

            if (command.isExit()) {
                return "exit";
            }
        } catch (ToDoBeastException e) {
            ui.addToResponseOutput(ui.showError(e.getMessage()));
        } finally {
            try {
                storage.saveToStorage(taskList.formatTaskListForStorage());
            } catch (IOException e) {
                ui.addToResponseOutput(ui.showError(e.getMessage()));
            }
//            ui.addToResponseOutput(ui.showLine());
        }
        return ui.returnResponseOutput();
    }

    String getWelcome() {
        ui.addToResponseOutput(ui.showWelcome());
//        ui.addToResponseOutput(ui.showLine());
        return ui.returnResponseOutput();
    }

    String getExit() {
        ui.addToResponseOutput(ui.showExit());
        return ui.returnResponseOutput();
    }

    /**
     * Contains the main logic of the application. The program will run until the "exit" command is given.
     */
    public void runApplication() {

        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.addToResponseOutput(ui.showLine());
                Command command = Parser.parse(fullCommand);
                command.execute(taskList, ui);
                isExit = command.isExit();
            } catch (ToDoBeastException e) {
                ui.addToResponseOutput(ui.showError(e.getMessage()));
            } finally {
                try {
                    storage.saveToStorage(taskList.formatTaskListForStorage());
                } catch (IOException e) {
                    ui.showError(e.getMessage());
                }
                ui.addToResponseOutput(ui.showLine());
            }
        }
        ui.showExit();
    }

    /**
     *Runs the main logic of the application.
     * @param args not used in this method
     */
    public static void main(String[] args) {
        ToDoBeast toDoBeast = new ToDoBeast();
        toDoBeast.runApplication();
    }



}
