package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Game {

    // Constants:
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    private static final String SAVEFILE = "saved.txt";

    // private fields:
    // Game mode:
    private boolean setupMode = false;
    private boolean playMode;
    private boolean quitMode = false;

    private StringBuilder seedStr = new StringBuilder();
    private StringBuilder hudDisplay = new StringBuilder();
    private TETile[][] world;
    private TERenderer ter = new TERenderer();
    private Random random;
    private WorldGenerator.Position playerPosition;

    // private methods:
    private long processInput(String input) {
        int index = 1;
        while (index < input.length() && Character.isDigit(input.charAt(index))) {
            index++;
        }
        long seed = Long.parseLong(input.substring(1, index));
        return seed;
    }

    private void openWelcomeWindow() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.setFont(new Font("Monaco", Font.BOLD, 50));
        StdDraw.text(0.5, 0.8, "CS61B: THE GAME");

        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.45, "Load Game (L)");
        StdDraw.text(0.5, 0.4, "Quit (Q)");
        StdDraw.show();
    }

    private void drawHud(String s) {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(0, 0.95, s);
        StdDraw.show();
    }

    private void writeFeedback(String s) {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textRight(0.95, 0.1, s);
        StdDraw.show();
    }

    private void processInput(char input) {
        input = Character.toLowerCase(input);
        if (playMode) {
            hudDisplay.append(input);
            drawHud(hudDisplay.toString());
        }
        switch (input) {
            case 'n':
                setupMode = true;
                break;
            case 'l':
                loadGame();
                setupMode = false;
                break;
            case 's':
                if (setupMode) {
                    random = new Random(Long.parseLong(seedStr.toString()));
                    startNewGame();
                    setupMode = false;
                } else {
                    move(input);
                }
                break;
            case 'w':
            case 'a':
            case 'd':
                if (setupMode) {
                    writeFeedback("Invalid input, please try again.");
                } else {
                    move(input);
                }
                break;
            case ':':
                quitMode = true;
                break;
            case 'q':
                if (quitMode) {
                    saveAndQuit();
                    quitMode = false;
                }
                System.exit(0);
                break;
            default:
                if (Character.isDigit(input)) {
                    seedStr.append(input);
                } else {
                    writeFeedback("Invalid input, please try again.");
                }
                break;
        }
    }

    private void solicitInput(String s) {
        for (char ch : s.toCharArray()) {
            processInput(ch);
        }
    }

    private void solicitInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                processInput(StdDraw.nextKeyTyped());
            }
        }
    }

    private void startNewGame() {
        if (playMode) {
            ter.initialize(WIDTH, HEIGHT);
        }
        WorldGenerator wg = new WorldGenerator(WIDTH, HEIGHT, random);
        world = wg.generate();
        playerPosition = wg.getPlayerPosition();
        if (playMode) {
            ter.renderFrame(world);
        }
    }

    private void move(char input) {
        switch (input) {
            case 's':
                if (playerPosition.getyCoordinate() == 0) {
                    return;
                }
                if (world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate() - 1].equals(Tileset.FLOOR)) {
                    world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate()] = Tileset.FLOOR;
                    world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate() - 1] = Tileset.PLAYER;
                    playerPosition.setyCoordinate(playerPosition.getyCoordinate() - 1);
                    if (playMode) {
                        ter.renderFrame(world);
                    }
                }
                return;
            case 'w':
                if (playerPosition.getyCoordinate() == HEIGHT - 1) {
                    return;
                }
                if (world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate() + 1].equals(Tileset.FLOOR)) {
                    world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate()] = Tileset.FLOOR;
                    world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate() + 1] = Tileset.PLAYER;
                    playerPosition.setyCoordinate(playerPosition.getyCoordinate() + 1);
                    if (playMode) {
                        ter.renderFrame(world);
                    }
                }
                return;
            case 'a':
                if (playerPosition.getxCoordinate() == 0) {
                    return;
                }
                if (world[playerPosition.getxCoordinate() - 1][playerPosition.getyCoordinate()].equals(Tileset.FLOOR)) {
                    world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate()] = Tileset.FLOOR;
                    world[playerPosition.getxCoordinate() - 1][playerPosition.getyCoordinate()] = Tileset.PLAYER;
                    playerPosition.setxCoordinate(playerPosition.getxCoordinate() - 1);
                    if (playMode) {
                        ter.renderFrame(world);
                    }
                }
                return;
            case 'd':
                if (playerPosition.getxCoordinate() == WIDTH - 1) {
                    return;
                }
                if (world[playerPosition.getxCoordinate() + 1][playerPosition.getyCoordinate()].equals(Tileset.FLOOR)) {
                    world[playerPosition.getxCoordinate()][playerPosition.getyCoordinate()] = Tileset.FLOOR;
                    world[playerPosition.getxCoordinate() + 1][playerPosition.getyCoordinate()] = Tileset.PLAYER;
                    playerPosition.setxCoordinate(playerPosition.getxCoordinate() + 1);
                    if (playMode) {
                        ter.renderFrame(world);
                    }
                }
            default:
                return;
        }
    }

    private void saveAndQuit() {
        if (!quitMode) {
            return;
        }

        File savedFile = new File(SAVEFILE);
        try {
            if (!savedFile.exists()) {
                savedFile.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(savedFile);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(world);
            oos.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        quitMode = false;
    }

    private void loadGame() {
        if (playMode) {
            ter.initialize(WIDTH, HEIGHT);
        }
        File savedFile = new File(SAVEFILE);
        try {
            FileInputStream fin = new FileInputStream(savedFile);
            ObjectInputStream ois = new ObjectInputStream(fin);
            world = (TETile[][]) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("You haven't saved any game yet.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println("Can not read map from saved file.");
            System.exit(1);
        }

        playerPosition = findPlayer(world);
        if (playMode) {
            ter.renderFrame(world);
        }
    }

    private WorldGenerator.Position findPlayer(TETile[][] world) {
        for (int w = 0; w < WIDTH; w++) {
            for (int h = 0; h < HEIGHT; h++) {
                if (world[w][h].equals(Tileset.PLAYER)) {
                    WorldGenerator.Position playerPosition = new WorldGenerator.Position(w, h);
                    return playerPosition;
                }
            }
        }
        return null;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        playMode = true;
        openWelcomeWindow();
        solicitInput();
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
        playMode = false;
        solicitInput(input);
        return world;
    }
    // Main method for testing purposes:
    public static void main(String[] args) {
        Game game = new Game();
        game.playWithKeyboard();
    }
}
