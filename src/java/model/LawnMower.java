package model;

import model.item.Item;
import utils.Location;
import utils.Row;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * This class manege lawn mower
 *
 * @author AroutinBehnam
 * @version 1.1.0 1/28/2021
 */
public class LawnMower extends Item {
    private final static String LAWN_MOWER_IMAGE = "resource/images/others/lawn_mower.png";
    private final static String LAWN_MOWER_GIF = "resource/images/others/lawn_mower.gif";

    private int speed = 7;
    private boolean zombieRich = false;

    /**
     * This constructor initialize lawn mower location and image
     *
     * @param location is a pair number include x and y.
     */
    public LawnMower(Location location) {
        super(location);
        try {
            image = ImageIO.read(new File(LAWN_MOWER_IMAGE));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        setupRectangle();
    }

    /**
     * This constructor initialize lawn mower location, speed and image
     *
     * @param location is a pair number include x and y.
     * @param speed    speed of lawn mower
     */
    public LawnMower(Location location, int speed) {
        super(location);
        this.speed = speed;
        try {
            image = ImageIO.read(new File(LAWN_MOWER_IMAGE));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        setupRectangle();
    }

    @Override
    public void setupRectangle() {
        rectangle = new Rectangle(location.x, location.y, 130, 150);
    }

    @Override
    public void update(Row row) {
        for (int i = 0; i < row.getZombies().size(); i++) {
            if (getRectangle().intersects(row.getZombies().get(i).getRectangle())) {
                row.getZombies().remove(row.getZombies().get(i));
                image = new ImageIcon(LAWN_MOWER_GIF).getImage();
                zombieRich = true;
            }
        }

        if (zombieRich) move();
    }

    private void move() {
        location.x += speed;
        setupRectangle();
    }
}