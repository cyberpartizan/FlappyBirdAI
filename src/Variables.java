
import java.util.ArrayList;
import javax.swing.Timer;

public class Variables {
	public static int Width, Height, ColumSpace, animationspeed, populationcount, columspassed, birdwidth, population,counter;
	public static Timer sleep;
	public static int[] hidenlayers = new int[] { 4, 4, 2 };
	public static ArrayList<Colum> colums = new ArrayList<Colum>();
	public static ArrayList<Bird> birds = new ArrayList<Bird>();
	public static int maxcolumspassed;
	public static Bird bestBird;
	public static double chancemutate;
	public static double maxweightchage;
	public static Panel panel = new Panel();

	public Variables() {
		Width = 1280;
		Height = 720;
		birdwidth = 13;

	}

	public int getWidth() {
		return Width;
	}

	public static void newgame() {
		colums.clear();
		counter=-1;
		birds.clear();
		populationcount++;
		columspassed = 0;
		for (int i = 0; i < 3; i++) {
			colums.add(new Colum((i * 305) + 400));
		}

		for (int i = 0; i < population; i++) {
			birds.add(new Bird(hidenlayers));
		}
	}

}
