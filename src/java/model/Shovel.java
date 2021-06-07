package model;

import model.item.Item;
import utils.Location;
import utils.Row;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * This class manege shovel
 *
 * @author AroutinBehnam
 * @version 1.2.0 1/28/2021
 */
public class Shovel extends Item {
    private final static String SHOVEL_IMAGE = "resource/images/others/shovel.png";

    private Location firstLocation;

    /**
     * This constructor initialize shovel <b>card</b>
     */
    public Shovel() {
        location = new Location(750, 10);
        firstLocation = new Location(750, 10);
        setupShovel();
    }

    /**
     * This constructor initialize the shovel <b>plant</b>
     *
     * @param location is a pair number include x and y
     */
    public Shovel(Location location) {
        super(location);
        setupShovel();
    }

    private void setupShovel() {
        try {
            image = ImageIO.read(new File(SHOVEL_IMAGE));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        setupRectangle();
    }

    /**
     * This method update location shovel when selected
     *
     * @param isSelect boolean
     */
    public void itemSelected(boolean isSelect) {
        if (!isSelect) { // Update location when shovel released
            location.x = firstLocation.x;
            location.y = firstLocation.y;
        }
    }

    @Override
    public void setupRectangle() {
        rectangle = new Rectangle(location.x, location.y, 115, 80);
    }

    @Override
    public void update(Row row) {}
}