package model.zombies;

import utils.Location;

import javax.swing.*;

/**
 * <h1>NormalZombie</h1>
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/28/2021
 * @see Zombie
 */
public class NormalZombie extends Zombie {
    public final static String ZOMBIE_IMAGE = ZOMBIES_ADDRESS.concat("zombie/zombie.gif");

    public NormalZombie(Location location) {
        super(location);
        image = new ImageIcon(ZOMBIE_IMAGE).getImage();

        setupRectangle();
        setHealth(200);
        setDamagePerSecond(5);
        setSpeedPerSecond(4);
    }
}