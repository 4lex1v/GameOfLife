package app.gol.components;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ControlPanel extends HBox {
    private Grid grid;
    private Button startButton, stopButton,
            nextButton, resetButton,
            randomButton;
    private boolean running = false;
    private Scene scene;
    private long time;
    private long counter = 0;

    Runnable generationChanger = new Runnable() {
        @Override
        public void run() {
            grid.nextGeneration();
        }
    };

    private Timeline a = new Timeline(
            new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    counter++;
                    long start = System.currentTimeMillis();
                    grid.nextGeneration();
                    long end = System.currentTimeMillis() - start;
                    time += end;
                    System.out.println("Generation done in: " + end);
                }
            }),
            new KeyFrame(Duration.millis(75), (KeyValue) null)
    );

    private EventHandler<ActionEvent> nextAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (!running) {
                long start = System.currentTimeMillis();
                grid.nextGeneration();
                long end = System.currentTimeMillis() - start;
                System.out.println("Generation done in: " + end);
            }
        }
    };

    private EventHandler<ActionEvent> startAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (!running) {
                running = true;
                counter = 0;
                time = 0;
                a.play();
            }
        }
    };

    private EventHandler<ActionEvent> stopAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (running){
                running = false;
                a.stop();
                System.out.println("Middle time: " + time/counter);
            }
        }
    };
    private EventHandler<ActionEvent> resetAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (!running) {
                grid.reset();
            }
        }
    };

    private EventHandler<ActionEvent> randomAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            grid.randomCells();
        }
    };

    public ControlPanel(final Grid grid, Scene scene) {
        this.grid = grid;
        this.scene = scene;
        setPadding(new Insets(5, 0, 5, 0));
        setAlignment(Pos.CENTER);
        a.setCycleCount(Timeline.INDEFINITE);
        nextButton = new Button("Next");
        nextButton.setOnAction(nextAction);

        startButton = new Button("Start");
        startButton.setOnAction(startAction);

        stopButton = new Button("Stop");
        stopButton.setOnAction(stopAction);

        resetButton = new Button("Reset");
        resetButton.setOnAction(resetAction);

        randomButton = new Button("Random");
        randomButton.setOnAction(randomAction);
        getChildren().addAll(nextButton, startButton, stopButton, resetButton, randomButton);
    }
}
