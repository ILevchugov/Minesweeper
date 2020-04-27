package ru.levchugov.minesweeper.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.levchugov.minesweeper.cellstate.CellContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class GameField {
    private static final Logger logger = LoggerFactory.getLogger(GameField.class);

    private final GameCell[][] gameCells;
    private final int rowsNumber;
    private final int columnsNumber;

    public GameField(int rowsNumber, int columnsNumber) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.gameCells = new GameCell[rowsNumber][columnsNumber];
        initGameCells();
    }

    public void generateField(int startRowNum, int startColumnNum, int minesNum) {
        initGameCells();
        generateMines(startRowNum, startColumnNum, minesNum);
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                countBombsNear(i, j);
            }
        }
        logger.info("Game field was generated");
    }

    private void generateMines(int firstOpenedRowNum, int firstOpenedColumnNum, int minesNum) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < minesNum) {
            int x = random.nextInt(rowsNumber);
            int y = random.nextInt(columnsNumber);
            if (isCellCanBeMined(gameCells[x][y], firstOpenedRowNum, firstOpenedColumnNum)) {
                if (gameCells[x][y].getContent() != CellContent.BOMB) {
                    gameCells[x][y].setBomb();
                    minesPlaced++;
                }
            }
        }
    }

    //First cell must be EMPTY for balance.
    private boolean isCellCanBeMined(GameCell gameCell, int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (gameCell.getRow() == i && gameCell.getColumn() == j) {
                    return false;
                }
            }
        }
        return true;
    }

    private void countBombsNear(int row, int column) {
        List<GameCell> neighbours = getNeighbours(row, column);
        if (gameCells[row][column].getContent() != CellContent.BOMB) {
            int bombCount = 0;
            for (GameCell neighbour : neighbours) {
                if (neighbour.getContent() == CellContent.BOMB) {
                    bombCount++;
                }
            }
            gameCells[row][column].changeBombsNear(bombCount);
        }
    }

    public List<GameCell> getNeighbours(int row, int column) {
        List<GameCell> neighbours = new ArrayList<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (isCellExist(i, j)) {
                    if (!(i == row && j == column)) {
                        neighbours.add(gameCells[i][j]);
                    }
                }
            }
        }
        return neighbours;
    }

    private boolean isCellExist(int i, int j) {
        return i < rowsNumber && i >= 0 && j < columnsNumber && j >= 0;
    }

    public GameCell getGameCell(int row, int column) {
        return gameCells[row][column];
    }

    private void initGameCells() {
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                gameCells[i][j] = new GameCell(i, j);
            }
        }
    }
}
