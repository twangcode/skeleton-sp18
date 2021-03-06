package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;

import java.util.*;

public class WorldGenerator {
    // Constants:
    private static final int MAXROOMWIDTH = 15;
    private static final int MINROOMWIDTH = 5;
    private static final int MAXROOMHEIGHT = 15;
    private static final int MINROOMHEIGHT = 5;
    private static final int MINROOMNUM = 6;
    private static final int MAXROOMNUM = 7;

    // Private fields:
    private Size size;
    private TETile[][] world;
    private Random random;
    private Set<Position> floors = new HashSet<>();
    private Position playerPosition;

    // Constructor:
    WorldGenerator(Size size, long seed) {
        this.size = size;
        random = new Random(seed);
    }

    WorldGenerator(int w, int h, long seed) {
        this.size = new Size(w, h);
        random = new Random(seed);
    }

    WorldGenerator(int w, int h, Random random) {
        this.size = new Size(w, h);
        this.random = random;
    }

    // Shared class
    protected static class Position {
        private int xCoordinate;
        private int yCoordinate;

        /** Constructor */
        Position(int x, int y) {
            xCoordinate = x;
            yCoordinate = y;
        }

        public int getxCoordinate() {
            return xCoordinate;
        }

        public int getyCoordinate() {
            return yCoordinate;
        }

        public void setxCoordinate(int x) {
            xCoordinate = x;
        }

        public void setyCoordinate(int y) {
            yCoordinate = y;
        }
    }

    // Private helper class:
    private class Size {
        private int width;
        private int height;

        /** Constructor */
        Size(int w, int h) {
            width = w;
            height = h;
        }
    }

    private class Room {
        private Position pos;
        private Size size;
        /** Constructor */
        Room(Position newPos, Size newSize) {
            pos = newPos;
            size = newSize;
        }
    }

    // Private methods:
    /** Return world, set everything to Tileset.NOTHING. */
    private TETile[][] initialize() {
        int width = size.width;
        int height = size.height;
        world = new TETile[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                world[w][h] = Tileset.NOTHING;
            }
        }
        return world;
    }

    private void drawRoom(Room room) {
        int x = room.pos.xCoordinate;
        int y = room.pos.yCoordinate;
        int width = room.size.width;
        int height = room.size.height;
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                world[i][j] = Tileset.FLOOR;
                Position floor = new Position(i, j);
                floors.add(floor);
            }
        }
    }

    /** Return a random room */
    private Room addRandomRoom(Random random) {
        int width = RandomUtils.uniform(random, MINROOMWIDTH, MAXROOMWIDTH);
        int height = RandomUtils.uniform(random, MINROOMHEIGHT, MAXROOMHEIGHT);
        int xCoordinate = RandomUtils.uniform(random, 0, size.width - width);
        int yCoordinate = RandomUtils.uniform(random, 0, size.height - height);
        Room newRoom = new Room(new Position(xCoordinate, yCoordinate), new Size(width, height));
        drawRoom(newRoom);
        return newRoom;
    }

    /** Generate a List of Rooms */
    private List<Room> addRooms() {
        List<Room> rooms = new ArrayList<>();
        int numRooms = RandomUtils.uniform(random, MINROOMNUM, MAXROOMNUM);
        int playerRoomNum = RandomUtils.uniform(random, 0, numRooms);
        for (int i = 0; i < numRooms; i++) {
            rooms.add(addRandomRoom(random));
            if (i == playerRoomNum) {
                setPlayerInitialPosition(rooms, playerRoomNum);
            }
        }
        return rooms;
    }

    /** Connect two rooms */
    private void connect(Room r1, Room r2) {
        int xMin = Math.min(r1.pos.xCoordinate, r2.pos.xCoordinate);
        int xMax = Math.max(r1.pos.xCoordinate, r2.pos.xCoordinate);
        int yMin = Math.min(r1.pos.yCoordinate, r2.pos.yCoordinate);
        int yMax = Math.max(r1.pos.yCoordinate, r2.pos.yCoordinate);

        for (int x = xMin; x <= xMax; x++) {
            world[x][yMin] = Tileset.FLOOR;
            floors.add(new Position(x, yMin));
        }
        for (int y = yMin; y <= yMax; y++) {
            world[xMax][y] = Tileset.FLOOR;
            floors.add(new Position(xMax, y));
        }
    }

    /** Connect all rooms */
    private void connectRooms(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                connect(rooms.get(i), rooms.get(j));
            }

        }
    }

    /** Add wall to a floor */
    private void addWall(int positionX, int positionY) {
        for (int i = Math.max(0, positionX - 1);
             i < Math.min(size.width, positionX + 2); i++) {
            for (int j = Math.max(0, positionY - 1);
                 j < Math.min(size.height, positionY + 2); j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /** Build walls for all rooms */
    private void buildWalls() {
        for (Position floor : floors) {
            addWall(floor.xCoordinate, floor.yCoordinate);
        }
    }

    private void setPlayerInitialPosition(List<Room> rooms, int playerRoomNum) {
        playerPosition = rooms.get(playerRoomNum).pos;
        world[playerPosition.xCoordinate][playerPosition.yCoordinate] = Tileset.PLAYER;
    }

    // Package-protected method:
    protected TETile[][] generate() {
        initialize();
        List<Room> rooms = addRooms();
        connectRooms(rooms);
        buildWalls();

        return world;
    }

    protected Position getPlayerPosition(){
        return playerPosition;
    }

    // Main method for test purposes:
    public static void main(String[] args) {
        int width = 80;
        int height = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        WorldGenerator wg = new WorldGenerator(width, height, 34563456);
        wg.generate();
        ter.renderFrame(wg.world);
    }
}
