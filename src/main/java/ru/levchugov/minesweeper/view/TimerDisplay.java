package ru.levchugov.minesweeper.view;

import lombok.Getter;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimerDisplay {
    private static final String DEFAULT_TIME = "00:00";
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
    @Getter
    private final JLabel timeText;
    private final Timer timer;
    private long startTime;

    TimerDisplay() {
        this.timeText = new JLabel();
        timeText.setText(DEFAULT_TIME);
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.timer = new Timer(1000, actionEvent -> timeText.setText(timeFormat.format(
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime))));
    }

    public void startTimer() {
        startTime = System.nanoTime();
        timeText.setText(DEFAULT_TIME);
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer() {
        timeText.setText(DEFAULT_TIME);
        timer.stop();
    }
}
