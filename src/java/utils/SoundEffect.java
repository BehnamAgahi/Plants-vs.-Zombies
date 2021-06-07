package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * <h1>Sound Effect</h1>
 * This class manage sound effect
 *
 * @author AroutinBehnam
 * @version 1.0.0 1/22/2021
 */
public class SoundEffect {
    private static final String SOUND_ADDRESS = "/resource/sounds/";
    private static SoundEffect soundEffectInstance = null;

    private SoundEffect() {
    }

    public static SoundEffect getInstance() {
        if (soundEffectInstance == null) {
            soundEffectInstance = new SoundEffect();
        }
        return soundEffectInstance;
    }

    public static synchronized void playSound(String path) {
        String correctPath = getPath(path);
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundEffect.class.getResourceAsStream(correctPath));
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    public static Clip clipForLoopFactory(String path) {
        String correctPath = getPath(path);

        Clip clip = null;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundEffect.class.getResourceAsStream(correctPath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return clip;
    }

    public static void stopLoopingSounds(Clip... clips) {
        for (Clip clip : clips) clip.stop();
    }

    private static String getPath(String path) {
        String correctPath;
        if (path.contains(SOUND_ADDRESS)) {
            correctPath = path;
        } else {
            correctPath = SOUND_ADDRESS.concat(path);
        }
        return correctPath;
    }
}