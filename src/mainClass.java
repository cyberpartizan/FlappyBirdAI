import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Timer;

public class mainClass implements ActionListener, KeyListener {
	public int height, width;
	public static mainClass mainClass;
	public Renderer renderer;
	public static Panel panel;
	boolean passedcolum;
	boolean lastbirdbool = false;
	int delay;
	Font font = new Font("TimesRoman", Font.BOLD, 27);
	public Bird bird = new Bird();
	// public Variables var = new Variables();
	// public ArrayList<Colum> colums = new ArrayList<Colum>();
	// public ArrayList<Bird> birds = new ArrayList<Bird>();

	public mainClass() {

		width = Variables.Width;
		passedcolum = false;
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
		newgame(-1);
	}

	public void newgame(int q) {
		Variables.columns.clear();
		Variables.populationCount++;
		Variables.columnsPassed = 0;
		for (int i = 0; i < 3; i++) {
			Variables.columns.add(new Colum((i * 305) + 400));
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
		Colum closestColum;
		if (Variables.columns.get(0).x + Variables.columns.get(0).rectDown.width < 60) {
			closestColum = Variables.columns.get(1);
			closestColum.color = Color.BLUE;
			Variables.columns.get(0).color = Color.green;
			if (passedcolum) {
				passedcolum = false;
				Variables.columnsPassed++;
				if (Variables.maxColumnsPassed < Variables.columnsPassed) {
					Variables.maxColumnsPassed = Variables.columnsPassed;
				}
			}
		} else {
			closestColum = Variables.columns.get(0);
			closestColum.color = Color.BLUE;
			passedcolum = true;
		}
		for (int i = 0; i < Variables.columns.size(); i++) {
			Variables.columns.get(i).repaintColumn(g);
		}

		for (int i = 0; i < Variables.birds.size(); i++) {
			Variables.birds.get(i).think(closestColum);
			Variables.birds.get(i).repaintBird(g);
		}
		if (Variables.counter % 101 == 0) {
			Variables.columns.add(new Colum());
		}
		if (Variables.columns.get(0).x + Variables.columns.get(0).fatness < 0) {
			Variables.columns.remove(0);
		}
		g.setFont(font);
		g.setColor(Color.white);
		panel.numbPopLabl.setText(Integer.toString(Variables.populationCount));
		panel.countlivebirdslbl.setText(Integer.toString(Variables.birds.size()));
		panel.prepiatlbl.setText(Integer.toString(Variables.columnsPassed));
		panel.maxprepiatlbl.setText(Integer.toString(Variables.maxColumnsPassed));
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
				if (lastbirdbool) {
					Variables.bestBird = Variables.birds.get(0);
					Graphics gg= panel.panel_1.getGraphics();
					panel.panel_1.paint(gg);
					lastbirdbool = false;
				}
			} else {
				lastbirdbool = true;
			}
			if (brd.birdRect.intersects(Variables.columns.get(0).rectDown)
					|| brd.birdRect.intersects(Variables.columns.get(0).rectUp)) {
				Variables.birds.remove(brd);
			}
		}

		if (Variables.birds.size() == 0) {
			Variables.counter = -1;
			newgame(1);
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
		Variables.hiddenLayers = new int[] { 4, 4, 2 };
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