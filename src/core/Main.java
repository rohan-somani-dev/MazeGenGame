package core;
/** start the program
 * @author RohanSomani
 * @name Main
 * @date 2025-12-18
 */


public class Main {

    /** the main class to run the game;
     * @param unused not used; TODO: add optional file to read from.
     * @pre program exists
     * @post {@link GameController} is started
     */
    public static void main(String[] unused) {
        GameController gc = new GameController();
        gc.startMazeGen();
    }



}