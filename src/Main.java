import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    public Variables var = new Variables();
    public int height, width;
    public static Main Main;
    public Renderer renderer;
    public static Panel panel;
    boolean columnPassed;
    boolean lastBirdBool = false;
    int delay;
    Font font = new Font("TimesRoman", Font.BOLD, 27);


    public Main() {

        width = Variables.WIDTH;
        columnPassed = false;
        delay = 20 - Variables.animationSpeed;
        Variables.populationCount = 0;
        Variables.columnsPassed = 0;
        Variables.maxColumnsPassed = 0;
        height = Variables.HEIGHT;
        Variables.counter = -1;
        JFrame window = new JFrame();
        renderer = new Renderer();
        Variables.sleep = new Timer(20, this);
        window.add(renderer);
        window.setSize(width, height);
        window.setTitle("Flappy Bird AI");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        newGeneration(true);
    }

    public void newGeneration(boolean newGame) {
        Variables.clear();
        if (newGame) {
            for (int i = 0; i < Variables.population; i++) {
                Variables.birds.add(new Bird());
            }
        } else {
            Variables.generateFromBest();
        }
    }

    public void repaint(Graphics g) {
        g.setColor(Color.black);
        delay = 20 - Variables.animationSpeed;
        Variables.sleep.setDelay(delay);
        Variables.counter++;
        g.fillRect(0, 0, width, height);
        collision();
        Column closestColumn;
        if (Variables.columns.get(0).x + Variables.columns.get(0).rectDown.width < 60) {
            closestColumn = Variables.columns.get(1);
            closestColumn.color = Color.BLUE;
            Variables.columns.get(0).color = Color.green;
            if (columnPassed) {
                columnPassed = false;
                Variables.columnsPassed++;
                if (Variables.maxColumnsPassed < Variables.columnsPassed) {
                    Variables.maxColumnsPassed = Variables.columnsPassed;
                }
            }
        } else {
            closestColumn = Variables.columns.get(0);
            closestColumn.color = Color.BLUE;
            columnPassed = true;
        }
        for (int i = 0; i < Variables.columns.size(); i++) {
            Variables.columns.get(i).repaintColumn(g);
        }

        for (int i = 0; i < Variables.birds.size(); i++) {
            Variables.birds.get(i).think(closestColumn);
            Variables.birds.get(i).repaintBird(g);
        }
        if (Variables.counter % 101 == 0) {
            Variables.columns.add(new Column());
        }
        if (Variables.columns.get(0).x + Variables.columns.get(0).fatness < 0) {
            Variables.columns.remove(0);
        }
        g.setFont(font);
        g.setColor(Color.white);
        panel.populationNumberLbl.setText(Integer.toString(Variables.populationCount));
        panel.countBirdsLivesLbl.setText(Integer.toString(Variables.birds.size()));
        panel.obstacleLbl.setText(Integer.toString(Variables.columnsPassed));
        panel.maxObstacleLbl.setText(Integer.toString(Variables.maxColumnsPassed));
    }

    public void collision() {
        Variables.birds.removeIf(bird -> bird.y < 0 || bird.y + 2 * Variables.BIRD_SIDE > height);
        Bird brd;
        for (int i = 0; i < Variables.birds.size(); i++) {
            brd = Variables.birds.get(i);
            if (Variables.birds.size() == 1) {
                if (lastBirdBool && Variables.maxColumnsPassed <= Variables.columnsPassed) {
                    Variables.bestBird = Variables.birds.get(0);
                    Graphics gg = panel.panel.getGraphics();
                    panel.panel.paint(gg);
                    lastBirdBool = false;
                }
            } else {
                lastBirdBool = true;
            }
            if (brd.birdRect.intersects(Variables.columns.get(0).rectDown)
                    || brd.birdRect.intersects(Variables.columns.get(0).rectUp)) {
                Variables.birds.remove(brd);
            }
        }

        if (Variables.birds.size() == 0) {
            Variables.counter = -1;
            newGeneration(false);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                panel = new Panel();
                panel.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Main = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        renderer.repaint();
    }
}