package ru.levchugov.minesweeper.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class Setting {
    public static final Setting EASY = new Setting(9, 9, 4, "Easy");
    public static final Setting MIDDLE = new Setting(16, 16, 40, "Middle");
    public static final Setting HARD = new Setting(16, 30, 99, "Hard");

    private static final String DEFAULT_NAME = "Custom";

    private final int rowNum;
    private final int columnNum;
    private final int minesNum;
    private final String name;

    Setting(int rowNum, int columnNum, int minesNum) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        this.minesNum = minesNum;
        this.name = DEFAULT_NAME;
    }

    private Setting(int rowNum, int columnNum, int minesNum, String name) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        this.minesNum = minesNum;
        this.name = name;
    }

    public boolean isCustom() {
        return name.equals(DEFAULT_NAME);
    }
}
