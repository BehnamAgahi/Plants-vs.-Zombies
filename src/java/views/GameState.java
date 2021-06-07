package views;

import model.Shovel;
import model.Sun;
import model.item.Item;
import model.plants.*;
import utils.GameMode;
import utils.ItemType;
import utils.Row;
import utils.Sky;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author AroutinBehnam
 * @version 2.0.0 1/27/2021
 */
public class GameState {
    private final Rectangle[][] screen;
    private Row[] rows;
    private Sky sky;
    private Item selectedItem;
    private Item previewItem;
    private ArrayList<Item> itemsInBar;

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    private State state;
    private State selectState;

    public int locX, locY, diam;
    private boolean mousePress, itemPress = false;
    private int mouseX, mouseY;

    public GameState() {
        locX = 100;
        locY = 100;
        diam = 32;
        sky = new Sky(GameMode.Normal);
        rows = new Row[5];
        screen = new Rectangle[5][9];
        setupScreen();
        setupRows();

        state = State.StartMenu;
        selectState = State.StartMenu;

        // Initialize mouse values
        mousePress = false;
        mouseX = 0;
        mouseY = 0;

        // Initialize handler
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    /**
     * Initizlize rows
     */
    private void setupRows() {
        for (int index = 0; index < 5; index++) {
            rows[index] = new Row(sky, screen, index);
        }
    }

    /**
     * Making rectangles of each house on the ground
     */
    private void setupScreen() {
        int locationX = 50;
        int locationY = 105;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                screen[i][j] = new Rectangle(locationX, locationY, 115, 115);
                locationX += 115;
            }
            locationX = 50;
            locationY += 115;
        }
    }

    public void setupItemsInBar() {
        itemsInBar = new ArrayList<>();
        itemsInBar.add(new Sunflower());
        itemsInBar.add(new Peashooter());
        itemsInBar.add(new Wallnut());
        itemsInBar.add(new SnowPea(GameMode.Normal));
        itemsInBar.add(new CherryBomb(GameMode.Normal));
        itemsInBar.add(new Shovel());
    }

    public KeyListener getKeyListener() {
        return keyHandler;
    }

    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public State getSelectState() {
        return selectState;
    }

    public String getStoredSun() {
        return String.valueOf(sky.getStoredSun());
    }

    public ArrayList<Item> getItemsInBar() {
        return itemsInBar;
    }

    public Sky getSky() {
        return sky;
    }

    public List<Sun> getSkySuns() {
        return sky.getSuns();
    }

    public Row[] getRows() {
        return rows;
    }

    public Item getPreviewItem() {
        return previewItem;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        if (state == State.NewGame) {
            for (int i = 0; i < rows.length; i++) {
                rows[i].update(rows);
                if (rows[i].isGameOver()){
                    setState(State.GameOver);
                }
            }
            sky.update();
        }
    }

    /**
     * Check all zombies are dead
     *
     * @return boolean
     */
    public boolean isZombiesDead() {
        boolean isZombiesDead = true;
        for (int i = 0; i < rows.length; i++) {
            if (!rows[i].isZombiesDead()) {
                isZombiesDead = false;
                break;
            }
        }
        return isZombiesDead;
    }

    /**
     * The method refresh rows
     */
    public void refreshRows() {
        rows = new Row[5];
        sky = new Sky(GameMode.Normal);
        for (int index = 0; index < 5; index++) {
            rows[index] = new Row(sky, screen, index);
        }
    }

    /**
     * Check sun is selected
     *
     * @param mouseRect Rectangle
     */
    private void updateStoredSun(Rectangle mouseRect) {
        if (state == State.NewGame) {
            sky.updateStoredSun(mouseRect);
        }
    }

    /**
     * Changes the planet image card if its clicked or released
     *
     * @param mouseRect  Rectangle
     * @param mousePress Boolean
     */
    private void updateCardImage(Rectangle mouseRect, boolean mousePress) {
        if (state == State.NewGame) {
            for (Item item : itemsInBar) {
                if (item != null) {
                    // updating planet's item rectangle
                    if (item instanceof Plant) {
                        Plant plant = ((Plant) item);
                        plant.setupCardRectangle();
                        if (plant.getRectangle().intersects(mouseRect) && !plant.isLocked()) {
                            plant.itemSelected(mousePress);
                            itemPress = mousePress;
                            selectedItem = plant;
                        }
                    } else if (item instanceof Shovel) {
                        Shovel shovel = ((Shovel) item);
                        shovel.setupRectangle();
                        if (shovel.getRectangle().intersects(mouseRect)) {
                            shovel.itemSelected(mousePress);
                            itemPress = mousePress;
                            selectedItem = shovel;
                        }
                    }
                }
            }
        }
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // Press Esc button from key board
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (state == State.StartMenu) { // app finished when In start menu
                    state = State.QuitGame;
                } else if (state == State.NewGame) { // resume menu show when In game
                    selectState = State.ResumeMenu;
                    state = State.ResumeMenu;
                } else if (state == State.ResumeMenu) { // It disappears when the resume menu is open
                    state = State.NewGame;
                }
            }
        }
    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            updateMouseLocation(event);

            if (state == State.StartMenu) { // Select item from start menu
                if (mouseX >= 662 && mouseX <= 1143 && mouseY >= 122 && mouseY <= 237) {
                    sky.updateStartTime();
                    state = State.NewGame;
                } else if (mouseX >= 658 && mouseX <= 1114 && mouseY >= 218 && mouseY <= 356) {
                    state = State.LoadGame;
                } else if (mouseX >= 663 && mouseX <= 1079 && mouseY >= 314 && mouseY <= 446) {
                    state = State.Ranking;
                } else if (mouseX >= 918 && mouseX <= 1018 && mouseY >= 582 && mouseY <= 626) {
                    state = State.Setting;
                } else if (mouseX >= 1134 && mouseX <= 1239 && mouseY >= 596 && mouseY <= 646) {
                    state = State.QuitGame;
                }
            } else if (state == State.NewGame) {
                if (mouseX >= 1094 && mouseX <= 1270 && mouseY >= 0 && mouseY <= 30) {
                    // Clear select state and show popup menu
                    selectState = State.ResumeMenu;
                    state = State.ResumeMenu;
                }
            } else if (state == State.ResumeMenu) { // Select items from popup menu
                if (mouseX >= 474 && mouseX <= 788 && mouseY >= 256 && mouseY <= 300) {
                    state = State.SaveGame;
                } else if (mouseX >= 473 && mouseX <= 789 && mouseY >= 332 && mouseY <= 374) {
                    // Clear select state and go to start menu
                    selectState = State.StartMenu;
                    state = State.StartMenu;
                } else if (mouseX >= 386 && mouseX <= 891 && mouseY >= 540 && mouseY <= 612) {
                    state = State.NewGame;
                }
            }

            mousePress = true;
            Rectangle mouseRect = new Rectangle(mouseX, mouseY, 1, 1);
            updateStoredSun(mouseRect);
            updateCardImage(mouseRect, mousePress);
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            Rectangle mouseRect = new Rectangle(mouseX, mouseY, 1, 1);

            if (state == State.NewGame) {
                // When card released back to first location
                if (selectedItem != null) {
                    if (selectedItem instanceof Plant) {
                        ((Plant) selectedItem).itemSelected(false);
                    } else if (selectedItem instanceof Shovel) {
                        ((Shovel) selectedItem).itemSelected(false);
                    }
                }

                // Add plant to ground
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (screen[i][j].intersects(mouseRect) && itemPress) {
                            boolean isMakePlant = rows[i].makePlant(ItemType.getItemType(selectedItem), GameMode.Normal, Row.getLocationX(j));
                            // When card use then locked
                            if (selectedItem instanceof Plant && isMakePlant) {
                                ((Plant) selectedItem).startRechargeTime();
                            }
                        }
                    }
                }
            }

            mousePress = false;
            itemPress = false;
            previewItem = null;
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            updateMouseLocation(event);
            Rectangle mouseRect = new Rectangle(mouseX, mouseY, 1, 1);

            if (state == State.NewGame) {
                // Update location of card
                if (itemPress) {
                    selectedItem.getLocation().x = mouseX - 50;
                    selectedItem.getLocation().y = mouseY - 50;
                }
                // Add preview to ground
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (screen[i][j].intersects(mouseRect) && itemPress) {
                            previewItem = rows[i].makePreviewItem(ItemType.getItemType(selectedItem), Row.getLocationX(j));
                        }
                    }
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            updateMouseLocation(event);

            if (state == State.StartMenu) {
                // If you hold the mouse over the items in start menu then change color text
                if (mouseX >= 662 && mouseX <= 1143 && mouseY >= 122 && mouseY <= 237) {
                    selectState = State.SelectNewGame;
                } else if (mouseX >= 658 && mouseX <= 1114 && mouseY >= 218 && mouseY <= 356) {
                    selectState = State.SelectLoadGame;
                } else if (mouseX >= 663 && mouseX <= 1079 && mouseY >= 314 && mouseY <= 446) {
                    selectState = State.SelectRanking;
                } else if (mouseX >= 918 && mouseX <= 1018 && mouseY >= 582 && mouseY <= 626) {
                    selectState = State.SelectSetting;
                } else if (mouseX >= 1134 && mouseX <= 1239 && mouseY >= 596 && mouseY <= 646) {
                    selectState = State.SelectQuitGame;
                } else {
                    selectState = State.StartMenu;
                }
            } else if (state == State.NewGame) {
                if (mouseX >= 1094 && mouseX <= 1270 && mouseY >= 0 && mouseY <= 30) {
                    selectState = State.SelectResumeMenu;
                } else {
                    selectState = State.NewGame;
                }
            } else if (state == State.ResumeMenu) {
                // If you hold the mouse over the items in popup menu then change color text
                if (mouseX >= 474 && mouseX <= 788 && mouseY >= 256 && mouseY <= 300) {
                    selectState = State.SelectSaveGame;
                } else if (mouseX >= 473 && mouseX <= 789 && mouseY >= 332 && mouseY <= 374) {
                    selectState = State.SelectExitToMainMenu;
                } else if (mouseX >= 386 && mouseX <= 891 && mouseY >= 540 && mouseY <= 612) {
                    selectState = State.SelectResumeGame;
                } else {
                    selectState = State.ResumeMenu;
                }
            }
        }

        /**
         * Update locate of mouse
         */
        private void updateMouseLocation(MouseEvent event) {
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }

    /**
     * State class handle all of possible state
     */
    public enum State {
        GameOver, Win,
        // Start menu state
        StartMenu, NewGame, LoadGame, QuitGame, Ranking, Setting,
        SelectNewGame, SelectLoadGame, SelectQuitGame, SelectRanking, SelectSetting,
        // Popup menu state
        ResumeMenu, SelectResumeMenu, SaveGame, SelectSaveGame, SelectExitToMainMenu, SelectResumeGame
    }
}