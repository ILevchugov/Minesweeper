package ru.levchugov.minesweeper.model;

import lombok.Getter;
import lombok.Setter;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameCell {
    private static final List<CellContent> cellContentList;

    static {
        cellContentList = new ArrayList<>();
        cellContentList.add(CellContent.EMPTY);
        cellContentList.add(CellContent.ONE_BOMB_NEAR);
        cellContentList.add(CellContent.TWO_BOMB_NEAR);
        cellContentList.add(CellContent.THREE_BOMB_NEAR);
        cellContentList.add(CellContent.FOUR_BOMB_NEAR);
        cellContentList.add(CellContent.FIVE_BOMB_NEAR);
        cellContentList.add(CellContent.SIX_BOMB_NEAR);
        cellContentList.add(CellContent.SEVEN_BOMB_NEAR);
        cellContentList.add(CellContent.EIGHT_BOMB_NEAR);
    }

    private final int row;
    private final int column;

    private int bombsNearNum;

    @Setter
    private CellStatus status;
    @Setter
    private CellContent content;

    public GameCell(int row, int column) {
        this.status = CellStatus.CLOSED;
        this.content = CellContent.EMPTY;
        this.row = row;
        this.column = column;
    }

    public void increaseBombsNear() {
        bombsNearNum++;
        content = cellContentList.get(bombsNearNum);
    }

}
