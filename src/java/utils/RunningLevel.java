package utils;

import views.GameState;

import java.security.SecureRandom;

public class RunningLevel {
    private final SecureRandom random;
    private GameState gameState;
    private GameMode gameMode;

    private final long startGameTime;
    private long startRoundTime;
    private int counterOfZombie = 0;
    private boolean roundOne = true, roundTwo = false;

    public RunningLevel(GameState gameState, GameMode gameMode) {
        setGameState(gameState);
        setGameMode(gameMode);
        getGameState().setupItemsInBar();
        getGameState().getSky().setStoredSun(100);

        random = new SecureRandom();
        startGameTime = System.currentTimeMillis();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void update() {
        if ((System.currentTimeMillis() - startGameTime) > 50 * 1000) {
            if (roundOne) {
                roundOne();
            } else if (roundTwo) {
                roundTwo();
            } else {
                roundThree();
            }
        }
    }

    private void roundOne() {
        if ((System.currentTimeMillis() - startRoundTime) > 30 * 1000) {
            getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.SimpleZombie, getGameMode());
            counterOfZombie++;
            startRoundTime = System.currentTimeMillis();
        }
        // after 2.5 minute 5 zombies created and round two begin
        if (counterOfZombie == 5) {
            counterOfZombie = 0;
            roundOne = false;
            roundTwo = true;
        }
    }

    private void roundTwo() {
        if ((System.currentTimeMillis() - startRoundTime) > 30 * 1000) {
            // Simple zmobie stable
            getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.SimpleZombie, getGameMode());
            // Random zombie (ConeHeadZombie or SimpleZombie)
            int zombieType = random.nextInt(2);
            if (zombieType == 0) {
                getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.SimpleZombie, getGameMode());
            } else {
                getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.ConeHeadZombie, getGameMode());
            }

            counterOfZombie += 2;
            startRoundTime = System.currentTimeMillis();
        }
        // after 3 minute 12 zombies created and round three begin
        if (counterOfZombie == 12) {
            counterOfZombie = 0;
            roundOne = false;
            roundTwo = false;
        }
    }

    private void roundThree() {
        if ((System.currentTimeMillis() - startRoundTime) > 25 * 1000) {
            // Simple zmobie stable
            getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.SimpleZombie, getGameMode());
            // Random zombie (BucketHeadZombie, ConeHeadZombie or SimpleZombie)
            int zombieType = random.nextInt(2);
            if (zombieType == 0) {
                getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.ConeHeadZombie, getGameMode());
            } else {
                getGameState().getRows()[random.nextInt(5)].makeZombie(ZombieType.BucketHeadZombie, getGameMode());
            }

            counterOfZombie += 2;
            startRoundTime = System.currentTimeMillis();
        }
        // after 3 minute 12 zombies created and level end
        if (counterOfZombie == 12) {
            if (getGameState().isZombiesDead()) {
                getGameState().setState(GameState.State.Win);
            }
        }
    }
}