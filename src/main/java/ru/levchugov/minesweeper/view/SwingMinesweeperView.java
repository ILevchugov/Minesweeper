package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.controller.MinesweeperController;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;
import ru.levchugov.minesweeper.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

public class SwingMinesweeperView implements MinesweeperView {
    private final JFrame playingFrame;
    private final MinesweeperField minesweeperField;
    private final MinesweeperDisplay display;
    private final MinesweeperBar bar;

    public SwingMinesweeperView(MinesweeperController controller) {
        this.playingFrame = new JFrame("Minesweeper : CORONAVIRUS EDITION");
        this.minesweeperField = new MinesweeperField(controller);
        this.display = new MinesweeperDisplay();
        this.bar = new MinesweeperBar(controller);
    }

    private void init() {
        playingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setIcon();
        setMenuBar();
        setGamePanel();
        setDisplay();

        playingFrame.pack();

        playingFrame.setResizable(false);
        playingFrame.setVisible(true);
    }
    private void setGamePanel() {
        JPanel panel = minesweeperField.getField();
        playingFrame.add(panel);
    }

    private void setMenuBar() {
        JMenuBar bar1 = bar.getMenuBar();
        playingFrame.setJMenuBar(bar1);
    }

    private void setDisplay() {
        JPanel displayPanel = display.getDisplay();
        playingFrame.add(displayPanel, BorderLayout.SOUTH);
    }

    private void setIcon() {
        IconRegistry iconRegistry = new IconRegistry();
        Optional<ImageIcon> imageIconOpt = iconRegistry.getImageForCell(CellContent.BOMB);
        imageIconOpt.ifPresent(imageIcon -> playingFrame.setIconImage(imageIcon.getImage()));
    }

    @Override
    public void updateCell(int row, int column, CellContent content) {
        minesweeperField.updateCell(row, column, content);
    }

    @Override
    public void updateCell(int row, int column, CellStatus status) {
        minesweeperField.updateCell(row, column, status);
    }

    @Override
    public void getLoseMessage() {
        JOptionPane.showMessageDialog(playingFrame, "CORONAVIRUS WIN!", ":(", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void getWinMessage() {
        JOptionPane.showMessageDialog(playingFrame, "YOU WIN!", ":)", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void changeMinesNumber(int minesNumber) {
        display.setBombNums(minesNumber);
    }

    @Override
    public void startTimer() {
        display.startTimer();
    }

    @Override
    public void stopTimer() {
        display.stopTimer();
    }

    @Override
    public void initLeaderBoard(Map<Setting, Long> scores) {
        bar.initLeaderBoard(scores);
    }

    @Override
    public void updateLeaderBoard(Setting setting, long time) {
        bar.updateLeaderBoard(setting, time);
    }

    @Override
    public void renderNewGame(int rowNumber, int columnNumber, int minesNumber) {
        init();
        display.resetTimer();
        display.setBombNums(minesNumber);
        minesweeperField.getNewField(rowNumber, columnNumber);
        playingFrame.pack();
    }
}
