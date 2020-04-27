package ru.levchugov.minesweeper.controller;

import ru.levchugov.minesweeper.model.MinesweeperGame;
import ru.levchugov.minesweeper.settings.Setting;

public class MinesweeperController {
    private final MinesweeperGame minesweeperGame;

    public MinesweeperController(MinesweeperGame minesweeperGame) {
        this.minesweeperGame = minesweeperGame;
    }

    public void startNewGame() {
        minesweeperGame.prepareNewGame();
    }

    public void setDifficulty(Setting setting) {
        minesweeperGame.changeSetting(setting);
        minesweeperGame.prepareNewGame();
    }

    public void handleUserClickedLeftButtonOnCell(int row, int column) {
        minesweeperGame.openCell(row, column);
    }

    public void handleUserClickedRightButtonOnCell(int row, int column) {
        minesweeperGame.flagCell(row, column);
    }

    public void handleUserClickedMiddleButtonOnCell(int row, int column) {
        minesweeperGame.openCellsAroundOpenedCell(row, column);
    }
}
