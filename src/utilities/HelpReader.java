package utilities;

import config.Setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A helper method to read the help message from resources
 *
 * @author RohanSomani
 * @date Jan 14, 2026
 * @name HelpReader
 */
public class HelpReader {
  /**
   * read the help from the help file.
   *
   * @param helpPath the url of the help file.
   * @return the html help file.
   */
  public static String readHelp(URL helpPath) {
    StringBuilder out = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(helpPath.openStream()))) { // what? IDK either.
      // here.
      // https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
      String line = br.readLine();

      while (line != null) {
        out.append(line);
        line = br.readLine();
      }
    } catch (IOException e) {
      Setup.handleError(e);
    }

    return out.toString();
  }

}
