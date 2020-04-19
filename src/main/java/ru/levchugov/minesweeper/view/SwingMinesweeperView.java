package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.controller.MinesweeperController;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;
import ru.levchugov.minesweeper.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class SwingMinesweeperView extends JFrame implements MinesweeperView {
    private final MinesweeperField minesweeperField;
    private final MinesweeperDisplay display;
    private final MinesweeperBar bar;

    public SwingMinesweeperView(MinesweeperController controller) {
        super("Minesweeper : CORONAVIRUS EDITION");
        this.minesweeperField = new MinesweeperField(Setting.EASY.getRowNum(), Setting.EASY.getColumnNum(), controller);
        this.display = new MinesweeperDisplay();
        this.bar = new MinesweeperBar(controller);
        display.setBombNums(Setting.EASY.getMinesNum());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setIcon();
        setMenuBar();
        setGamePanel();
        setDisplay();

        pack();

        setResizable(false);
        setVisible(true);
    }

    private void setGamePanel() {
        JPanel panel = minesweeperField.getField();
        add(panel);
    }

    private void setMenuBar() {
        JMenuBar bar1 = bar.getMenuBar();
        setJMenuBar(bar1);
    }

    private void setDisplay() {
        JPanel displayPanel = display.getDisplay();
        add(displayPanel, BorderLayout.SOUTH);
    }

    private void setIcon() {
        IconRegistry iconRegistry = new IconRegistry();
        Optional<ImageIcon> imageIconOpt = iconRegistry.getImageForCell(CellContent.BOMB);
        imageIconOpt.ifPresent(imageIcon -> setIconImage(imageIcon.getImage()));
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
        JOptionPane.showMessageDialog(this, "CORONAVIRUS WIN!", ":(", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void getWinMessage() {
        JOptionPane.showMessageDialog(this, "YOU WIN!", ":)", JOptionPane.INFORMATION_MESSAGE);
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
    public void updateLeaderBoard(Setting setting) {
        bar.updateLeaderBoard(setting);
    }

    @Override
    public void renderNewGame(int rowNumber, int columnNumber, int minesNumber) {
        display.resetTimer();
        display.setBombNums(minesNumber);
        minesweeperField.getNewField(rowNumber, columnNumber);
        pack();
    }
}
