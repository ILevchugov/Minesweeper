package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.controller.MinesweeperController;
import ru.levchugov.minesweeper.settings.Setting;

import javax.swing.*;
import java.awt.*;

public class CustomSettingFrame extends JFrame {
    private static final int FRAME_WIDTH = 360;
    private static final int FRAME_HEIGHT = 160;

    private static final int MAX_ROWS_NUM = 24;
    private static final int MAX_COLUMNS_NUM = 30;

    private static final int MIN_ROWS_NUM = 9;
    private static final int MIN_COLUMN_NUM = 9;

    private static final int MIN_MINES_NUM = 10;

    private final JLabel rowsNumText;
    private final JLabel columnsNumText;
    private final JLabel minesNumText;

    private final JSlider rowsSlider;
    private final JSlider columnsSlider;
    private final JSlider minesSlider;

    private final JButton acceptButton;
    private final GridBagConstraints gbc;

    CustomSettingFrame(MinesweeperController controller) {
        super("My Settings");

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        rowsNumText = new JLabel();
        rowsNumText.setName("Rows");
        rowsSlider = new JSlider();

        columnsNumText = new JLabel();
        columnsNumText.setName("Columns");
        columnsSlider = new JSlider();

        minesNumText = new JLabel("Mines: ");
        minesNumText.setName("Mines");
        minesSlider = new JSlider();

        acceptButton = new JButton("Accept");
        setListener(controller);

        init();

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void init() {
        setGridItem(rowsNumText, 0, 0, GridBagConstraints.EAST);

        rowsSlider.setMaximum(MAX_ROWS_NUM);
        rowsSlider.setMinimum(MIN_ROWS_NUM);
        rowsSlider.addChangeListener(changeEvent -> minesSlider.setMaximum(Setting.getMaxNumOfMines(rowsSlider.getValue(), columnsSlider.getValue())));
        setSliderListener(rowsSlider, rowsNumText);
        rowsNumText.setText("Rows: " + rowsSlider.getValue());
        setGridItem(rowsSlider, 1, 0, GridBagConstraints.WEST);

        setGridItem(columnsNumText, 0, 1, GridBagConstraints.EAST);

        columnsSlider.setMaximum(MAX_COLUMNS_NUM);
        columnsSlider.setMinimum(MIN_COLUMN_NUM);
        columnsSlider.addChangeListener(changeEvent -> minesSlider.setMaximum(Setting.getMaxNumOfMines(rowsSlider.getValue(), columnsSlider.getValue())));
        setSliderListener(columnsSlider, columnsNumText);
        columnsNumText.setText("Columns: " + columnsSlider.getValue());
        setGridItem(columnsSlider, 1, 1, GridBagConstraints.WEST);

        setGridItem(minesNumText, 0, 2, GridBagConstraints.EAST);
        minesNumText.setText("Mines: " + String.format("%2d", minesSlider.getValue()));

        setSliderListener(minesSlider, minesNumText);
        minesSlider.setMaximum(Setting.getMaxNumOfMines(rowsSlider.getValue(), columnsSlider.getValue()));
        minesSlider.setMinimum(MIN_MINES_NUM);
        setGridItem(minesSlider, 1, 2, GridBagConstraints.WEST);

        gbc.fill = GridBagConstraints.BOTH;
        setGridItem(acceptButton, 1, 3, GridBagConstraints.WEST);
    }

    private void setGridItem(Component component, int gridx, int gridy, int Side) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = Side;
        add(component, gbc);
    }

    private void setSliderListener(JSlider slider, JLabel text) {
        slider.addChangeListener(changeEvent -> text.setText(text.getName() + ": " + slider.getValue()));
    }

    private void setListener(MinesweeperController controller) {
        acceptButton.addActionListener(actionEvent -> {
            controller.setDifficulty(Setting.getCustomSetting(rowsSlider.getValue(), columnsSlider.getValue(), minesSlider.getValue()));
            setVisible(false);
        });
    }
}
