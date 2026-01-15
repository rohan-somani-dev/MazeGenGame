/*
* Date: Jan 14, 2026
* Name: FontLoader
* Description: 
* Author: RohanSomani
*/

package utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import config.Setup;

public class FontLoader {
	public static void loadFonts(){
		File fontsDir = new File(Setup.FONTS_PATH);
		if (!fontsDir.isDirectory() || fontsDir == null) Setup.handleError(new FileNotFoundException("fonts is not a directory"));
		
		for (File f : fontsDir.listFiles()){
			if (f.getName().endsWith(".ttf")) {
				loadFont(f); 		
			}
		}
	}
	
	public static void loadFont(File f) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (IOException | FontFormatException e){
			Setup.handleError(e);
			return;
		}
		
	}
	

}
