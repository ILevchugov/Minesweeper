package ru.levchugov.minesweeper.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.levchugov.minesweeper.bestscores.BestScores;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;
import ru.levchugov.minesweeper.settings.Setting;
import ru.levchugov.minesweeper.view.MinesweeperView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MinesweeperGame {
    private static final Logger logger = LoggerFactory.getLogger(MinesweeperGame.class);

    private final BestScores bestScores;
    private final MinesweeperNotifier minesweeperNotifier;

    private GameField gameField;

    private int rowNum;
    private int columnNum;
    private int minesNum;
    private int openedCells;
    private int flagStated;

    private boolean isAllCellsClosed;

    private long startTime;

    private Setting setting;

    public MinesweeperGame() {
        this.setting = Setting.EASY;
        this.bestScores = BestScores.getInstance();
        this.minesweeperNotifier = new MinesweeperNotifier();
        prepareNewGame();
    }

    public void prepareNewGame() {
        this.rowNum = setting.getRowNum();
        this.columnNum = setting.getColumnNum();
        this.minesNum = setting.getMinesNum();
        this.gameField = new GameField(rowNum, columnNum);
        this.flagStated = 0;
        this.openedCells = 0;
        this.isAllCellsClosed = true;
        minesweeperNotifier.notifyViewAboutNewGame(rowNum, columnNum, minesNum);
    }

    public void changeSetting(Setting setting) {
        logger.info("Settings was changed on {}", setting.getName());
        this.setting = setting;
    }

    public void attachView(MinesweeperView view) {
        minesweeperNotifier.attachView(view);
    }

    public void openCell(int row, int column) {
        if (isAllCellsClosed) {
            startGame(row, column);
        }
        GameCell currentCell = gameField.getGameCell(row, column);
        if (currentCell.getStatus() == CellStatus.CLOSED) {
            currentCell.setStatus(CellStatus.OPENED);
            minesweeperNotifier.notifyViewShowContent(row, column, gameField.getGameCell(row, column).getContent());
            if (currentCell.getContent() == CellContent.EMPTY) {
                openNeighbors(row, column);
            }
            openedCells++;
            checkGameFinished(row, column);
        }
    }

    private void startGame(int row, int column) {
        logger.info("New game started");
        gameField.generateField(row, column, minesNum);
        isAllCellsClosed = false;
        startTime = System.nanoTime();
        minesweeperNotifier.notifySetTimer();
    }

    private void openNeighbors(int row, int column) {
        List<GameCell> neighbors = gameField.getNeighbours(row, column);
        for (GameCell neighbor : neighbors) {
            openCell(neighbor.getRow(), neighbor.getColumn());
        }
    }

    public void flagCell(int row, int column) {
        GameCell currentCell = gameField.getGameCell(row, column);
        if (currentCell.getStatus() == CellStatus.FLAGGED) {
            currentCell.setStatus(CellStatus.CLOSED);
            flagStated--;
            minesweeperNotifier.notifyViewUpdateFlagStatus(row, column, minesNum - flagStated, CellStatus.CLOSED);
        } else {
            if (currentCell.getStatus() == CellStatus.CLOSED && !isAllCellsClosed) {
                currentCell.setStatus(CellStatus.FLAGGED);
                flagStated++;
                minesweeperNotifier.notifyViewUpdateFlagStatus(row, column, minesNum - flagStated, CellStatus.FLAGGED);
            }
        }
    }

    public void openCellsAroundOpenCell(int row, int column) {
        GameCell currentCell = gameField.getGameCell(row, column);
        if (currentCell.getStatus() == CellStatus.OPENED) {
            if (isFlagsAroundEqualsBombsNear(row, column)) {
                List<GameCell> neighbors = gameField.getNeighbours(row, column);
                for (GameCell neighbor : neighbors) {
                    openCell(neighbor.getRow(), neighbor.getColumn());
                    if (isAllCellsClosed) {
                        break;
                    }
                }
            }
        }
    }

    private boolean isFlagsAroundEqualsBombsNear(int row, int column) {
        GameCell currentCell = gameField.getGameCell(row, column);
        List<GameCell> neighbors = gameField.getNeighbours(row, column);
        int flagsNum = 0;
        for (GameCell neighbor : neighbors) {
            if (neighbor.getStatus() == CellStatus.FLAGGED) {
                flagsNum++;
            }
        }
        return flagsNum == currentCell.getBombsNearNum();
    }

    private void checkGameFinished(int row, int column) {
        if (isGameLost(row, column)) {
            minesweeperNotifier.notifyStopTimer();
            minesweeperNotifier.notifyViewAboutLose();
            logger.info("Game ended with lose");
            prepareNewGame();
        } else {
            if (isGameWin()) {
                long finishTime = System.nanoTime();
                bestScores.addScore(setting, TimeUnit.NANOSECONDS.toSeconds(finishTime - startTime));
                logger.info("Game ended with win");
                minesweeperNotifier.notifyStopTimer();
                minesweeperNotifier.notifyViewAboutWin();
                minesweeperNotifier.notifyViewUpdateLeaderBoard(setting);
                prepareNewGame();
            }
        }
    }

    private boolean isGameLost(int row, int column) {
        return gameField.getGameCell(row, column).getContent() == CellContent.BOMB;
    }

    private boolean isGameWin() {
        return openedCells == rowNum * columnNum - minesNum;
    }

}
