package ru.levchugov.minesweeper.model;

import lombok.Getter;
import lombok.Setter;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameCell {
    private static final Map<Integer, CellContent> CELL_CONTENT_MAP;

    static {
        CELL_CONTENT_MAP = new HashMap<>();
        CELL_CONTENT_MAP.put(0, CellContent.EMPTY);
        CELL_CONTENT_MAP.put(1, CellContent.ONE_BOMB_NEAR);
        CELL_CONTENT_MAP.put(2, CellContent.TWO_BOMB_NEAR);
        CELL_CONTENT_MAP.put(3, CellContent.THREE_BOMB_NEAR);
        CELL_CONTENT_MAP.put(4, CellContent.FOUR_BOMB_NEAR);
        CELL_CONTENT_MAP.put(5, CellContent.FIVE_BOMB_NEAR);
        CELL_CONTENT_MAP.put(6, CellContent.SIX_BOMB_NEAR);
        CELL_CONTENT_MAP.put(7, CellContent.SEVEN_BOMB_NEAR);
        CELL_CONTENT_MAP.put(8, CellContent.EIGHT_BOMB_NEAR);
    }

    private final int row;
    private final int column;

    private int bombsNearNum;

    @Setter
    private CellStatus status;

    private CellContent content;

    public GameCell(int row, int column) {
        this.status = CellStatus.CLOSED;
        this.content = CellContent.EMPTY;
        this.row = row;
        this.column = column;
    }

    public void changeBombsNear(int bombsNearNum) {
        this.bombsNearNum = bombsNearNum;
        content = CELL_CONTENT_MAP.get(bombsNearNum);
    }

    public void setBomb() {
        content = CellContent.BOMB;
    }
}
