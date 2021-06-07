package model.item;

import model.LawnMower;
import model.Shovel;
import model.plants.Plant;
import model.zombies.Zombie;
import utils.Location;

import java.awt.*;

/**
 * This class manege Items like plants, zombies, lawn mower or Shovel
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/21/2021
 * @see Plant
 * @see Zombie
 * @see LawnMower
 * @see Shovel
 */
public abstract class Item implements OnUpdateItems {
    protected Location location;
    protected Image image;
    protected Rectangle rectangle;

    /**
     * Setup rectangle
     */
    public abstract void setupRectangle();

    public Item() {
    }

    /**
     * This constructor initialize the item values
     *
     * @param location Location
     */
    public Item(Location location) {
        this.location = location;
    }

    /**
     * This constructor initialize the item values
     *
     * @param location Location
     * @param image    Image
     */
    public Item(Location location, Image image) {
        this.location = location;
        this.image = image;
    }

    /**
     * Get location
     *
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Get image
     *
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get rectangle behind item
     *
     * @return Rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
}