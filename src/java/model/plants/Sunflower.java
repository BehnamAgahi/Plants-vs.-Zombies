package model.plants;

import model.Sun;
import utils.GameMode;
import utils.Location;
import utils.Row;

import javax.swing.*;

/**
 * This class manege sunflower plants and card
 *
 * @author AroutinBehnam
 * @version 1.2.0 1/27/2021
 * @see Plant
 */
public class Sunflower extends Plant {
    public final static int REQUIRDE_SUN = 50;

    private final static String SUNFLOWER_CARD = CARD_ADDRESS.concat("sun_flower.png");
    private final static String SUNFLOWER_CARD_LOCKED = CARD_ADDRESS.concat("sun_flower_off.png");
    private final static String SUNFLOWER_PREVIEW_IMAGE = PLANTS_ADDRESS.concat("sunflower/sun_flower.png");
    private final static String SUNFLOWER_IMAGE = PLANTS_ADDRESS.concat("sunflower/sun_flower.gif");

    private long startTime;
    private long durationTime;
    private int fallingSpeed = -1;

    /**
     * This constructor initialize the Sunflower <b>card</b>
     */
    public Sunflower() {
        setupSunflowerCard();
    }

    /**
     * This constructor initialize the Sunflower <b>plant</b>
     *
     * @param gameMode Gamemode (Normal, Hard)
     * @param location is a pair number include x and y
     */
    public Sunflower(GameMode gameMode, Location location) {
        super(location);
        setupSunflowerPlant(gameMode);
    }

    /**
     * This constructor initialize the Sunflower <b>plant</b>
     *
     * @param gameMode     Gamemode (Normal, Hard)
     * @param location     is a pair number include x and y
     * @param fallingSpeed speed of falling sun
     */
    public Sunflower(GameMode gameMode, Location location, int fallingSpeed) {
        super(location);
        setupSunflowerPlant(gameMode, fallingSpeed);
    }

    /**
     * This constructor initialize the Sunflower <b>card or plant</b>
     *
     * @param isCard       is a boolean
     * @param gameMode     Gamemode (Normal, Hard)
     * @param location     is a pair number include x and y
     * @param fallingSpeed speed of falling sun
     */
    public Sunflower(boolean isCard, GameMode gameMode, Location location, int fallingSpeed) {
        super(location);
        if (isCard) {
            setupSunflowerCard();
        } else {
            setupSunflowerPlant(gameMode, fallingSpeed);
        }
    }

    private void setupSunflowerCard() {
        image = cardImage = new ImageIcon(SUNFLOWER_CARD).getImage();
        lockCardImage = new ImageIcon(SUNFLOWER_CARD_LOCKED).getImage();
        previewImage = new ImageIcon(SUNFLOWER_PREVIEW_IMAGE).getImage();

        this.location = new Location(140, 10);
        firstCardLocation = new Location(140, 10);
        cardRechargeTime *= 7.5;
        setupCardRectangle();
    }

    private void setupSunflowerPlant(GameMode gameMode) {
        image = new ImageIcon(SUNFLOWER_IMAGE).getImage();
        previewImage = new ImageIcon(SUNFLOWER_PREVIEW_IMAGE).getImage();

        // Duration time not same in different game mode
        setDurationTime(gameMode);
        setupRectangle();
        setHealth(50);
        startTime = System.currentTimeMillis();
    }

    private void setupSunflowerPlant(GameMode gameMode, int fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
        setupSunflowerPlant(gameMode);
    }

    /**
     * Updates the time variable according to the game mode
     *
     * @param gameMode GameMode
     */
    public void setDurationTime(GameMode gameMode) {
        if (gameMode == GameMode.Normal) {
            durationTime = 20 * 1000; // 20 Second
        } else if (gameMode == GameMode.Hard) {
            durationTime = 25 * 1000; // 25 Second
        }
    }

    @Override
    public void update(Row row) {
        if ((System.currentTimeMillis() - startTime) > durationTime) {
            Location location = new Location(getLocation().x, getLocation().y);

            if (fallingSpeed > 0) {
                row.getSky().addSun(new Sun(location, fallingSpeed));
            } else {
                row.getSky().addSun(new Sun(location));
            }
            startTime = System.currentTimeMillis();
        }
    }
}