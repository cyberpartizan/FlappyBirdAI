import java.awt.*;
import java.util.Random;

public class Column {
    int x, y, fatness, height;
    int space;
    Rectangle rectUp = new Rectangle();
    Rectangle rectDown = new Rectangle();
    Random dice = new Random();
    Color color;

    public Column() {
        x = 1280;
        init();
    }

    public Column(int coordinateX) {
        x = coordinateX;
        init();
    }

    public void init() {
        height = Variables.height;
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
        rectUp.x = x;
        rectDown.x = x;
        g.fillRect(x, 0, fatness, y);
        g.fillRect(x, y + space, fatness, height);
    }
}
