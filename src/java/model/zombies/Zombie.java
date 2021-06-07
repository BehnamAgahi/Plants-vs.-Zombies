package model.zombies;

import model.item.Item;
import model.plants.Plant;
import utils.Location;
import utils.Row;

import javax.swing.*;
import java.awt.*;

/**
 * This class is super class for all kind of zombies.
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/28/2021
 * @see Item
 */
public abstract class Zombie extends Item {
    // The address where the zombie images are stored
    protected final static String ZOMBIES_ADDRESS = "resource/images/zombies/";
    private final static String ZOMBIE_BURNED_IMAGE = ZOMBIES_ADDRESS.concat("zombie/zombie_incinerated.gif");
    private final static String ZOMBIE_DYING_IMAGE = ZOMBIES_ADDRESS.concat("zombie/zombie_dying.gif");

    private final static int HOUSE_WIDTH = 115;

    protected int health;
    protected int damagePerSecond;
    protected double speedPerSecond, firstSpeed;
    private long freezenStartTime, burnedStartTime, eatingStartTime, movingStartTime;

    /*If the zombie fist bite a plant, this variable will be true
    otherwise it will be false*/
    private boolean isFirstBite;

    /*If the zombie burned, this variable will be true
    otherwise it will be false*/
    private boolean isZombieBurned;

    /*If the zombie reached a plant, this variable will be true
    otherwise it will be false*/
    private boolean reachedPlant;

    /*If the zombie get shot by the Snow pea plant, this variable will be true
    otherwise it will be false*/
    private boolean receivedFreezenpeaShot;

    public Zombie(Location location) {
        super(location);

        setFirstBite(false);
        setZombieBurned(false);
        setReachedPlant(false);
        setReceivedFreezenpeaShot(false);
        movingStartTime = System.currentTimeMillis();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamagePerSecond() {
        return damagePerSecond;
    }

    public void setDamagePerSecond(int damagePerSecond) {
        this.damagePerSecond = damagePerSecond;
    }

    public double getSpeedPerSecond() {
        return speedPerSecond;
    }

    public void setSpeedPerSecond(double speedPerSecond) {
        this.speedPerSecond = speedPerSecond;
    }

    public boolean isFirstBite() {
        return isFirstBite;
    }

    public void setFirstBite(boolean firstBite) {
        isFirstBite = firstBite;
    }

    public boolean isReachedPlant() {
        return reachedPlant;
    }

    public void setReachedPlant(boolean reachedPlant) {
        this.reachedPlant = reachedPlant;
    }

    public boolean isReceivedFreezenpeaShot() {
        return receivedFreezenpeaShot;
    }

    public void setReceivedFreezenpeaShot(boolean receivedFreezenpeaShot) {
        if (receivedFreezenpeaShot) {
            double speed = getSpeedPerSecond();
            firstSpeed = speed;
            setSpeedPerSecond(speed * 2);
            freezenStartTime = System.currentTimeMillis();
        }
        this.receivedFreezenpeaShot = receivedFreezenpeaShot;
    }

    public boolean isZombieBurned() {
        return isZombieBurned;
    }

    public void setZombieBurned(boolean zombieBurned) {
        if (zombieBurned) {
            image = new ImageIcon(ZOMBIE_BURNED_IMAGE).getImage();
            burnedStartTime = System.currentTimeMillis();
        }
        isZombieBurned = zombieBurned;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void dameged(int damage) {
        health -= damage;
    }

    /**
     * This mehtod move the zombies
     */
    private void move() {
        if ((System.currentTimeMillis() - movingStartTime) > 250) {
            getLocation().x -= (HOUSE_WIDTH * 250) / (getSpeedPerSecond() * 1000);
            setupRectangle();
            movingStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void setupRectangle() {
        rectangle = new Rectangle(getLocation().x + 25, getLocation().y, 70, 170);
    }

    @Override
    public void update(Row row) {
        // Removes burned zombie after a few seconds
        removeBurnedZombie(row);
        // Zombie eat plants every second
        zombieEatPlant(row);
        // Zombie movment
        zombieMovement();
        // When the effect of the freezen pea is over
        resetFreezenMode();
        // Checking if a zombie could reach the house
        zombieReachTheHouse(row);
        // Checking zombie out of Screen
        zombieOutOfScreen(row);
    }

    private void removeBurnedZombie(Row row) {
        if (isZombieBurned() && (System.currentTimeMillis() - burnedStartTime) > 1500) {
            row.getZombies().remove(this);
        }
    }

    private void zombieEatPlant(Row row) {
        for (int i = row.getPlants().size() - 1; i >= 0; i--) {
            Plant planet = row.getPlants().get(i);

            if (planet != null) {
                if (planet.getRectangle().intersects(getRectangle()) && !isZombieBurned()) {
                    setReachedPlant(true);

                    if (!isFirstBite()) {
                        setFirstBite(true);
                        planet.dameged(getDamagePerSecond());
                        eatingStartTime = System.currentTimeMillis();
                    } else if ((System.currentTimeMillis() - eatingStartTime) > 1000) {
                        planet.dameged(getDamagePerSecond());
                        eatingStartTime = System.currentTimeMillis();
                    }
                    if (planet.isDead()) {
                        row.getPlants().remove(planet);
                        setReachedPlant(false);
                        setFirstBite(false);
                    }
                }
            }
        }
    }

    private void zombieMovement() {
        if (!isReachedPlant() && !isZombieBurned()) move();
    }

    private void resetFreezenMode() {
        if (isReceivedFreezenpeaShot()) {
            if ((System.currentTimeMillis() - freezenStartTime) > 3000) {
                setReceivedFreezenpeaShot(false);
                setSpeedPerSecond(firstSpeed);
            }
        }
    }

    private void zombieReachTheHouse(Row row) {
        if (getLocation().x <= 0) {
            row.setGameOver(true);
        }
    }

    private void zombieOutOfScreen(Row row) {
        if (getLocation().x > 1280) {
            row.getZombies().remove(this);
        }
    }
}