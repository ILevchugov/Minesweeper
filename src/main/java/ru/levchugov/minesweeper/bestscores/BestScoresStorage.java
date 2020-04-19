package ru.levchugov.minesweeper.bestscores;

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

public class BestScoresStorage {
    private static final Logger logger = LoggerFactory.getLogger(BestScoresStorage.class);

    private static final String FILE_NAME = "High_Scores.json";
    private static final String ENCRYPT_KEY = "COVID-19";
    private static final int DEFAULT_SCORE = 3599;

    private Map<String, Integer> scores;
    private Map<String, String> encryptedScores;

    private final List<String> scoresKeys;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final BasicTextEncryptor scoresEncryptor;

    public BestScoresStorage() {
        this.scoresEncryptor = new BasicTextEncryptor();
        this.encryptedScores = new HashMap<>();
        this.scores = new HashMap<>();
        this.scoresKeys = new ArrayList<>();

        scoresKeys.add(Setting.EASY.getName());
        scoresKeys.add(Setting.MIDDLE.getName());
        scoresKeys.add(Setting.HARD.getName());

        scoresEncryptor.setPassword(ENCRYPT_KEY);

        readScores();
    }

    public void writeScore() {
        try {
            encryptedScores = encryptMap(scores);
            objectMapper.writeValue(new File(FILE_NAME), encryptedScores);
        } catch (IOException e) {
            logger.error("Scores file writing problem");
        }
    }

    public int getScore(String difficulty) {
        return scores.get(difficulty);
    }

    public void setScore(String difficulty, int value) {
        scores.remove(difficulty);
        scores.put(difficulty, value);
        writeScore();
    }


    private void readScores() {
        if (Files.exists(Paths.get(FILE_NAME))) {
            try {
                encryptedScores = objectMapper.readValue(new File(FILE_NAME),
                        new TypeReference<HashMap<String, String>>() {
                        });
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
        for (String key : scoresKeys) {
            scores.put(key, DEFAULT_SCORE);
        }
    }

    private Map<String, Integer> decryptMap(Map<String, String> map) {
        Map<String, Integer> decryptedMap = new HashMap<>();
        for (String key : scoresKeys) {
            String decryptedInt = scoresEncryptor.decrypt(map.get(key));
            decryptedMap.put(key, Integer.parseInt(decryptedInt));
        }
        return decryptedMap;
    }

    private Map<String, String> encryptMap(Map<String, Integer> map) {
        Map<String, String> encryptedMap = new HashMap<>();
        for (String key : scoresKeys) {
            encryptedMap.put(key, scoresEncryptor.encrypt(String.valueOf(map.get(key))));
        }
        return encryptedMap;
    }

    private boolean isValidKeys() {
        for (String key : scoresKeys) {
            if (!scores.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
}