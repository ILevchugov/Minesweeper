package ru.levchugov.minesweeper.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.levchugov.minesweeper.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LeaderBoard {
    private static final Logger logger = LoggerFactory.getLogger(LeaderBoard.class);

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
    private static final Font LABELS_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 110;

    private final JFrame leaderBoard;

    private final Map<String, JLabel> recordsText;
    private final GridBagConstraints gridBagConstraints;

    LeaderBoard() {
        this.leaderBoard = new JFrame("Best Scores");
        this.gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;

        recordsText = new HashMap<>();
        JLabel valueEasy = new JLabel();
        recordsText.put(Setting.EASY.getName(), valueEasy);

        JLabel valueMiddle = new JLabel();
        recordsText.put(Setting.MIDDLE.getName(), valueMiddle);

        JLabel valueHard = new JLabel();
        recordsText.put(Setting.HARD.getName(), valueHard);

        leaderBoard.setLayout(new GridBagLayout());

        setLabels();

        leaderBoard.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        leaderBoard.setResizable(false);

    }

    private void setLabels() {
        setLabelForSetting(Setting.EASY, 0);
        setLabelForSetting(Setting.MIDDLE, 1);
        setLabelForSetting(Setting.HARD, 2);
    }

    private void setLabelForSetting(Setting setting, int gridy) {
        gridBagConstraints.gridy = gridy;
        JLabel nameLabel = new JLabel(setting.getName());
        nameLabel.setFont(LABELS_FONT);
        leaderBoard.add(nameLabel, gridBagConstraints);

        JLabel textLabel = recordsText.get(setting.getName());
        textLabel.setFont(LABELS_FONT);

        leaderBoard.add(textLabel, gridBagConstraints);
    }

    void initLeaderBoard(Map<Setting, Long> scores) {
            recordsText.get(Setting.EASY.getName()).setText(timeFormat.format(TimeUnit.SECONDS.toMillis(scores.get(Setting.EASY))));
            recordsText.get(Setting.MIDDLE.getName()).setText(timeFormat.format(TimeUnit.SECONDS.toMillis(scores.get(Setting.MIDDLE))));
            recordsText.get(Setting.HARD.getName()).setText(timeFormat.format(TimeUnit.SECONDS.toMillis(scores.get(Setting.HARD))));
    }

    void updateLeaderBoard(Setting setting, long time) {
        logger.info("Prishel suda" + time);
            recordsText.get(setting.getName()).setText(timeFormat.format(TimeUnit.SECONDS.toMillis(time)));
    }
    void setVisible() {
        leaderBoard.setVisible(true);
    }
}
