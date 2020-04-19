package ru.levchugov.minesweeper.model;

import org.junit.Assert;
import org.junit.Test;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;

import static org.junit.Assert.*;

public class GameCellTest {
    final GameCell gameCell = new GameCell(1,1);
    @Test
    public void constructor_test(){
        Assert.assertEquals(CellContent.EMPTY, gameCell.getContent());
        Assert.assertEquals(CellStatus.CLOSED, gameCell.getStatus());
    }

    @Test
    public void test_increment_1time() {
        gameCell.increaseBombsNear();
        assertEquals(CellContent.ONE_BOMB_NEAR, gameCell.getContent());
    }

    @Test
    public void test_increment_2time() {
        gameCell.increaseBombsNear();
        gameCell.increaseBombsNear();
        assertEquals(CellContent.TWO_BOMB_NEAR, gameCell.getContent());
    }
    @Test
    public void test_increment_3time() {
        gameCell.increaseBombsNear();
        gameCell.increaseBombsNear();
        gameCell.increaseBombsNear();
        assertEquals(CellContent.THREE_BOMB_NEAR, gameCell.getContent());
    }

    @Test
    public void test_status() {
        gameCell.setStatus(CellStatus.OPENED);
        assertEquals(CellStatus.OPENED, gameCell.getStatus());
    }

    @Test
    public void test_status_flagged() {
        gameCell.setStatus(CellStatus.FLAGGED);
        assertEquals(CellStatus.FLAGGED, gameCell.getStatus());
    }

    @Test
    public void test_content() {
        gameCell.setContent(CellContent.BOMB);
        assertEquals(CellContent.BOMB, gameCell.getContent());

        gameCell.setContent(CellContent.EIGHT_BOMB_NEAR);
        assertEquals(CellContent.EIGHT_BOMB_NEAR, gameCell.getContent());
    }
}