package model;

import utils.Location;
import utils.Sky;

import java.awt.*;
import java.security.SecureRandom;

/**
 * This class manege sun from sky or plant
 *
 * @author AroutinBehnam
 * @version 1.1.0 1/27/2021
 * @see Sky
 */
public class Sun {
    public static final String SUN_IMAGE_RES = "resource/images/others/sun.png";
    private static final long DIE_TIME = 5 * 1000; // 5 second

    private Location location;
    private Rectangle rectangle;

    private final int fallingDistance;
    private final boolean fromFlower;

    private long startTime = 0;
    private int fallingSpeed = 7;
    private int locationY;
    private boolean timeIsSet = false;
    private boolean sunHasStoppedVertically = false;
    private boolean goingUpIsDone = false;

    /**
     * This constructor initialize sun from <b>flower</b>
     *
     * @param location is a pair number include x and y.
     */
    public Sun(Location location) {
        SecureRandom random = new SecureRandom();
        this.fromFlower = true;

        fallingDistance = 100 + random.nextInt(50);
        if (location != null) {
            this.location = location;
            locationY = location.y;
        }
    }

    /**
     * This constructor initialize sun from <b>sky</b>
     */
    public Sun() {
        SecureRandom random = new SecureRandom();
        this.fromFlower = false;

        fallingDistance = 110 + random.nextInt(500);
        this.location = new Location();
        this.location.x = 55 + random.nextInt(1100);
    }

    /**
     * This constructor initialize sun from <b>flower</b>
     *
     * @param location     is a pair number include x and y.
     * @param fallingSpeed speed of falling sun
     */
    public Sun(Location location, int fallingSpeed) {
        SecureRandom random = new SecureRandom();
        this.fromFlower = true;
        this.fallingSpeed = fallingSpeed;

        fallingDistance = 100 + random.nextInt(50);
        if (location != null) {
            this.location = location;
            locationY = location.y;
        }
    }

    /**
     * This constructor initialize sun from <b>sky</b>
     *
     * @param fallingSpeed speed of falling sun
     */
    public Sun(int fallingSpeed) {
        SecureRandom random = new SecureRandom();
        this.fromFlower = false;
        this.fallingSpeed = fallingSpeed;

        fallingDistance = 110 + random.nextInt(500);
        this.location = new Location();
        this.location.x = 55 + random.nextInt(1100);
    }

    /**
     * This constructor initialize sun from <b>flower or sky</b> <br/>
     * sun from sky doesn't need {@code location} and {@code fromFlower} is {@code false} <br/>
     * also sun from flower need {@code location} and {@code fromFlower} is {@code true}
     *
     * @param location   is a pair number include x and y.
     * @param fromFlower is a boolean
     */
    public Sun(boolean fromFlower, Location location) {
        SecureRandom random = new SecureRandom();
        this.fromFlower = fromFlower;

        if (fromFlower) {
            fallingDistance = 100 + random.nextInt(50);
            if (location != null) {
                this.location = location;
                locationY = location.y;
            }
        } else {
            fallingDistance = 110 + random.nextInt(500);
            this.location = new Location();
            this.location.x = 55 + random.nextInt(1100);
        }
    }

    /**
     * This constructor initialize sun from <b>flower or sky</b> <br/>
     * sun from sky doesn't need {@code location} and {@code fromFlower} is {@code false} <br/>
     * also sun from flower need {@code location} and {@code fromFlower} is {@code true}
     *
     * @param location     is a pair number include x and y.
     * @param fromFlower   is a boolean
     * @param fallingSpeed speed of falling sun
     */
    public Sun(boolean fromFlower, Location location, int fallingSpeed) {
        SecureRandom random = new SecureRandom();
        this.fromFlower = fromFlower;
        this.fallingSpeed = fallingSpeed;

        if (fromFlower) {
            fallingDistance = 100 + random.nextInt(50);
            if (location != null) {
                this.location = location;
                locationY = location.y;
            }
        } else {
            fallingDistance = 110 + random.nextInt(500);
            this.location = new Location();
            this.location.x = 55 + random.nextInt(1100);
        }
    }

    /**
     * This method move suns with 2 different way : <br/>
     * 1. raining sun <br/>
     * 2. dragged from flower <br/>
     */
    public void move() {
        if (fromFlower) {
            if (!goingUpIsDone) {
                if (location.y > locationY - 85) {
                    location.y -= fallingSpeed;
                } else {
                    goingUpIsDone = true;
                    locationY = location.y;
                }
            } else if (location.y < locationY + fallingDistance) {
                location.y += fallingSpeed - 1;
            } else {
                sunHasStoppedVertically = true;
            }
        } else if (location.y < fallingDistance) {
            location.y += fallingSpeed;
        } else {
            sunHasStoppedVertically = true;
        }

        // Checking if the sun has stopped moving
        if (sunHasStoppedVertically && !timeIsSet) {
            startTime = System.currentTimeMillis();
            timeIsSet = true;
        }
        // init rectangle
        setupRectangle();
    }

    private void setupRectangle() {
        rectangle = new Rectangle(location.x, location.y, 100, 100);
    }

    /**
     * Get rectangle behind sun
     *
     * @return Rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Get location of sun
     *
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Check sun stile alive
     *
     * @return Boolean
     */
    public boolean isDeprecated() {
        return startTime != 0 && (System.currentTimeMillis() - startTime) > DIE_TIME;
    }
}