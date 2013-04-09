package app.gol;

import app.gol.ui.Frame;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameOfLife extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Conway's Game Of Life");
        stage.setScene(new Frame(stage).getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(GameOfLife.class, args);
    }
}
