package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Draw background
     */
    private static void drawBackground(TETile[][] world){
        int width = world.length;
        int height = world[0].length;

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Draw one line.
     */
    private static void addLine(TETile[][] world, TETile shape, int length, int positionX, int positionY){
        for (int i=0; i<length; i+=1){
            world[positionX+i][positionY] = shape;
        }
    }

    /**
     * Draw one Hexagon of given length and position.
     */
    public static void addHexagon (TETile[][] world, TETile shape, int length, int positionX, int positionY) {
        if (positionX<length-1 || positionX+length<WIDTH || positionY<0 || positionY+2*length<HEIGHT) {
            return;
        }
        for (int i=0; i<length; i+=1){
            addLine(world, shape, length+i*2, positionX-i, positionY+i);
        }
        for (int i=length-1; i>=0; i-=1){
            addLine(world, shape, length+i*2, positionX-i, positionY+2*length-1-i);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        drawBackground(world);

        addHexagon(world, Tileset.WALL, 5, 0, 0);

        ter.renderFrame(world);
    }
}
