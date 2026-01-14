/*
* Date: Jan 13, 2026
* Name: SoundPlayer
* Description: 
* Author: RohanSomani
*/

package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import config.Setup;

public class SoundPlayer {
	public static void playSound(SoundType soundType) {
		switch (soundType) {
		case PLAYER_WALK:
			SoundPlayer.handlePlayerWalk();
			break;
		default:
			SoundPlayer.playSound("resources/sounds/" + soundType + ".wav");
		}
	}
	
	public static void playSound(String path){
		File sound = new File(path); 
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			Setup.handleError(e);
			return;
		} catch (IOException e){
			Setup.handleError(e);
			return;
		} catch (LineUnavailableException e){
			Setup.handleError(e);
			return;
		}
	}
	
	public static void handlePlayerWalk(){
		Random r = new Random();
		int choice = r.nextInt(8) + 1;
		String path = "resources/sounds/walk_sounds/walk" + choice + ".wav";
		SoundPlayer.playSound(path);
	}

}
