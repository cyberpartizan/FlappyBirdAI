import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class mainClass implements ActionListener, KeyListener {
    public int height, width;
    public Variables var = new Variables();
    public static mainClass mainClass;
    public Renderer renderer;
    public static Panel panel;
    boolean columnPassed;
    boolean lastBirdBool = false;
    int delay;
    Font font = new Font("TimesRoman", Font.BOLD, 27);

    public mainClass() {

        width = Variables.Width;
        columnPassed = false;
        delay = 20 - Variables.animationSpeed;
        Variables.populationCount = 0;
        Variables.columnsPassed = 0;
        Variables.maxColumnsPassed = 0;
        height = Variables.Height;
        Variables.counter = -1;
        JFrame window = new JFrame();
        renderer = new Renderer();
        Variables.sleep = new Timer(20, this);
        window.add(renderer);
        window.setSize(width, height);
        window.setTitle("Flappy Bird Нейросеть");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        window.addKeyListener(this);
        newGame(-1);
    }

    public void newGame(int q) {
        Variables.columns.clear();
        Variables.populationCount++;
        Variables.columnsPassed = 0;
        for (int i = 0; i < 3; i++) {
            Variables.columns.add(new Column((i * 305) + 400));
        }
        if (q < 0) {
            for (int i = 0; i < Variables.population; i++) {
                Variables.birds.add(new Bird());
            }
        } else {
            for (int i = 0; i < Variables.population; i++) {
                Variables.birds.add(new Bird());
                Variables.birds.get(i).brain = new Network(Variables.bestBird.brain);
                Variables.birds.get(i).brain.mutate(i);
            }
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
        panel.popNumberLbl.setText(Integer.toString(Variables.populationCount));
        panel.countBirdsLivesLbl.setText(Integer.toString(Variables.birds.size()));
        panel.obstacleLbl.setText(Integer.toString(Variables.columnsPassed));
        panel.maxObstacleLbl.setText(Integer.toString(Variables.maxColumnsPassed));
    }

    public void collision() {
        for (int i = 0; i < Variables.birds.size(); i++) {
            if (Variables.birds.get(i).y < 0 || Variables.birds.get(i).y + 2 * Variables.birdWidth > height) {
                Variables.birds.remove(i);
            }
        }
        Bird brd;
        for (int i = 0; i < Variables.birds.size(); i++) {
            brd = Variables.birds.get(i);
            if (Variables.birds.size() == 1) {
                if (lastBirdBool) {
                    Variables.bestBird = Variables.birds.get(0);
                    Graphics gg = panel.panel_1.getGraphics();
                    panel.panel_1.paint(gg);
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
            newGame(1);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            for (int i = 0; i < Variables.birds.size(); i++) {
                Variables.birds.get(i).jump();
            }
        }
        if (key == KeyEvent.VK_UP) {
            Variables.sleep.setDelay(0);
        }
        if (key == KeyEvent.VK_DOWN) {
            Variables.sleep.setDelay(15);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    panel = new Panel();
                    panel.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mainClass = new mainClass();

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        renderer.repaint();
    }
}