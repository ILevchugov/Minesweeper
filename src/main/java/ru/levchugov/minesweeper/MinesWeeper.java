package ru.levchugov.minesweeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.levchugov.minesweeper.controller.MinesweeperController;
import ru.levchugov.minesweeper.model.MinesweeperGame;
import ru.levchugov.minesweeper.view.SwingMinesweeperView;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class MinesWeeper {
    private static final Logger logger = LoggerFactory.getLogger(MinesWeeper.class);
    private static final int PORT = 8447;
    private static ServerSocket socket;

    public static void main(String[] args) {
        checkIfRunning();

        MinesweeperGame game = new MinesweeperGame();
        MinesweeperController controller = new MinesweeperController(game);
        SwingMinesweeperView view = new SwingMinesweeperView(controller);

        game.attachView(view);
    }

    //trick to forbid running several apps
    private static void checkIfRunning() {
        try {
          socket = new ServerSocket(PORT, 0, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
        } catch (BindException e) {
            logger.warn("Game app already running");
            System.exit(1);
        } catch (IOException e) {
            logger.warn("Unexpected error.");
            System.exit(2);
        }
    }
}
