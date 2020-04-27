package ru.levchugov.minesweeper.model.bestscores;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.levchugov.minesweeper.settings.Setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

final class BestScoresStorage {
    private static final Logger logger = LoggerFactory.getLogger(BestScoresStorage.class);

    private static final String FILE_NAME = "High_Scores.json";
    private static final String ENCRYPT_KEY = "COVID-19";
    private static final long DEFAULT_SCORE = 3599;

    private static final TypeReference<HashMap<String, String>> TYPE_OF_DATA_IN_RECORD_FILE =
            new TypeReference<HashMap<String, String>>() {};

    private static final List<String> SCORES_KEYS;

    static {
        SCORES_KEYS = new ArrayList<>();
        SCORES_KEYS.add(Setting.EASY.getName());
        SCORES_KEYS.add(Setting.MIDDLE.getName());
        SCORES_KEYS.add(Setting.HARD.getName());
    }

    private Map<String, Long> scores;
    private Map<String, String> encryptedScores;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final BasicTextEncryptor scoresEncryptor;

    BestScoresStorage() {
        this.scoresEncryptor = new BasicTextEncryptor();
        this.encryptedScores = new HashMap<>();
        this.scores = new HashMap<>();

        scoresEncryptor.setPassword(ENCRYPT_KEY);

        readScores();
    }

    private void writeScore() {
        try {
            encryptedScores = encryptMap(scores);
            objectMapper.writeValue(new File(FILE_NAME), encryptedScores);
        } catch (IOException e) {
            logger.error("Scores file writing problem");
        }
    }

    long getScore(String difficulty) {
        return scores.get(difficulty);
    }

    void setScore(String difficulty, long value) {
        scores.remove(difficulty);
        scores.put(difficulty, value);
        writeScore();
    }


    private void readScores() {
        if (Files.exists(Paths.get(FILE_NAME))) {
            try {
                encryptedScores = objectMapper.readValue(new File(FILE_NAME),
                        TYPE_OF_DATA_IN_RECORD_FILE);
                scores = decryptMap(encryptedScores);
                if (!isValidKeys()) {
                    resetScores();
                }
            } catch (IOException e) {
                logger.warn("Scores file reading problem, best scores was reset", e);
                resetScores();
            } catch (EncryptionOperationNotPossibleException e) {
                logger.warn("Encryption problems, best scores was reset", e);
                resetScores();
            }
        } else {
            resetScores();
        }
    }

    private void resetScores() {
        scores.clear();
        for (String key : SCORES_KEYS) {
            scores.put(key, DEFAULT_SCORE);
        }
    }

    private Map<String, Long> decryptMap(Map<String, String> map) {
        Map<String, Long> decryptedMap = new HashMap<>();
        for (String key : SCORES_KEYS) {
            String decryptedInt = scoresEncryptor.decrypt(map.get(key));
            decryptedMap.put(key, Long.parseLong(decryptedInt));
        }
        return decryptedMap;
    }

    private Map<String, String> encryptMap(Map<String, Long> map) {
        Map<String, String> encryptedMap = new HashMap<>();
        for (String key : SCORES_KEYS) {
            encryptedMap.put(key, scoresEncryptor.encrypt(String.valueOf(map.get(key))));
        }
        return encryptedMap;
    }

    private boolean isValidKeys() {
        for (String key : SCORES_KEYS) {
            if (!scores.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
}