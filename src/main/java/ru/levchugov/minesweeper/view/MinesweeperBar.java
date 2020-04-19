package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.controller.MinesweeperController;
import ru.levchugov.minesweeper.settings.Setting;

import javax.swing.*;
import java.awt.*;

public class MinesweeperBar {
    private final JMenuBar menuBar;
    private final MinesweeperController controller;
    private final CustomSettingFrame customSettingView;
    private final LeaderBoardFrame leaderBoard;

    MinesweeperBar(MinesweeperController controller) {
        this.controller = controller;
        this.customSettingView = new CustomSettingFrame(controller);
        this.menuBar = new JMenuBar();
        this.leaderBoard = new LeaderBoardFrame();
        menuBar.setLayout(new BorderLayout());
        createMinesweeperBar();
    }

    private void createMinesweeperBar() {
        JMenu jMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(actionEvent -> controller.startNewGame());

        JMenuItem setEasyDifficulty = new JMenuItem("Easy difficulty");
        setEasyDifficulty.addActionListener(actionEvent -> controller.setDifficulty(Setting.EASY));

        JMenuItem setMiddleDifficulty = new JMenuItem("Middle difficulty");
        setMiddleDifficulty.addActionListener(actionEvent -> controller.setDifficulty(Setting.MIDDLE));

        JMenuItem setHardDifficulty = new JMenuItem("Hard difficulty");
        setHardDifficulty.addActionListener(actionEvent -> controller.setDifficulty(Setting.HARD));

        JMenuItem setCustomDifficulty = new JMenuItem("Custom difficulty");
        setCustomDifficulty.addActionListener(actionEvent -> customSettingView.setVisible(true));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(actionEvent -> System.exit(0));

        JMenu bestScoresMenu = new JMenu("Best Scores");
        JMenuItem showScores = new JMenuItem("Show");
        showScores.addActionListener(actionEvent -> leaderBoard.setVisible(true));
        bestScoresMenu.add(showScores);

        jMenu.add(newGame);
        jMenu.add(setEasyDifficulty);
        jMenu.add(setMiddleDifficulty);
        jMenu.add(setHardDifficulty);
        jMenu.add(setCustomDifficulty);
        jMenu.add(exit);

        menuBar.setLayout(new FlowLayout());

        menuBar.add(jMenu);
        menuBar.add(bestScoresMenu);
    }

    public void updateLeaderBoard(Setting setting) {
        leaderBoard.updateLeaderBoard(setting);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
