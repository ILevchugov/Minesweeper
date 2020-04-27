package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;
import ru.levchugov.minesweeper.settings.Setting;

import java.util.Map;

public interface MinesweeperView {
    void renderNewGame(int rowNumber, int columnNumber, int minesNumber);

    void updateCell(int row, int column, CellContent content);

    void updateCell(int row, int column, CellStatus status);

    void getLoseMessage();

    void getWinMessage();

    void changeMinesNumber(int minesNumber);

    void startTimer();

    void stopTimer();

    void initLeaderBoard(Map<Setting, Long> scores);

    void updateLeaderBoard(Setting setting, long time);
}
