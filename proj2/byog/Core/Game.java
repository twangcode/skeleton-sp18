package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.RandomUtils;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    private static List<Room> addRooms(TETile[][] world, Random r){
        List<Room> rooms = new ArrayList<Room>();
        int numOfRooms = RandomUtils.uniform(r, 10,20);
        for (int i=0; i<numOfRooms; i+=1){
            Room room = Room.addRandomRoom(r, WIDTH, HEIGHT);
            room.draw(world);
            rooms.add(room);
        }
        return rooms;
    }

    private static void addHallways(TETile[][] world, List<Room> rooms){
        for (int i=0; i<rooms.size()-1; i+=1){
            rooms.get(i).connect(world, rooms.get(i+1));
        }
    }

    private static void addWall(TETile[][] world, int positionX, int positionY){
        for (int i=Math.max(0, positionX-1); i<positionX+2; i+=1){
            for (int j=Math.max(0, positionY-1); j<positionY+2; j+=1 ){
                if(world[i][j] == Tileset.NOTHING){
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    private static void buildWalls(TETile[][] world){
        int width = world.length;
        int height = world[0].length;

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    addWall(world, x, y);
                }
            }
        }
    }

    private static void drawBackground(TETile[][] world){
        int width = world.length;
        int height = world[0].length;

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static void drawWorld(TETile[][] world, long seed) {
        int width = world.length;
        int height = world[0].length;

        Random r = new Random(seed);
        drawBackground(world);
        List<Room> rooms = addRooms(world, r);
        addHallways(world, rooms);
        buildWalls(world);
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
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        drawWorld(finalWorldFrame, 1234);

        ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;
    }
}
