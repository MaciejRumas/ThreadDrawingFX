package model;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;


public class Controller {

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    private DrawerTask task;

    @FXML
    private void handleRunBtnAction(){

        Canvas canvas = (Canvas)Main.getMainScene().lookup("#mainCanvas");
        GraphicsContext gc = canvas.getGraphicsContext2D();

        task = new DrawerTask(gc, canvas);

        ProgressBar progressBar = (ProgressBar)Main.getMainScene().lookup("#mainProgressBar");
        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                int var = (int) task.getValue();
            }
        });
        new Thread(task).start();
    }

    @FXML
    private void handleStopBtnAction(){

        task.cancel();
    }


}
