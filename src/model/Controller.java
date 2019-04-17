package model;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;


public class Controller {

    @FXML
    private TextField pointsAmountTextField;

    @FXML
    private TextField valueTextField;

    @FXML
    private ProgressBar mainProgressBar;

    private DrawerTask task;
    private DrawerTaskValue taskValue;


    @FXML
    private void handleRunBtnAction(){
        valueTextField.setText("");
        task = new DrawerTask(Integer.parseInt(pointsAmountTextField.getText()));

        mainProgressBar = (ProgressBar)Main.getMainScene().lookup("#mainProgressBar");
        mainProgressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                taskValue = task.getValue();
                valueTextField.setText(String.format("%.3f",taskValue.getValue()));
            }
        });

        Platform.runLater(()->new Thread(task).start());
    }

    @FXML
    private void handleStopBtnAction(){
        task.cancel();
    }

}
