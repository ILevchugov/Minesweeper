package ru.levchugov.minesweeper.view;

import lombok.Getter;
import ru.levchugov.minesweeper.controller.MinesweeperController;
import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;


public class MinesweeperField {
    private static final int CELL_WIDTH = 32;
    private static final int CELL_HEIGHT = 32;
    @Getter
    private final JPanel field;
    private final MinesweeperController controller;
    private final IconRegistry iconRegistry = new IconRegistry();

    private JLabel[][] cells;

    MinesweeperField(MinesweeperController controller) {
        this.field = new JPanel();
        this.controller = controller;
    }

    private void setMinesweeperField(int rowNum, int columnNum) {
        field.setLayout(new GridLayout(rowNum, columnNum));
        field.setBackground(Color.WHITE);

        cells = new JLabel[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                cells[i][j] = new JLabel();
                cells[i][j].setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
                Optional<ImageIcon> imageIconOpt = iconRegistry.getImageForCell(CellStatus.CLOSED);
                if (imageIconOpt.isPresent()) {
                    cells[i][j].setIcon(imageIconOpt.get());
                }
                setListenerForCells(i, j);
                field.add(cells[i][j]);
            }
        }
    }

    private void setListenerForCells(int i, int j) {
        cells[i][j].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    controller.handleUserClickedLeftButtonOnCell(i, j);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    controller.handleUserClickedRightButtonOnCell(i, j);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    controller.handleUserClickedMiddleButtonOnCell(i, j);
                }
            }
        });
    }

    void updateCell(int row, int column, CellStatus status) {
        Optional<ImageIcon> imageIconOpt = iconRegistry.getImageForCell(status);
        imageIconOpt.ifPresent(imageIcon -> cells[row][column].setIcon(imageIcon));
    }

    void updateCell(int row, int column, CellContent content) {
        Optional<ImageIcon> imageIconOpt = iconRegistry.getImageForCell(content);
        imageIconOpt.ifPresent(imageIcon -> cells[row][column].setIcon(imageIcon));
    }

    void getNewField(int rowNum, int columnNum) {
        field.removeAll();
        setMinesweeperField(rowNum, columnNum);
        field.repaint();
    }
}
