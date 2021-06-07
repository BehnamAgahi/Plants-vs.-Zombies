package model.plants;

import utils.Location;
import utils.Row;

import javax.swing.*;

/**
 * This class manege wallnut plants and card
 *
 * @author AroutinBehnam
 * @version 1.3.0 1/27/2021
 * @see Plant
 */
public class Wallnut extends Plant {
    public final static int REQUIRDE_SUN = 50;

    private final static String WALLNUT_CARD = CARD_ADDRESS.concat("wall_nut.png");
    private final static String WALLNUT_CARD_LOCKED = CARD_ADDRESS.concat("wall_nut_off.png");
    private final static String WALLNUT_PREVIEW_IMAGE = PLANTS_ADDRESS.concat("wallnut/wall_nut.png");
    private final static String WALLNUT_FULL_LIFE_IMAGE = PLANTS_ADDRESS.concat("wallnut/wall_nut_full_life.gif");
    private final static String WALLNUT_HALF_LIFE_IMAGE = PLANTS_ADDRESS.concat("wallnut/wall_nut_half_life.gif");
    private final static String WALLNUT_DEAD_IMAGE = PLANTS_ADDRESS.concat("wallnut/wall_nut_dead.gif");

    /**
     * This constructor initialize the Wallnut <b>card</b>
     */
    public Wallnut() {
        setupWallnutCard();
    }

    /**
     * This constructor initialize the Wallnut <b>plant</b>
     *
     * @param location is a pair number include x and y
     */
    public Wallnut(Location location) {
        super(location);
        setupWallnutPlant();
    }

    /**
     * This constructor initialize the Wallnut <b>card or plant</b>
     *
     * @param isCard   is a boolean
     * @param location is a pair number include x and y
     */
    public Wallnut(boolean isCard, Location location) {
        super(location);
        if (isCard) {
            setupWallnutCard();
        } else {
            setupWallnutPlant();
        }
    }

    private void setupWallnutCard() {
        location = new Location(370, 10);
        firstCardLocation = new Location(370, 10);

        image = cardImage = new ImageIcon(WALLNUT_CARD).getImage();
        lockCardImage = new ImageIcon(WALLNUT_CARD_LOCKED).getImage();
        previewImage = new ImageIcon(WALLNUT_PREVIEW_IMAGE).getImage();

        cardRechargeTime *= 30;
        setupCardRectangle();
    }

    private void setupWallnutPlant() {
        image = new ImageIcon(WALLNUT_FULL_LIFE_IMAGE).getImage();
        previewImage = new ImageIcon(WALLNUT_PREVIEW_IMAGE).getImage();

        setupRectangle();
        setHealth(150);
    }

    private void updateImage() {
        if (health > 0 && health <= 75) {
            image = new ImageIcon(WALLNUT_HALF_LIFE_IMAGE).getImage();
        } else if (health <= 0) {
            image = new ImageIcon(WALLNUT_DEAD_IMAGE).getImage();
        }
    }

    @Override
    public void update(Row row) {
        updateImage();
    }
}