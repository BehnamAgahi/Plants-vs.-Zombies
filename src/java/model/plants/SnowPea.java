package model.plants;

import model.shot.FreezenPeaShot;
import utils.GameMode;
import utils.Location;
import utils.Row;

import javax.swing.*;

/**
 * This class manege snow pea plants and card
 *
 * @author AroutinBehnam
 * @version 1.3.0 1/27/2021
 * @see Plant
 */
public class SnowPea extends Plant {
    public final static int REQUIRDE_SUN = 175;

    private final static String SNOWPEA_CARD = CARD_ADDRESS.concat("freeze_pea_shooter.png");
    private final static String SNOWPEA_CARD_LOCKED = CARD_ADDRESS.concat("freeze_pea_shooter_off.png");
    private final static String SNOWPEA_PREVIEW_IMAGE = PLANTS_ADDRESS.concat("freezepeashooter/freeze_pea_shooter.png");
    private final static String SNOWPEA_IMAGE = PLANTS_ADDRESS.concat("freezepeashooter/freeze_pea_shooter.gif");

    private long startTime;

    /**
     * This constructor initialize the SnowPea <b>card</b>
     *
     * @param gameMode GameMode
     */
    public SnowPea(GameMode gameMode) {
        setupSnowPeaCard(gameMode);
    }

    /**
     * This constructor initialize the SnowPea <b>plant</b>
     *
     * @param location is a pair number include x and y
     */
    public SnowPea(Location location) {
        super(location);
        setupSnowPeaPlant();
    }

    /**
     * This constructor initialize the SnowPea <b>card or plant</b>
     *
     * @param isCard   is a boolean
     * @param location is a pair number include x and y
     * @param gameMode GameMode
     */
    public SnowPea(boolean isCard, Location location, GameMode gameMode) {
        super(location);
        if (isCard) {
            setupSnowPeaCard(gameMode);
        } else {
            setupSnowPeaPlant();
        }
    }

    private void setupSnowPeaCard(GameMode gameMode) {
        location = new Location(485, 10);
        firstCardLocation = new Location(485, 10);
        image = cardImage = new ImageIcon(SNOWPEA_CARD).getImage();
        lockCardImage = new ImageIcon(SNOWPEA_CARD_LOCKED).getImage();
        previewImage = new ImageIcon(SNOWPEA_PREVIEW_IMAGE).getImage();

        // Duration time not same in different game mode
        setDurationTime(gameMode);
        setupCardRectangle();
    }

    private void setupSnowPeaPlant() {
        image = new ImageIcon(SNOWPEA_IMAGE).getImage();
        previewImage = new ImageIcon(SNOWPEA_PREVIEW_IMAGE).getImage();

        setupRectangle();
        setHealth(100);
        startTime = System.currentTimeMillis();
    }

    /**
     * Updates the time variable according to the game mode
     *
     * @param gameMode GameMode
     */
    public void setDurationTime(GameMode gameMode) {
        if (gameMode == GameMode.Normal) {
            cardRechargeTime = 7.5 * 1000; // 7.5 Second
        } else if (gameMode == GameMode.Hard) {
            cardRechargeTime = 30 * 1000; // 30 Second
        }
    }

    @Override
    public void update(Row row) {
        super.update(row);

        if (row.getZombieInRow()) {
            if ((System.currentTimeMillis() - startTime) > 1000) { // shot done every 1 second
                shots.add(new FreezenPeaShot(new Location(location.x, location.y)));
                startTime = System.currentTimeMillis();
            }
        }
    }
}