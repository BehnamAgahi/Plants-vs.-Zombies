package model.plants;

import model.shot.PeaShot;
import utils.Location;
import utils.Row;

import javax.swing.*;

/**
 * This class manege peashooter plants and card
 *
 * @author AroutinBehnam
 * @version 1.3.0 1/27/2021
 * @see Plant
 */
public class Peashooter extends Plant {
    public final static int REQUIRDE_SUN = 100;

    private final static String PEASHOOTER_CARD = CARD_ADDRESS.concat("pea_shooter.png");
    private final static String PEASHOOTER_CARD_LOCKED = CARD_ADDRESS.concat("pea_shooter_off.png");
    private final static String PEASHOOTER_PREVIEW_IMAGE = PLANTS_ADDRESS.concat("peashooter/pea_shooter.png");
    private final static String PEASHOOTER_IMAGE = PLANTS_ADDRESS.concat("peashooter/pea_shooter.gif");

    private long startTime;

    /**
     * This constructor initialize the Peashooter <b>card</b>
     */
    public Peashooter() {
        setupPeashooterCard();
    }

    /**
     * This constructor initialize the Peashooter <b>plant</b>
     *
     * @param location is a pair number include x and y
     */
    public Peashooter(Location location) {
        super(location);
        setupPeashooterPlant();
    }

    /**
     * This constructor initialize the Peashooter <b>card or plant</b>
     *
     * @param isCard   is a boolean
     * @param location is a pair number include x and y
     */
    public Peashooter(boolean isCard, Location location) {
        super(location);
        if (isCard) {
            setupPeashooterCard();
        } else {
            setupPeashooterPlant();
        }
    }

    private void setupPeashooterCard() {
        location = new Location(255, 10);
        firstCardLocation = new Location(255, 10);

        image = cardImage = new ImageIcon(PEASHOOTER_CARD).getImage();
        lockCardImage = new ImageIcon(PEASHOOTER_CARD_LOCKED).getImage();
        previewImage = new ImageIcon(PEASHOOTER_PREVIEW_IMAGE).getImage();

        cardRechargeTime *= 7.5;
        setupCardRectangle();
    }

    private void setupPeashooterPlant() {
        image = new ImageIcon(PEASHOOTER_IMAGE).getImage();
        previewImage = new ImageIcon(PEASHOOTER_PREVIEW_IMAGE).getImage();

        setupRectangle();
        setHealth(70);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update(Row row) {
        super.update(row);

        if (row.getZombieInRow()) {
            if ((System.currentTimeMillis() - startTime) > 1000) { // shot done every 1 second
                shots.add(new PeaShot(new Location(location.x, location.y)));
                startTime = System.currentTimeMillis();
            }
        }
    }
}