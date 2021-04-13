import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;

public class Variables {
    public static Connection con = DB.connect();
    public static int width, height;
    public static int columnSpace, columnsPassed;
    public static int population, populationCount;
    public static int birdSide;
    public static int animationSpeed;
    public static int counter;
    public static Timer sleep;
    public static int[] hiddenLayers = new int[]{4, 4, 2};
    public static ArrayList<Column> columns = new ArrayList<>();
    public static ArrayList<Bird> birds = new ArrayList<>();
    public static int maxColumnsPassed;
    public static Bird bestBird;
    public static double chanceMutate;
    public static double maxWeightChange;
    public static Panel panel = new Panel();

    public Variables() {
        width = 1280;
        height = 720;
        birdSide = 13;
    }

    public static void newGame() {
        clear();
        for (int i = 0; i < population; i++) {
            birds.add(new Bird(hiddenLayers));
        }
    }

    public static void clear() {
        Variables.columns.clear();
        Variables.birds.clear();
        Variables.populationCount++;
        Variables.counter = -1;
        Variables.columnsPassed = 0;
        for (int i = 0; i < 3; i++) {
            Variables.columns.add(new Column((i * 305) + 400));
        }
    }

    public static void generateFromBest() {
        for (int i = 0; i < Variables.population; i++) {
            Variables.birds.add(new Bird());
            Variables.birds.get(i).brain = new Network(Variables.bestBird.brain);
            Variables.birds.get(i).brain.mutate(i);
        }
    }

}
