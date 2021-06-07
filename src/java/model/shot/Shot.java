package model.shot;

import utils.Location;

import java.awt.*;

/**
 * This class manege shots
 *
 * @author AroutinBehnam
 * @version 1.0.0 1/22/2021
 * @see PeaShot
 * @see FreezenPeaShot
 */
public abstract class Shot {
    protected final static String SHOTS_ADDRESS = "resource/images/shot/";
    protected Location location;
    protected Rectangle rectangle;
    protected Image image;

    protected int width, height;
    protected int speed = 12;
    protected int damaged;

    /**
     * This method move shots
     */
    public abstract void move();

    /**
     * This method initalize rectangel
     */
    protected abstract void setupRectangle();

    /**
     * Constructor initalzie shot variable
     *
     * @param location Location of shot
     * @param damaged  Int
     */
    public Shot(Location location, int damaged) {
        this.location = new Location(location.x + 80, location.y + 15);
        this.damaged = damaged;
    }

    /**
     * Constructor initalzie shot variable
     *
     * @param location Location of shot
     * @param speed    Int
     * @param damaged  Int
     */
    public Shot(Location location, int speed, int damaged) {
        this.location = new Location(location.x + 80, location.y + 15);
        this.speed = speed;
        this.damaged = damaged;
    }

    /**
     * Get rectangle
     *
     * @return Rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Get shot image
     *
     * @return Image
     */
    public Image getShotImage() {
        return image;
    }

    /**
     * Get shot damaged
     *
     * @return damaged
     */
    public int getDamaged() {
        return damaged;
    }

    /**
     * Get location
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }
}