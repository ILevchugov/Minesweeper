package ru.levchugov.minesweeper.view;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;


public class MinesweeperDisplay {
    private static final Font LABELS_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final JPanel display;
    private final JLabel bombCounter;

    private final IconRegistry iconRegistry = new IconRegistry();
    private final TimerDisplay timerDisplay;

    private final GridBagConstraints gridBagConstraints;

    MinesweeperDisplay() {
        this.display = new JPanel();
        this.bombCounter = new JLabel();
        this.timerDisplay = new TimerDisplay();
        this.gridBagConstraints = new GridBagConstraints();

        display.setLayout(new GridBagLayout());
        gridBagConstraints.weightx = 1;

        createBombCounterIcon();
        createBombCounter();
        createTimer();
        createTimerIcon();
    }

    private void createBombCounterIcon() {
        JLabel bombCounterIcon = new JLabel();
        Optional<ImageIcon> imageIconOpt = iconRegistry.getMinesCounterImage();
        imageIconOpt.ifPresent(bombCounterIcon::setIcon);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        display.add(bombCounterIcon, gridBagConstraints);
    }

    private void createBombCounter() {
        bombCounter.setFont(LABELS_FONT);
        bombCounter.setForeground(Color.BLACK);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        display.add(bombCounter, gridBagConstraints);
    }

    private void createTimer() {
        JLabel timerText = timerDisplay.getTimeText();
        timerText.setFont(LABELS_FONT);
        timerText.setForeground(Color.BLACK);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        display.add(timerText, gridBagConstraints);
    }

    private void createTimerIcon() {
        JLabel timerIcon = new JLabel();
        Optional<ImageIcon> imageIconOpt = iconRegistry.getTimer();
        imageIconOpt.ifPresent(timerIcon::setIcon);
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        display.add(timerIcon, gridBagConstraints);
    }

    void setBombNums(int bombNums) {
        bombCounter.setText(String.valueOf(bombNums));
    }

    void startTimer() {
        timerDisplay.startTimer();
    }

    void resetTimer() {
        timerDisplay.resetTimer();
    }

    void stopTimer() {
        timerDisplay.stopTimer();
    }

    JPanel getDisplay() {
        return display;
    }
}

