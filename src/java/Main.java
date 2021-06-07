import utils.ThreadPool;
import views.GameFrame;
import views.GameLoop;

import javax.swing.*;
import java.awt.*;

/**
 * Program start.
 *
 * @author AroutinBehnam
 * @version 1.0.0 1/19/2021
 */
public class Main {
    private static final String GAME_ICON = "resource/images/ic_pvz.png";

    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Start GameFrame
        EventQueue.invokeLater(() -> {
            GameFrame frame = new GameFrame("PVZ");
            frame.setLocationRelativeTo(null); // put frame at center of screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setIconImage(new ImageIcon(GAME_ICON).getImage());
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.initBufferStrategy();
            // Create and execute the game-loop
            GameLoop game = new GameLoop(frame);
            game.init();
            ThreadPool.execute(game);
            // and the game starts ...
        });
    }
}