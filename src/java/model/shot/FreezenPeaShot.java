package model.shot;

import utils.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * <h1>FreezenPeaShot</h1>
 *
 * @author AroutinBehnam
 * @version 1.0.0 1/22/2021
 * @see Shot
 */
public class FreezenPeaShot extends Shot {
    private static final String FREEZEN_PEASHOT_IMAGE = SHOTS_ADDRESS.concat("freeze_pea.png");

    /**
     * Constructor initalzie FreezenPeaShot variable
     *
     * @param location Location of shot
     */
    public FreezenPeaShot(Location location) {
        super(location, 35);
        try {
            image = ImageIO.read(new File(FREEZEN_PEASHOT_IMAGE));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        setupRectangle();
    }

    @Override
    public void move() {
        location.x += speed;
        setupRectangle();
    }

    @Override
    protected void setupRectangle() {
        height = image.getHeight(null);
        width = image.getWidth(null);
        rectangle = new Rectangle(location.x, location.y, width, height);
    }
}