package utils;

import model.item.Item;
import model.plants.*;

public enum ItemType {
    CherryBomb, PeaShooter, SnowPea, SunFlower, Wallnut, Shovel;

    public static ItemType getItemType(Item item) {
        if (item instanceof Sunflower) {
            return SunFlower;
        } else if (item instanceof Peashooter) {
            return PeaShooter;
        } else if (item instanceof Wallnut) {
            return Wallnut;
        } else if (item instanceof SnowPea) {
            return SnowPea;
        } else if (item instanceof CherryBomb) {
            return CherryBomb;
        } else {
            return Shovel;
        }
    }
}