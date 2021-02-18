import java.awt.*;
import java.util.Random;

public class Bird {
    int x, y, gravity;

    Random dice = new Random();
    int birdSide = Variables.birdSide;
    Rectangle birdRect = new Rectangle(50, 50, birdSide, birdSide);
    Network brain;

    public Bird() {
        x = 60;
        y = dice.nextInt(50) - 50 + 360;
        gravity = 1;
        brain = new Network(new int[]{4, 4, 2});
    }

    public Bird(int[] layers) {
        x = 60;
        y = dice.nextInt(50) - 50 + 360;
        gravity = 1;
        brain = new Network(layers);
    }

    public void jump() {
        gravity = -10;
    }

    public void think(Column closestColumn) {
        double[] input = new double[]{y, closestColumn.x, closestColumn.rectUp.y, closestColumn.rectDown.y};
        double[] output = brain.calculate(input);
        if (output[0] > output[1]) {
            jump();
        }
    }

    public void repaintBird(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        g.setColor(Color.RED);
        gravity++;
        y = y + gravity;
        birdRect.x = x;
        birdRect.y = y;
        gg.setStroke(new BasicStroke(2));
        gg.drawRect(birdRect.x, birdRect.y, birdRect.width, birdRect.height);
    }
}
