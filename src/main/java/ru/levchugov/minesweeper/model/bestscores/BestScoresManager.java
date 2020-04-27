package ru.levchugov.minesweeper.model.bestscores;

import ru.levchugov.minesweeper.settings.Setting;

import java.util.HashMap;
import java.util.Map;

public final class BestScoresManager {
    private final Map<Setting, Long> scores;
    private static BestScoresManager instance;

    private final BestScoresStorage bestScoresStorage;

    public static BestScoresManager getInstance() {
        if (instance == null) {
            instance = new BestScoresManager();
        }
        return instance;
    }

    private BestScoresManager() {
        scores = new HashMap<>();
        bestScoresStorage = new BestScoresStorage();
        init();
    }

    private void init() {
        scores.put(Setting.EASY, bestScoresStorage.getScore("Easy"));
        scores.put(Setting.MIDDLE, bestScoresStorage.getScore("Middle"));
        scores.put(Setting.HARD, bestScoresStorage.getScore("Hard"));
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


    public Map<Setting, Long> getScores() {
        return scores;
    }
}
