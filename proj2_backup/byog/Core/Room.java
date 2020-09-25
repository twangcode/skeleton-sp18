package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

//import byog.Core.RandomUtils;
//import org.junit.Test;

import java.util.Random;

public class Room {
    private int width;
    private int height;
    private int positionX;
    private int positionY;
    private TETile shape;
    
    public Room(int width, int height, int positionX, int positionY, TETile shape) {
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shape = shape;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public static Room addRandomRoom(Random random, int worldWidth, int worldHeight) {
        int width = RandomUtils.uniform(random, 2, worldWidth / 4);
        int height = RandomUtils.uniform(random, 2, worldHeight / 4);
        int positionX = RandomUtils.uniform(random, worldWidth - width);
        int positionY = RandomUtils.uniform(random, worldHeight - height);
        
        Room room = new Room(width, height, positionX, positionY, Tileset.FLOOR);
        return room;
    }

    private void drawHallway(int positionX1, int positionY1,
                             int positionX2, int positionY2,
                             TETile[][] world) {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        if (positionX1 > positionX2) {
            x1 = positionX2;
            x2 = positionX1;
        } else {
            x1 = positionX1;
            x2 = positionX2;
        }
        if (positionY1 > positionY2) {
            y1 = positionY2;
            y2 = positionY1;
        } else {
            y1 = positionY1;
            y2 = positionY2;
        }
        for (int x = x1; x <= x2; x += 1) {
            world[x][y1] = this.shape;
        }
        for (int y = y1; y <= y2; y += 1) {
            world[x2][y] = shape;
        }
    }

    public void connect(TETile[][] world, Room room) {
        int positionX2 = room.getPositionX();
        int positionY2 = room.getPositionY();
        drawHallway(this.positionX, this.positionY, positionX2, positionY2, world);
    }
    
    public void draw(TETile[][] world) {
        for (int x = positionX; x < positionX + width; x += 1) {
            for (int y = positionY; y < positionY + height; y += 1) {
                world[x][y] = shape;
            }
        }
    }
}
