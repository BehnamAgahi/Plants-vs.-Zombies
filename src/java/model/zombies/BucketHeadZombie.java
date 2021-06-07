package model.zombies;

import utils.GameMode;
import utils.Location;
import utils.Row;

import javax.swing.*;

/**
 * <h1>BucketHeadZombie</h1>
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/28/2021
 * @see Zombie
 */
public class BucketHeadZombie extends Zombie {
    private final static String ZOMBIE_IMAGE = ZOMBIES_ADDRESS.concat("bucketheadzombie/bucket_head_zombie.gif");

    public BucketHeadZombie(Location location, GameMode gameMode) {
        super(location);
        image = new ImageIcon(ZOMBIE_IMAGE).getImage();

        setupRectangle();
        setHealth(1300);
        setDamagePerSecond(gameMode.equals(GameMode.Normal) ? 20 : 25);
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