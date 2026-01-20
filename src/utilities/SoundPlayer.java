package utilities;

import config.Setup;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * A class that preloads sounds to be played at will, SoundType.BLANK should be
 * played on application startup to minimize lag. Don't ask me why this works.
 *
 * @author RohanSomani
 * @date Jan 13, 2026
 * @name SoundPlayer
 */
public class SoundPlayer {

  private static final HashMap<SoundType, Clip> clips = new HashMap<>();
  private static final Random r = new Random();
  private static final Clip[] walkClips = new Clip[8];

  /**
   * load the sounds to be played in the static clips map.
   */
  public static void LoadSounds() {

    String path;
    for (SoundType soundType : SoundType.values()) {
      try {
        if (soundType == SoundType.PLAYER_WALK)
          continue;

        path = "/sounds/" + soundType + ".wav";
        URL soundFile = SoundPlayer.class.getResource(path);

        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);

        clip.start();
        clip.stop();
        clip.setFramePosition(0); // prime clip for playing

        clips.put(soundType, clip);

      } catch (Exception e) { // usually hate writing this but way too many errors to care about.
        Setup.handleError(e);
      }
    }

    for (int i = 1; i <= 8; i++) {
      try {
        path = "/sounds/walk_sounds/walk" + i + ".wav";
        URL soundFile = SoundPlayer.class.getResource(path);
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

  /**
   * play a sound given a sound type, if the soundType is PLAYER_WALK then
   * randomly pick from 8 player walks.
   *
   * @param soundType the sound type to be used.
   */
  public static void playSound(SoundType soundType) {
    try {
      if (soundType == SoundType.PLAYER_WALK) {
        int choice = r.nextInt(8);
        Clip clip = walkClips[choice];
        if (clip.isRunning())
          clip.stop();
        clip.setFramePosition(0);
        clip.start();
      } else {
        Clip clip = clips.get(soundType);
        if (clip != null) {
          if (clip.isRunning())
            clip.stop();
          clip.setFramePosition(0);
          clip.start();
        }
      }
    } catch (Exception e) {
      Setup.handleError(e);
    }
  }
}
