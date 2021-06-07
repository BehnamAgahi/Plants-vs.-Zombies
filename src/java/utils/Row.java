package utils;

import model.LawnMower;
import model.Shovel;
import model.item.Item;
import model.plants.*;
import model.zombies.BucketHeadZombie;
import model.zombies.ConeHeadZombie;
import model.zombies.NormalZombie;
import model.zombies.Zombie;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Row</h1>
 * This class which handles zombies moving, planets shooting and death of zombies or plants
 *
 * @author AroutinBehnam
 * @version 1.1.0 1/27/2021
 */
public class Row {
    private Sky sky;
    private Rectangle[][] screen;
    private Zombie firstZombie;
    private LawnMower lawnMower;

    private Location zombieLocation;
    private Location lawnmowerLocation;
    private Location plantLocation;

    private List<Zombie> zombies;
    private List<Plant> plants;

    private static final int[] locationX = {75, 200, 325, 450, 585, 720, 845, 975, 1100};
    private boolean zombieInRow = false, gameOver = false;
    private int row;

    /**
     * This constractor initialize the valueble
     *
     * @param sky    Sky
     * @param screen Game screen
     * @param row    Row number
     */
    public Row(Sky sky, Rectangle[][] screen, int row) {
        // Init valuible
        setSky(sky);
        setRow(row);
        setScreen(screen);
        setZombies(new ArrayList<>());
        setPlants(new ArrayList<>());

        // Initalize zombies location
        setZombieLocation(new Location());
        getZombieLocation().x = 1280; // End of Jframe
        // Initalize lawn mower location
        setLawnmowerLocation(new Location());
        getLawnmowerLocation().x = -50; // Start of Jframe
        // Initalize plants location
        setPlantLocation(new Location());

        switch (row) {
            case 0:
                getZombieLocation().y = 70;
                getLawnmowerLocation().y = 90;
                getPlantLocation().y = 90;
                break;
            case 1:
                getZombieLocation().y = 180;
                getLawnmowerLocation().y = 200;
                getPlantLocation().y = 200;
                break;
            case 2:
                getZombieLocation().y = 300;
                getLawnmowerLocation().y = 310;
                getPlantLocation().y = 320;
                break;
            case 3:
                getZombieLocation().y = 410;
                getLawnmowerLocation().y = 420;
                getPlantLocation().y = 430;
                break;
            case 4:
                getZombieLocation().y = 530;
                getLawnmowerLocation().y = 530;
                getPlantLocation().y = 550;
        }

        Location location = new Location(getLawnmowerLocation().x, getLawnmowerLocation().y);
        setLawnMower(new LawnMower(location));
    }

    /**
     * Get screen
     *
     * @return Rectangle[][]
     */
    public Rectangle[][] getScreen() {
        return screen;
    }

    /**
     * Set screen
     *
     * @param screen Rectangle[][]
     */
    public void setScreen(Rectangle[][] screen) {
        this.screen = screen;
    }

    /**
     * Get zombieFirstLocation
     *
     * @return Location
     */
    public Location getZombieLocation() {
        return zombieLocation;
    }

    /**
     * Set zombieFirstLocation
     *
     * @param zombieLocation Location
     */
    public void setZombieLocation(Location zombieLocation) {
        this.zombieLocation = zombieLocation;
    }

    /**
     * Get lawnmowerLocation
     *
     * @return Location
     */
    public Location getLawnmowerLocation() {
        return lawnmowerLocation;
    }

    /**
     * Set lawnmowerLocation
     *
     * @param lawnmowerLocation Location
     */
    public void setLawnmowerLocation(Location lawnmowerLocation) {
        this.lawnmowerLocation = lawnmowerLocation;
    }

    /**
     * Get plantLocation
     *
     * @return Location
     */
    public Location getPlantLocation() {
        return plantLocation;
    }

    /**
     * Set plantLocation
     *
     * @param plantLocation Location
     */
    public void setPlantLocation(Location plantLocation) {
        this.plantLocation = plantLocation;
    }

    /**
     * Get sky
     *
     * @return Sky
     */
    public Sky getSky() {
        return sky;
    }

    /**
     * Set sky
     *
     * @param sky Sky
     */
    public void setSky(Sky sky) {
        this.sky = sky;
    }

    /**
     * Get zombies
     *
     * @return List of Zombie
     */
    public List<Zombie> getZombies() {
        return zombies;
    }

    /**
     * Set zombies
     *
     * @param zombies List
     */
    public void setZombies(List<Zombie> zombies) {
        this.zombies = zombies;
    }

    /**
     * Get plants
     *
     * @return List of Plant
     */
    public List<Plant> getPlants() {
        return plants;
    }

    /**
     * Set plants
     *
     * @param plants List
     */
    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    /**
     * Get row
     *
     * @return row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Set row
     *
     * @param row row number
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Is game over
     *
     * @return boolean
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Set gameOver
     *
     * @param gameOver boolean
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Get first zombie
     *
     * @return Zombie
     */
    public Zombie getFirstZombie() {
        return firstZombie;
    }

    /**
     * Get lawnMower
     *
     * @return LawnMower
     */
    public LawnMower getLawnMower() {
        return lawnMower;
    }

    /**
     * Set lawnMower
     *
     * @param lawnMower LawnMower
     */
    public void setLawnMower(LawnMower lawnMower) {
        this.lawnMower = lawnMower;
    }

    /**
     * Get zombie in row
     *
     * @return boolean
     */
    public boolean getZombieInRow() {
        return zombieInRow;
    }

    /**
     * Get Location x
     *
     * @return location x
     */
    public static int getLocationX(int index) {
        return locationX[index];
    }

    /**
     * Making zombies with type and mode
     *
     * @param zombieType ZombieType
     * @param gameMode   Gmae mode
     */
    public void makeZombie(ZombieType zombieType, GameMode gameMode) {
        Location location = new Location(getZombieLocation().x, getZombieLocation().y);

        switch (zombieType) {
            case SimpleZombie:
                zombies.add(new NormalZombie(location));
                break;
            case BucketHeadZombie:
                zombies.add(new BucketHeadZombie(location, gameMode));
                break;
            case ConeHeadZombie:
                zombies.add(new ConeHeadZombie(location, gameMode));
        }
    }

    /**
     * Making plants with type and mode
     *
     * @param itemType PlantType
     * @param gameMode Gmae mode
     * @param x        Location x
     * @return isMakePlant
     */
    public boolean makePlant(ItemType itemType, GameMode gameMode, int x) {
        boolean isMakePlant = false;
        getPlantLocation().x = x;
        Location location = new Location(getPlantLocation().x, getPlantLocation().y);

        if (isEmptyHouse(x)) {
            switch (itemType) {
                case SunFlower:
                    if (sky.getStoredSun() >= Sunflower.REQUIRDE_SUN) {
                        isMakePlant = true;
                        sky.updateStoredSun(Sunflower.REQUIRDE_SUN);
                        plants.add(new Sunflower(gameMode, location));
                    }
                    break;
                case PeaShooter:
                    if (sky.getStoredSun() >= Peashooter.REQUIRDE_SUN) {
                        isMakePlant = true;
                        sky.updateStoredSun(Peashooter.REQUIRDE_SUN);
                        plants.add(new Peashooter(location));
                    }
                    break;
                case Wallnut:
                    if (sky.getStoredSun() >= Wallnut.REQUIRDE_SUN) {
                        isMakePlant = true;
                        sky.updateStoredSun(Wallnut.REQUIRDE_SUN);
                        plants.add(new Wallnut(location));
                    }
                    break;
                case SnowPea:
                    if (sky.getStoredSun() >= SnowPea.REQUIRDE_SUN) {
                        isMakePlant = true;
                        sky.updateStoredSun(SnowPea.REQUIRDE_SUN);
                        plants.add(new SnowPea(location));
                    }
                    break;
                case CherryBomb:
                    if (sky.getStoredSun() >= CherryBomb.REQUIRDE_SUN) {
                        isMakePlant = true;
                        sky.updateStoredSun(CherryBomb.REQUIRDE_SUN);
                        Location rectangleLocation = new Location(row, Arrays.binarySearch(locationX, x));
                        plants.add(new CherryBomb(location, rectangleLocation, screen));
                    }
            }
        } else { // Remove planet with shovel
            if (itemType == ItemType.Shovel) {
                for (Plant plant : plants) {
                    if (plant.getLocation().x == x) {
                        plants.remove(plant);
                        break;
                    }
                }
            }
        }
        return isMakePlant;
    }

    /**
     * Check this house is empty
     *
     * @param x Location x
     * @return boolean
     */
    private boolean isEmptyHouse(int x) {
        for (Plant plant : plants) {
            if (plant.getLocation().x == x) {
                return false;
            }
        }
        return true;
    }

    /**
     * Making preview item with type
     *
     * @param itemType PlantType
     * @param x        Location x
     */
    public Item makePreviewItem(ItemType itemType, int x) {
        Item item = null;
        getPlantLocation().x = x;
        Location location = new Location(getPlantLocation().x, getPlantLocation().y);

        if (isEmptyHouse(x)) {
            switch (itemType) {
                case SunFlower:
                    item = new Sunflower(GameMode.Normal, location);
                    break;
                case PeaShooter:
                    item = new Peashooter(location);
                    break;
                case Wallnut:
                    item = new Wallnut(location);
                    break;
                case SnowPea:
                    item = new SnowPea(location);
                    break;
                case CherryBomb:
                    Location rectangleLocation = new Location(row, Arrays.binarySearch(locationX, x));
                    item = new CherryBomb(location, rectangleLocation, screen);
            }
        } else if (itemType == ItemType.Shovel) {
            item = new Shovel(location);
        }
        return item;
    }

    public void update(Row[] rows) {
        // Checking if any zombie walks into row
        zombieInRow = zombies.size() != 0;
        // Update lawnMower
        lawnMower.update(this);
        // Update zombies
        for (int i = 0; i < zombies.size(); i++) zombies.get(i).update(this);
        // Update plants
        for (int i = 0; i < plants.size(); i++) {
            Plant plant = plants.get(i);
            if (plant instanceof CherryBomb) {
                CherryBomb cherryBomb = ((CherryBomb) plant);
                // Performs special update for cheryPlanet
                cherryBomb.update(rows);
                // Checks if cheryPlanet is exploded then removes it
                if (cherryBomb.isExploded()) {
                    plants.remove(cherryBomb);
                }
            } else {
                plant.update(this);
            }
        }
        // Find the closest zombie
        closestZombie();
    }

    /**
     * Finds which zombie is the first zombie and gets the shots
     */
    private void closestZombie() {
        int locationX = 1280;
        for (Zombie zombie : zombies) {
            if (zombie.getLocation().x < locationX/* && !zombie.zombieIsBurned*/) {
                locationX = zombie.getLocation().x;
                firstZombie = zombie;
            }
        }
    }

    /**
     * Check zombies are dead in row
     *
     * @return boolean
     */
    public boolean isZombiesDead() {
        return zombies.isEmpty();
    }
}