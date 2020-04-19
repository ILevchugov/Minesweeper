package ru.levchugov.minesweeper.model;

import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;
import ru.levchugov.minesweeper.settings.Setting;
import ru.levchugov.minesweeper.view.MinesweeperView;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperNotifier {
    private final List<MinesweeperView> minesweeperViews = new ArrayList<>();

    void attachView(MinesweeperView view) {
        minesweeperViews.add(view);
    }

    void notifyViewShowContent(int row, int column, CellContent content) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.updateCell(row, column, content));
    }

    void notifyViewUpdateFlagStatus(int row, int column, int minesRemaining, CellStatus status) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.updateCell(row, column, status));
        minesweeperViews.forEach(minesweeperView -> minesweeperView.changeMinesNumber(minesRemaining));
    }

    void notifyViewAboutLose() {
        minesweeperViews.forEach(MinesweeperView::getLoseMessage);
    }

    void notifyViewAboutWin() {
        minesweeperViews.forEach(MinesweeperView::getWinMessage);
    }

    void notifyViewUpdateLeaderBoard(Setting setting) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.updateLeaderBoard(setting));
    }

    void notifyViewAboutNewGame(int rowNumber, int columnNumber, int minesNumber) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.renderNewGame(rowNumber, columnNumber, minesNumber));
    }

    void notifySetTimer() {
        minesweeperViews.forEach(MinesweeperView::startTimer);
    }

    void notifyStopTimer() {
        minesweeperViews.forEach(MinesweeperView::stopTimer);
    }
}
