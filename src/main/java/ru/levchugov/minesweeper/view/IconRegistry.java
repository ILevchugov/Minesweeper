package ru.levchugov.minesweeper.view;

import ru.levchugov.minesweeper.cellstate.CellContent;
import ru.levchugov.minesweeper.cellstate.CellStatus;

import javax.swing.*;
import java.util.*;

public class IconRegistry {
    private final Map<CellContent, ImageIcon> cellIconMap = new HashMap<>();
    private final Map<CellStatus, ImageIcon> cellStatusIconMap = new HashMap<>();

    private final List<ImageIcon> emptyIconArray = new ArrayList<>();

    private final ImageIcon minesCounterIcon;
    private final ImageIcon timer;

    IconRegistry() {
        cellIconMap.put(CellContent.BOMB, new ImageIcon(IconRegistry.class.getResource("/images/corona.png")));
        cellIconMap.put(CellContent.ONE_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/one.png")));
        cellIconMap.put(CellContent.TWO_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/two.png")));
        cellIconMap.put(CellContent.THREE_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/three.png")));
        cellIconMap.put(CellContent.FOUR_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/four.png")));
        cellIconMap.put(CellContent.FIVE_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/five.png")));
        cellIconMap.put(CellContent.SIX_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/six.png")));
        cellIconMap.put(CellContent.SEVEN_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/seven.png")));
        cellIconMap.put(CellContent.EIGHT_BOMB_NEAR, new ImageIcon(IconRegistry.class.getResource("/images/eight.png")));
        cellIconMap.put(CellContent.EMPTY, new ImageIcon(IconRegistry.class.getResource("/images/empty.png")));

        cellStatusIconMap.put(CellStatus.FLAGGED, new ImageIcon((IconRegistry.class.getResource("/images/flag.png"))));
        cellStatusIconMap.put(CellStatus.CLOSED, new ImageIcon((IconRegistry.class.getResource("/images/closed.png"))));

        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/doctor.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/face-mask.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/hand-sanitizer.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/toilet.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/washhands.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/doctor_w.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/medicine.png")));
        emptyIconArray.add(new ImageIcon(IconRegistry.class.getResource("/images/emptycells/grain.png")));

        timer = new ImageIcon((IconRegistry.class.getResource("/images/timer.png")));
        minesCounterIcon = new ImageIcon((IconRegistry.class.getResource("/images/mines_counter.png")));
    }

    Optional<ImageIcon> getImageForCell(CellContent content) {
        if (content == CellContent.EMPTY) {
            Random random = new Random();
            return Optional.ofNullable(emptyIconArray.get(random.nextInt(emptyIconArray.size())));
        }
        return Optional.ofNullable(cellIconMap.get(content));
    }

    Optional<ImageIcon> getImageForCell(CellStatus status) {
        return Optional.ofNullable(cellStatusIconMap.get(status));
    }

    Optional<ImageIcon> getMinesCounterImage() {
        return Optional.ofNullable(minesCounterIcon);
    }

    Optional<ImageIcon> getTimer() {
        return Optional.ofNullable(timer);
    }
}
