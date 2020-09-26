package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {

    // Constants:
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    // private fields:
    private TETile[][] world;
    private TERenderer ter = new TERenderer();

    // private methods:
    private long processInput(String input) {
        int index = 1;
        while (index < input.length() && Character.isDigit(input.charAt(index))) {
            index++;

        }
        long seed = Long.parseLong(input.substring(1, index));
        return seed;
    }


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        ter.initialize(WIDTH, HEIGHT);
        long seed = processInput(input);
        WorldGenerator wg = new WorldGenerator(WIDTH, HEIGHT, seed);
        TETile[][] finalWorld = wg.generate();
        return finalWorld;
    }
    // Main method for testing purposes:
    public static void main(String[] args) {
        Game game = new Game();
        TETile[][] world = game.playWithInputString("n123sswwdasdassadwas");
        game.ter.renderFrame(world);
    }
}
