package model.plants;

import model.zombies.Zombie;
import utils.GameMode;
import utils.Location;
import utils.Row;

import javax.swing.*;
import java.awt.*;

/**
 * This class manege cherry bomb plants and card
 *
 * @author AroutinBehnam
 * @version 1.3.0 1/27/2021
 * @see Plant
 */
public class CherryBomb extends Plant {
    public final static int REQUIRDE_SUN = 150;

    private final static String CHERRYBOMB_CARD = CARD_ADDRESS.concat("cherry_bomb.png");
    private final static String CHERRYBOMB_CARD_LOCKED = CARD_ADDRESS.concat("cherry_bomb_off.png");
    private final static String CHERRYBOMB_PREVIEW_IMAGE = PLANTS_ADDRESS.concat("cherrybomb/cherry_bomb.png");
    private final static String CHERRYBOMB_IMAGE = PLANTS_ADDRESS.concat("cherrybomb/cherry_bomb.png");
    private final static String CHERRYBOMB_DEAD_IMAGE = PLANTS_ADDRESS.concat("cherrybomb/cherry_bomb_dead.gif");

    private Location rectangleLocation;
    private Rectangle[][] screen;
    private boolean isExploded = false;

    /**
     * This constructor initialize the CherryBomb <b>card</b>
     *
     * @param gameMode GameMode
     */
    public CherryBomb(GameMode gameMode) {
        setupCherryBombCard(gameMode);
    }

    /**
     * This constructor initialize the CherryBomb <b>plant</b>
     *
     * @param location          is a pair number include x and y
     * @param rectangleLocation Rectangle
     */
    public CherryBomb(Location location, Location rectangleLocation, Rectangle[][] screen) {
        super(location);
        setupCherryBombPlant(rectangleLocation, screen);
    }

    /**
     * This constructor initialize the CherryBomb <b>card or plant</b>
     *
     * @param isCard            is a boolean
     * @param location          is a pair number include x and y
     * @param rectangleLocation Rectangle
     * @param gameMode          GameMode
     */
    public CherryBomb(boolean isCard, Location location, Location rectangleLocation, Rectangle[][] screen, GameMode gameMode) {
        super(location);
        if (isCard) {
            setupCherryBombCard(gameMode);
        } else {
            setupCherryBombPlant(rectangleLocation, screen);
        }
    }

    private void setupCherryBombCard(GameMode gameMode) {
        location = new Location(600, 10);
        firstCardLocation = new Location(600, 10);

        image = cardImage = new ImageIcon(CHERRYBOMB_CARD).getImage();
        lockCardImage = new ImageIcon(CHERRYBOMB_CARD_LOCKED).getImage();
        previewImage = new ImageIcon(CHERRYBOMB_PREVIEW_IMAGE).getImage();

        // Duration time not same in different game mode
        setDurationTime(gameMode);
        setupCardRectangle();
    }

    private void setupCherryBombPlant(Location rectangleLocation, Rectangle[][] screen) {
        image = new ImageIcon(CHERRYBOMB_IMAGE).getImage();
        previewImage = new ImageIcon(CHERRYBOMB_PREVIEW_IMAGE).getImage();

        setupRectangle();
        setHealth(70);
        this.rectangleLocation = rectangleLocation;
        this.screen = screen;
    }

    /**
     * Updates the time variable according to the game mode
     *
     * @param gameMode GameMode
     */
    public void setDurationTime(GameMode gameMode) {
        if (gameMode == GameMode.Normal) {
            cardRechargeTime = 30 * 1000; // 30 Second
        } else if (gameMode == GameMode.Hard) {
            cardRechargeTime = 45 * 1000; // 45 Second
        }
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void update(Row[] rows) {
        if (health <= 35) { // shot done every 1 second
            explodCheery(rows);
        }
    }

    /**
     * Burns all the zombies in 9 houses around cherry bomb
     *
     * @param rows Row array
     */
    private void explodCheery(Row[] rows) {
        image = new ImageIcon(CHERRYBOMB_DEAD_IMAGE).getImage();

        for (int i = rectangleLocation.x + 1; i > rectangleLocation.x - 2; i--) {
            for (int j = rectangleLocation.y + 1; j > rectangleLocation.y - 2; j--) {
                try {
                    for (int k = 0; k < rows[i].getZombies().size(); k++) {
                        try {
                            Zombie zombie = rows[i].getZombies().get(k);
                            if (screen[i][j].intersects(zombie.getRectangle())) {
                                zombie.setZombieBurned(true);
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        isExploded = true;
    }
}