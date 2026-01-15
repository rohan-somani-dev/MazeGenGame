/*
* Date: Jan 14, 2026
* Name: HelpReader
* Description: 
* Author: RohanSomani
*/

package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import config.Setup;

public class HelpReader {
	public static String readHelp(){
		StringBuilder out = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(Setup.HELP_FILE))) {
			
			String line = br.readLine(); 
			
			while (line != null){
				out.append(line);
				line = br.readLine();
			}
		} catch (IOException e){
			Setup.handleError(e);
		}
		
		return out.toString();
	}
	
}
