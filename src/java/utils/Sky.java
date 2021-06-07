package utils;

import model.Sun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Sky class raining sun in game mode
 *
 * @author AroutinBehnam
 * @version 1.1.0 1/20/2021
 * @see Sun
 */
public class Sky {
    private final ArrayList<Sun> suns;
    private static Image sunImage;

    private long startTime;
    private long durationTime = 1000; // 1 second
    private int storedSun = 100;

    /**
     * This constructor initialize valuable and duration time will be update form game mode
     *
     * @param gameMode GameMode
     */
    public Sky(GameMode gameMode) {
        suns = new ArrayList<>();
        try {
            sunImage = ImageIO.read(new File(Sun.SUN_IMAGE_RES));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // Duration time not same in different game mode
        setDurationTime(gameMode);
        updateStartTime();
    }

    /**
     * This runnable moves the suns and checks their a live <br/>
     * In normal mode makes a sun every <b>25 second</b> and in hard mode every <b>30 second</b>
     */
    public void update() {
        Runnable run = () -> {
            if ((System.currentTimeMillis() - startTime) > durationTime) {
                addSun(new Sun());
                updateStartTime();
            }

            if (!suns.isEmpty()) {
                for (int i = 0; i < suns.size(); i++) {
                    Sun sun = suns.get(i);
                    if (sun.isDeprecated()) {
                        suns.remove(sun);
                    }
                    sun.move();
                }
            }
        };
        ThreadPool.execute(run);
    }

    /**
     * Update start time
     */
    public void updateStartTime() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Updates the time variable according to the game mode
     *
     * @param gameMode GameMode
     */
    public void setDurationTime(GameMode gameMode) {
        if (gameMode == GameMode.Normal) {
            durationTime = 25 * 1000; // 25 Second
        } else if (gameMode == GameMode.Hard) {
            durationTime = 30 * 1000; // 30 Second
        }
    }

    /**
     * Add sun in suns array
     *
     * @param sun Sun
     */
    public void addSun(Sun sun) {
        suns.add(sun);
    }

    /**
     * Get suns
     *
     * @return Sun ArrayList
     */
    public List<Sun> getSuns() {
        return suns;
    }

    /**
     * Get sun image
     *
     * @return Image
     */
    public static Image getSunImage() {
        return sunImage;
    }

    /**
     * Set stored sun
     *
     * @param sun int
     */
    public void setStoredSun(int sun) {
        storedSun = sun;
    }

    /**
     * Get stored sun
     *
     * @return storedSun
     */
    public int getStoredSun() {
        return storedSun;
    }

    /**
     * When a sun selected remove that and storedSun update
     *
     * @param rectangle Rectangle
     */
    public void updateStoredSun(Rectangle rectangle) {
        if (!suns.isEmpty()) {
            for (int i = 0; i < suns.size(); i++) {
                Sun sun = suns.get(i);
                if (sun.getRectangle().intersects(rectangle)) {
                    suns.remove(sun);
                    storedSun += 25;
                }
            }
        }
    }

    /**
     * Update stored sun when buy a plant
     *
     * @param sun required sun
     */
    public void updateStoredSun(int sun) {
        storedSun -= sun;
    }
}