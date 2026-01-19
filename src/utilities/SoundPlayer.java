/*
 * Date: Jan 13, 2026
 * Name: SoundPlayer
 * Description:
 * Author: RohanSomani
 */

package utilities;

import config.Setup;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;

public class SoundPlayer {

    private static final HashMap<SoundType, Clip> clips = new HashMap<>();
    private static final Random r = new Random();
    private static Clip[] walkClips = new Clip[8];

    public static void LoadSounds() {
        for (SoundType soundType : SoundType.values()) {
            try {
                String path = "resources/sounds/" + soundType + ".wav";
                File soundFile = new File(path);
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);

                clip.start();
                clip.stop();
                clip.setFramePosition(0); // prime clip for playin

                clips.put(soundType, clip);
            } catch (FileNotFoundException ignored) {
            } catch (Exception e) {
                Setup.handleError(e);
            }
        }

        for (int i = 1; i <= 8; i++) {
            try {
                String path = "resources/sounds/walk_sounds/walk" + i + ".wav";
                File soundFile = new File(path);
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);

                clip.start();
                clip.stop();
                clip.setFramePosition(0);

                walkClips[i - 1] = clip;
            } catch (Exception e) {
                Setup.handleError(e);
            }
        }
    }

    public static void playSound(SoundType soundType) {
        try {
            if (soundType == SoundType.PLAYER_WALK) {
                int choice = r.nextInt(8);
                Clip clip = walkClips[choice];
                if (clip.isRunning()) clip.stop();
                clip.setFramePosition(0);
                clip.start();
            } else {
                Clip clip = clips.get(soundType);
                if (clip != null) {
                    if (clip.isRunning()) clip.stop();
                    clip.setFramePosition(0);
                    clip.start();
                }
            }
        } catch (Exception e) {
            Setup.handleError(e);
        }
    }
}
