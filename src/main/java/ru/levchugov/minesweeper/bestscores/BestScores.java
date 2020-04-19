package ru.levchugov.minesweeper.bestscores;

import ru.levchugov.minesweeper.settings.Setting;

import java.util.HashMap;
import java.util.Map;

public class BestScores {
    private final Map<Setting, Long> scores;
    private static BestScores instance;

    private final BestScoresStorage bestScoresStorage;

    public static BestScores getInstance() {
        if (instance == null) {
            instance = new BestScores();
        }
        return instance;
    }

    private BestScores() {
        scores = new HashMap<>();
        bestScoresStorage = new BestScoresStorage();
        init();
    }

    private void init() {
        scores.put(Setting.EASY, (long) bestScoresStorage.getScore("Easy"));
        scores.put(Setting.MIDDLE, (long) bestScoresStorage.getScore("Middle"));
        scores.put(Setting.HARD, (long) bestScoresStorage.getScore("Hard"));
    }

    public void addScore(Setting setting, long score) {
        if (scores.containsKey(setting)) {
            if (score < scores.get(setting)) {
                scores.remove(setting);
                scores.put(setting, score);
                bestScoresStorage.setScore(setting.getName(), (int) score);
            }
        }
    }

    public Long getScore(Setting setting) {
        return scores.get(setting);
    }
}
