package ru.levchugov.minesweeper.settings;

public final class SettingUtils {
    private SettingUtils() {
    }

    public static Setting getCustomSetting(int rowNum, int columnNum, int minesNum) {
        return new Setting(rowNum, columnNum, minesNum);
    }

    //Impossible to create more mines
    public static int getMaxNumOfMines(int rowNum, int columnNum) {
        return (rowNum - 1) * (columnNum - 1);
    }
}
