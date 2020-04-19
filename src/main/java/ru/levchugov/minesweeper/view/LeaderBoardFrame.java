package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.bestscores.BestScores;
import ru.levchugov.minesweeper.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LeaderBoardFrame extends JFrame {
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
    private static final Font LABELS_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);

    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 110;

    private final BestScores bestScores;

    private final Map<String, JLabel> recordsText;
    private final GridBagConstraints gridBagConstraints;

    LeaderBoardFrame() {
        super("Best scores");
        this.bestScores = BestScores.getInstance();
        this.gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;

        recordsText = new HashMap<>();
        JLabel valueEasy = new JLabel();
        recordsText.put(Setting.EASY.getName(), valueEasy);

        JLabel valueMiddle = new JLabel();
        recordsText.put(Setting.MIDDLE.getName(), valueMiddle);

        JLabel valueHard = new JLabel();
        recordsText.put(Setting.HARD.getName(), valueHard);

        setLayout(new GridBagLayout());

        setLabels();

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);

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
        add(nameLabel, gridBagConstraints);

        JLabel textLabel = recordsText.get(setting.getName());
        textLabel.setFont(LABELS_FONT);

        long time = TimeUnit.SECONDS.toMillis((bestScores.getScore(setting)));

        textLabel.setText(timeFormat.format(time));
        add(textLabel, gridBagConstraints);
    }

    public void updateLeaderBoard(Setting setting) {
        if (recordsText.containsKey(setting.getName())) {
            long time = TimeUnit.SECONDS.toMillis((bestScores.getScore(setting)));
            recordsText.get(setting.getName()).setText(timeFormat.format(time));
        }
    }
}
