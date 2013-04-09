package app.gol.components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private static final double DEFAULT_WIDTH = 10.0;
    public static final Color ALIVE = Color.LIME;
    public static final Color DEAD = Color.rgb(0,5,25);
    private boolean state;

    public Cell() {
        this(false);
    }

    public Cell(boolean state) {
        this(DEFAULT_WIDTH, DEFAULT_WIDTH, state);
    }

    public Cell(double width, double height, boolean state) {
        super(width, height);
        this.state = state;
        super.setFill(state ? ALIVE : DEAD);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Cell cell = (Cell) mouseEvent.getTarget();
                cell.switchState();
            }
        });
    }

    public void switchState() {
        setState(!state);
    }

    public void setAlive() {
        setState(true);
    }

    public void setDead() {
        setState(false);
    }

    public void setState(boolean state) {
        this.state = state;
        super.setFill(state ? ALIVE : DEAD);
    }

    public boolean isAlive() {
        return state;
    }

    @Override
    public String toString() {
        return "Cell: " + (state ? "alive" : "dead");
    }
}
