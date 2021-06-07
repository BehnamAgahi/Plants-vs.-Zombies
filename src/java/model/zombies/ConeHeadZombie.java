package model.zombies;

import utils.GameMode;
import utils.Location;
import utils.Row;

import javax.swing.*;

/**
 * <h1>ConeHeadZombie</h1>
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/28/2021
 * @see Zombie
 */
public class ConeHeadZombie extends Zombie {
    private final static String ZOMBIE_IMAGE = ZOMBIES_ADDRESS.concat("coneheadezombie/cone_heade_zombie.gif");

    public ConeHeadZombie(Location location, GameMode gameMode) {
        super(location);
        image = new ImageIcon(ZOMBIE_IMAGE).getImage();

        setupRectangle();
        setHealth(560);
        setDamagePerSecond(gameMode.equals(GameMode.Normal) ? 10 : 15);
        setSpeedPerSecond(gameMode.equals(GameMode.Normal) ? 3.5 : 3.0);
    }

    @Override
    public void update(Row row) {
        super.update(row);

        // Hat will be lose
        if (getHealth() <= 200) {
            image = new ImageIcon(NormalZombie.ZOMBIE_IMAGE).getImage();
        }
    }
}