import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Colum {
	int x, y, fatness, height;
	int space;
	Rectangle rectUp = new Rectangle();
	Rectangle rectDown = new Rectangle();
	Random dice = new Random();
	public Variables var = new Variables();
	Color color;

	public Colum() {
		x = 1280;
		initialization();
	}

	public Colum(int coordX) {
		x = coordX;
		initialization();
	}

	public void initialization() {
		height = Variables.Height;
		space = Variables.columnSpace;
		fatness = 70;
		y = dice.nextInt(height);
		if (y > height - Variables.columnSpace - 90) {
			y = height - Variables.columnSpace - 90;
		}
		if (y < 90) {
			y = 90;
		}
		rectUp.width = fatness;
		rectDown.width = fatness;
		rectUp.height = y;
		rectUp.y = 0;
		rectUp.x = x;
		rectDown.x = x;
		rectDown.height = height;
		rectDown.y = y + Variables.columnSpace;
		color = Color.green;
	}

	public void repaintColumn(Graphics g) {
		g.setColor(color);
		x -= 3;
		updateRect();
		g.fillRect(x, 0, fatness, y);
		g.fillRect(x, y + space, fatness, height);
	}

	private void updateRect() {
		rectUp.x = x;
		rectDown.x = x;
	}
}
