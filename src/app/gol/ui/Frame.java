package app.gol.ui;

import app.gol.components.Grid;
import app.gol.components.ControlPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Frame {
    private Stage stage;
    private Scene scene;
    private BorderPane root;
    private Grid grid;
    private ControlPanel controlPanel;

    public Frame(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        initScene();
    }

    private void initScene() {
        root = new BorderPane();
        scene = new Scene(root, Color.rgb(31,31,31));
        grid = new Grid(50, 50);
        controlPanel = new ControlPanel(grid, scene);
        root.setTop(controlPanel);
        root.setCenter(grid);
    }

    public Scene getScene() {
        return scene;
    }
}
