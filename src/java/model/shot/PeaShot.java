package model.shot;

import utils.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * <h1>PeaShot</h1>
 *
 * @author AroutinBehnam
 * @version 1.0.0 1/22/2021
 * @see Shot
 */
public class PeaShot extends Shot {
    private static final String PEASHOT_IMAGE = SHOTS_ADDRESS.concat("pea.png");

    /**
     * Constructor initalzie PeaShot variable
     *
     * @param location Location of shot
     */
    public PeaShot(Location location) {
        super(location, 30);
        try {
            image = ImageIO.read(new File(PEASHOT_IMAGE));
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