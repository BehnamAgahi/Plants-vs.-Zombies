package model.plants;

import model.item.Item;
import model.shot.FreezenPeaShot;
import model.shot.Shot;
import utils.Location;
import utils.Row;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class manege Plants
 *
 * @author AroutinBehnam
 * @version 1.2.0 1/27/2021
 * @see Item
 */
public abstract class Plant extends Item {
    protected final static String PLANTS_ADDRESS = "resource/images/plants/";
    protected final static String CARD_ADDRESS = "resource/images/cards/";

    protected ArrayList<Shot> shots;
    protected Image cardImage, lockCardImage, previewImage;
    protected Location firstCardLocation;
    protected double cardRechargeTime = 1000; // 1 Second
    protected long startCardRechargeTime;
    protected boolean isLocked = false;
    protected int health;

    public Plant() {
        super();
        shots = new ArrayList<>();
    }

    /**
     * This constructor initialize the plant values
     *
     * @param location Location
     */
    public Plant(Location location) {
        super(location);
        shots = new ArrayList<>();
    }

    /**
     * This constructor initialize the plant values
     *
     * @param location     Location
     * @param previewImage Image
     */
    public Plant(Location location, Image previewImage) {
        super(location, previewImage);
        shots = new ArrayList<>();
    }

    /**
     * This constructor initialize the plant values
     *
     * @param location Location
     * @param image    Image
     */
    public Plant(Location location, Image image, Image previewImage) {
        super(location, image);
        shots = new ArrayList<>();
    }

    @Override
    public void setupRectangle() {
        rectangle = new Rectangle(location.x, location.y, 125, 105);
    }

    public void setupCardRectangle() {
        rectangle = new Rectangle(location.x, location.y, 115, 80);
    }

    /**
     * Get previewImage
     *
     * @return List of shots
     */
    public ArrayList<Shot> getShots() {
        return shots;
    }

    /**
     * Get previewImage
     *
     * @return Image
     */
    public Image getPreviewImage() {
        return previewImage;
    }

    /**
     * Set plant health
     *
     * @param health Int
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Get plant health
     *
     * @return Int
     */
    public int getHealth() {
        return health;
    }

    /**
     * Plant card is locked
     *
     * @return boolean
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * This method update image and location when plant card selected
     *
     * @param isSelect boolean
     */
    public void itemSelected(boolean isSelect) {
        updateImage(isSelect);
        updateLocation(isSelect);
    }

    private void updateLocation(boolean isSelect) {
        if (!isSelect) { // Update location when plant card released
            location.x = firstCardLocation.x;
            location.y = firstCardLocation.y;
        }
    }

    private void updateImage(boolean isSelect) {
        if (isSelect) // When plant card selected card image change
            image = previewImage;
        else // and aloso when plant card released image become card face
            image = cardImage;
    }

    /**
     * This method update card image between cardImage and lockCardImage
     */
    public void updateCardImage() {
        image = lockCardImage;
        if ((System.currentTimeMillis() - startCardRechargeTime) > cardRechargeTime) {
            image = cardImage;
            isLocked = false;
        }
    }

    /**
     * This method initalize startCardRechargeTime value
     */
    public void startRechargeTime() {
        isLocked = true;
        startCardRechargeTime = System.currentTimeMillis();
    }

    /**
     * Check plant is dead
     *
     * @return if plant is dead return true else then false
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Damage to the plant
     *
     * @param damage Int
     */
    public void dameged(int damage) {
        health -= damage;
    }

    @Override
    public void update(Row row) {
        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.move();
            try {
                if (!row.getFirstZombie().isZombieBurned() && row.getFirstZombie().isAlive() && row.getFirstZombie().getRectangle().intersects(shot.getRectangle())) {
                    if (shot instanceof FreezenPeaShot) { // If shot freezen zombie freez
                        row.getFirstZombie().setReceivedFreezenpeaShot(true);
                    }
                    shots.remove(shot);
                    // Zombie hert
                    row.getFirstZombie().dameged(shot.getDamaged());
                    // Checking if zombie is dead
                    if (!row.getFirstZombie().isAlive()) {
                        row.getZombies().remove(row.getFirstZombie());
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            // Removes the shot that goes out of frame
            if (shot.getLocation().x > 1280) {
                shots.remove(shot);
            }
        }
    }
}