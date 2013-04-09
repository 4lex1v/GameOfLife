package app.gol.components;

import javafx.scene.layout.GridPane;
import java.util.Random;

public class Grid extends GridPane {
    private static final int DEFAULT_ROWS = 30;
    private static final int DEFAULT_COLS = 30;
    private Cell[][] cells;
    private int rows;
    private int cols;

    public void setRows(int newRowsValue) { rows = newRowsValue; }
    public void setCols(int value) { cols = value; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public Grid() {
        this(DEFAULT_ROWS, DEFAULT_COLS)
    }

    public Grid(int rows, int cols) {
        setHgap(1.0);
        setVgap(1.0);
        setRows(rows);
        setCols(cols);
        cells = new Cell[rows][cols];
        initCells();
        renderCells();
    }

    // Qudratic time, but method called just once,
    // when the grid is initializing
    private void initCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    // Quadratic time, called only once, on 
    // button click
    public void reset() {
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                aCell.setDead();
            }
        }
    }

    // todo: quadratic time
    private void renderCells() {
        for (int i = 0; i < cells.length; i++) {
           addRow(i, cells[i]);
        }
    }

    public void nextGeneration() {
        // grid with additional cells around
        // one on top, left, bottom, right
        boolean[][] oldGen = new boolean[rows+2][cols+2];
        for (int i = 0; i < cells.length; i++) {
            if (i > 0 && i < cells.length-1) {
                boolean leftSideCell  = cells[i][0].isAlive();
                boolean rightSideCell = cells[i][cells.length-1].isAlive();
                oldGen[i+1][cells.length+1] = leftSideCell;
                oldGen[i+1][0] = rightSideCell;
            }
            for (int j = 0; j < cells[i].length; j++) {
                boolean cellSt = cells[i][j].isAlive();
                oldGen[i+1][j+1] = cellSt;
                if (i == 0) {
                    if (j == 0) {
                        oldGen[i+1][cells.length+1] = cellSt;
                        oldGen[cells.length+1][i+1] = cellSt;
                    } else if (j == cells.length-1) {
                        oldGen[i+1][i] = cellSt;
                        oldGen[cells.length+1][cells.length] = cellSt;
                    }
                } else if (i == cells.length - 1) {
                    if (j == 0) {
                        oldGen[j][j+1] = cellSt;
                        oldGen[cells.length][cells.length+1] = cellSt;
                    } else if (j == i) {
                        oldGen[cells.length][0] = cellSt;
                        oldGen[0][cells.length] = cellSt;
                    }
                } else {
                    oldGen[0][j+1] = cellSt;
                }
            }
        }
        boolean futureState;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                futureState = destiny(oldGen, i+1, j+1);
                if (cells[i][j].isAlive() != futureState)
                    cells[i][j].setState(futureState);
            }
        }
    }

    private boolean destiny(final boolean[][] generation, 
                            final int i, 
                            final int j) {
        int countLive = 0;
        // top neighbours
        if (generation[i-1][j-1]) countLive++;
        if (generation[i-1][j]) countLive++;
        if (generation[i-1][j+1]) countLive++;
        // neighbours
        if (generation[i][j+1]) countLive++;
        if (generation[i][j-1]) countLive++;
        // bottom neighbours
        if (generation[i+1][j-1]) countLive++;
        if (generation[i+1][j]) countLive++;
        if (generation[i+1][j+1]) countLive++;
        return countLive == 3 || countLive == 2 && generation[i][j];
    }

    public Cell getCell(final int row, final int col) {
        return cells[row][col];
    }

    public void randomCells() {
        Random rand = new Random();
        Cell cell;
        for (Cell[] cell1 : cells) {
            for (Cell aCell1 : cell1) {
                cell = aCell1;
                if (!cell.isAlive()) {
                    cell.setState(rand.nextBoolean());
                }
            }
        }
    }
}
