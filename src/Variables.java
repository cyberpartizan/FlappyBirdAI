
import java.util.ArrayList;
import javax.swing.Timer;

public class Variables {
	public static int Width, Height, columnSpace, animationSpeed, populationCount, columnsPassed, birdWidth, population,counter;
	public static Timer sleep;
	public static int[] hiddenLayers = new int[] { 4, 4, 2 };
	public static ArrayList<Colum> columns = new ArrayList<Colum>();
	public static ArrayList<Bird> birds = new ArrayList<Bird>();
	public static int maxColumnsPassed;
	public static Bird bestBird;
	public static double chanceMutate;
	public static double maxWeightChange;
	public static Panel panel = new Panel();

	public Variables() {
		Width = 1280;
		Height = 720;
		birdWidth = 13;

	}

	public int getWidth() {
		return Width;
	}

	public static void newgame() {
		columns.clear();
		counter=-1;
		birds.clear();
		populationCount++;
		columnsPassed = 0;
		for (int i = 0; i < 3; i++) {
			columns.add(new Colum((i * 305) + 400));
		}

		for (int i = 0; i < population; i++) {
			birds.add(new Bird(hiddenLayers));
		}
	}

}
