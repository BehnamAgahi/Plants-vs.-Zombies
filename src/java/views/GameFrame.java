package views;

import model.LawnMower;
import model.Sun;
import model.item.Item;
import model.plants.Plant;
import model.shot.Shot;
import model.zombies.Zombie;
import utils.Row;
import utils.Sky;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/27/2021
 * @see <a href="http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html">http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html</a><br/>
 * @see <a href="http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html">http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html</a>
 */
public class GameFrame extends JFrame {
    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
    private static final String IMAGE_RESOURCE = "resource/images/";

    // Menu backgrounds
    private static final String START_MENU = IMAGE_RESOURCE.concat("menu/menu.png");
    private static final String MENU_SELECT_NEW_GAME = IMAGE_RESOURCE.concat("menu/menu_select_new_game.png");
    private static final String MENU_SELECT_LOAD_GAME = IMAGE_RESOURCE.concat("menu/menu_select_load_game.png");
    private static final String MENU_SELECT_RANKING = IMAGE_RESOURCE.concat("menu/menu_select_ranking.png");
    private static final String MENU_SELECT_SETTING = IMAGE_RESOURCE.concat("menu/menu_select_setting.png");
    private static final String MENU_SELECT_QUIT_GAME = IMAGE_RESOURCE.concat("menu/menu_select_quit_game.png");

    // Popup menu
    private static final String RESUME_MENU = IMAGE_RESOURCE.concat("resumemenu/resume_menu.png");
    private static final String RESUME_MENU_SELECT_EXIT_GAME = IMAGE_RESOURCE.concat("resumemenu/resume_menu_select_exit_game.png");
    private static final String RESUME_MENU_SELECT_RESUME_GAME = IMAGE_RESOURCE.concat("resumemenu/resume_menu_select_resume_game.png");
    private static final String RESUME_MENU_SELECT_SAVE_GAME = IMAGE_RESOURCE.concat("resumemenu/resume_menu_select_save_game.png");

    // Background
    private static final String BACKGROUND_WINDOW = IMAGE_RESOURCE.concat("background.png");
    private static final String BACKGROUND_WINDOW_SELECT_MENU = IMAGE_RESOURCE.concat("background_select_menu.png");

    private BufferStrategy bufferStrategy;
    private BufferedImage image;
    private final Font font;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        font = new Font("", Font.BOLD, 26);
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        try {
            drawBackground(g2d, state);
            writeStoredSun(g2d, state);
            drawItemInBar(g2d, state);
            drawPlantsZombies(g2d, state);
            drawpreviewItem(g2d, state);
            drawResumeMenu(g2d, state);
            drawSunFromSky(g2d, state);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Draw planets and zombies
     *
     * @param g2d   Graphics2D
     * @param state Game state
     */
    private void drawPlantsZombies(Graphics2D g2d, GameState state) {
        if (state.getState() == GameState.State.NewGame || state.getState() == GameState.State.ResumeMenu) {
            for (int i = 0; i < state.getRows().length; i++) {
                Row row = state.getRows()[i];
                // drawing zombies of the row
                for (int j = 0; j < row.getZombies().size(); j++) {
                    Zombie zombie = row.getZombies().get(j);
                    g2d.drawImage(zombie.getImage(), zombie.getLocation().x, zombie.getLocation().y, 70, 170, null);
                }
                // drawing plantes
                for (int j = 0; j < row.getPlants().size(); j++) {
                    Plant planet = row.getPlants().get(j);
                    if (planet != null) {
                        g2d.drawImage(planet.getImage(), planet.getLocation().x, planet.getLocation().y, 90, 90, null);
                    }
                }
                // drawing shots of each planet
                for (int j = 0; j < row.getPlants().size(); j++) {
                    Plant planet = row.getPlants().get(j);
                    if (planet != null) {
                        for (int k = 0; k < planet.getShots().size(); k++) {
                            //drawing shots of planet
                            Shot shot = planet.getShots().get(k);
                            g2d.drawImage(shot.getShotImage(), shot.getLocation().x, shot.getLocation().y, null);
                        }
                    }
                }
                // drawing machins
                LawnMower lawnMower = row.getLawnMower();
                g2d.drawImage(lawnMower.getImage(), lawnMower.getLocation().x, lawnMower.getLocation().y, 120, 120, null);
            }
        }
    }

    /**
     * This method draw preview item
     *
     * @param g2d   Graphics2D
     * @param state Game state
     */
    private void drawpreviewItem(Graphics2D g2d, GameState state) {
        if (state.getState() == GameState.State.NewGame) {
            Item item = state.getPreviewItem();
            if (item != null) {
                if (item instanceof Plant) {
                    g2d.drawImage(((Plant) item).getPreviewImage(), item.getLocation().x, item.getLocation().y, 90, 90, null);
                } else {
                    g2d.drawImage(item.getImage(), item.getLocation().x, item.getLocation().y, 90, 90, null);
                }
            }
        }
    }

    /**
     * This method draw cards in bar
     *
     * @param g2d   Graphics2D
     * @param state Game state
     */
    private void drawItemInBar(Graphics2D g2d, GameState state) {
        if (state.getState() == GameState.State.NewGame || state.getState() == GameState.State.ResumeMenu) {
            for (int i = 0; i < state.getItemsInBar().size(); i++) {
                Item item = state.getItemsInBar().get(i);
                if (item != null) {
                    if (item instanceof Plant && ((Plant) item).isLocked()) {
                        ((Plant) item).updateCardImage(); // Card loke when used
                    }
                    g2d.drawImage(item.getImage(), item.getLocation().x, item.getLocation().y, 115, 80, null);
                }
            }
        }
    }

    /**
     * This method draw suns
     *
     * @param g2d   Graphics2D
     * @param state Game state
     */
    private void drawSunFromSky(Graphics2D g2d, GameState state) {
        if (state.getState() == GameState.State.NewGame) {
            for (int i = 0; i < state.getSkySuns().size(); i++) {
                Sun sun = state.getSkySuns().get(i);
                g2d.drawImage(Sky.getSunImage(), sun.getLocation().x, sun.getLocation().y, 100, 100, null);
            }
        }
    }

    /**
     * This method write stored sun
     *
     * @param g2d   Graphics2D
     * @param state Game state
     */
    private void writeStoredSun(Graphics2D g2d, GameState state) {
        if (state.getState() == GameState.State.NewGame || state.getState() == GameState.State.ResumeMenu) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(font);
            String storedSun = state.getStoredSun();
            int strWidth = g2d.getFontMetrics().stringWidth(storedSun);
            g2d.drawString(storedSun, 78 - (strWidth / 2), 95);
        }
    }

    /**
     * This method draw background of game
     *
     * @param g2d   Graphics2D
     * @param state Game state
     * @throws IOException IOException
     */
    private void drawBackground(Graphics2D g2d, GameState state) throws IOException {
        updateBackgroundImage(state);
        drawImage(g2d);
    }

    /**
     * This method draw popup menu in ground game
     *
     * @param g2d   Graphics2D
     * @param state Game state
     * @throws IOException IOException
     */
    private void drawResumeMenu(Graphics2D g2d, GameState state) throws IOException {
        if (state.getState() == GameState.State.ResumeMenu) {
            BufferedImage popupMenu = null;
            switch (state.getSelectState()) {
                case ResumeMenu:
                    popupMenu = ImageIO.read(new File(RESUME_MENU));
                    break;
                case SelectSaveGame:
                    popupMenu = ImageIO.read(new File(RESUME_MENU_SELECT_SAVE_GAME));
                    break;
                case SelectExitToMainMenu:
                    popupMenu = ImageIO.read(new File(RESUME_MENU_SELECT_EXIT_GAME));
                    break;
                case SelectResumeGame:
                    popupMenu = ImageIO.read(new File(RESUME_MENU_SELECT_RESUME_GAME));
                    break;
            }
            g2d.drawImage(popupMenu, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        }
    }
    /**
     * This method update background image
     *
     * @param state Game state
     * @throws IOException IOException
     */
    private void updateBackgroundImage(GameState state) throws IOException {
        if (state.getState() == GameState.State.StartMenu) {
            switch (state.getSelectState()) {
                case StartMenu:
                    image = ImageIO.read(new File(START_MENU));
                    break;
                case SelectNewGame:
                    image = ImageIO.read(new File(MENU_SELECT_NEW_GAME));
                    break;
                case SelectLoadGame:
                    image = ImageIO.read(new File(MENU_SELECT_LOAD_GAME));
                    break;
                case SelectRanking:
                    image = ImageIO.read(new File(MENU_SELECT_RANKING));
                    break;
                case SelectSetting:
                    image = ImageIO.read(new File(MENU_SELECT_SETTING));
                    break;
                case SelectQuitGame:
                    image = ImageIO.read(new File(MENU_SELECT_QUIT_GAME));
                    break;
            }
        } else if (state.getState() == GameState.State.NewGame) {
            if (state.getSelectState() == GameState.State.SelectResumeMenu) {
                image = ImageIO.read(new File(BACKGROUND_WINDOW_SELECT_MENU));
            } else {
                image = ImageIO.read(new File(BACKGROUND_WINDOW));
            }
        } else if (state.getState() == GameState.State.ResumeMenu) {
            image = ImageIO.read(new File(BACKGROUND_WINDOW_SELECT_MENU));
        } else if (state.getState() == GameState.State.QuitGame) {
            System.exit(0);
        }
    }

    /**
     * This method debug states
     *
     * @param state GameState
     */
    private void debugState(GameState state) {
        System.out.println("state : " + state.getState());
        System.out.println("select state : " + state.getSelectState());
    }

    /**
     * This method debug mouse location
     *
     * @param g2d   Graphics2D
     * @param state GameState
     */
    private void debugMouse(Graphics2D g2d, GameState state) {
        String str = "mouseX: " + state.getMouseX() + " mouseY: " + state.getMouseY();
        g2d.setColor(Color.CYAN);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        int strWidth = g2d.getFontMetrics().stringWidth(str);
        int strHeight = g2d.getFontMetrics().getHeight();
        g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight + 50);
    }

    /**
     * This method draw images in screen
     *
     * @param g2d Graphics2D
     */
    private void drawImage(Graphics2D g2d) {
        g2d.drawImage(image, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    }
}
