import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Panel {
	public JFrame frame;
	public Variables var = new Variables();
	private JTextField hiddenlayerTextField;
	public JLabel numbPopLabl;
	public JLabel countlivebirdslbl;
	public JLabel prepiatlbl;
	public JLabel maxprepiatlbl;
	public JPanel panel_1;

	public Panel() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
			}
		});
		frame.setBounds(100, 100, 615, 654);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		JLabel animationspeedLable = new JLabel("");
		JLabel freespaceLable = new JLabel("");
		JLabel lblNewLabel_1 = new JLabel(
				"\u0421\u0432\u043E\u0431\u043E\u0434\u043D\u043E\u0435 \u043F\u0440\u043E\u0441\u0442\u0440\u0430\u043D\u0441\u0442\u0432\u043E");
		JLabel lblNewLabel = new JLabel(
				"\u0421\u043A\u043E\u0440\u043E\u0441\u044C \u0430\u043D\u0438\u043C\u0430\u0446\u0438\u0438");
		JSlider slider_1 = new JSlider();
		JSlider slider = new JSlider();

		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				freespaceLable.setText(Integer.toString(slider_1.getValue()));
				Variables.ColumSpace = slider_1.getValue();
			}

		});
		JLabel MaxBirdNumbLable = new JLabel("");
		MaxBirdNumbLable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		MaxBirdNumbLable.setBounds(235, 131, 60, 31);
		frame.getContentPane().add(MaxBirdNumbLable);
		JLabel MaxBirdLable = new JLabel(
				"\u041C\u0430\u043A\u0441\u0438\u043C\u0430\u043B\u044C\u043D\u043E\u0435 \u043A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u0442\u0438\u0446");
		MaxBirdLable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		MaxBirdLable.setBounds(10, 133, 226, 29);
		frame.getContentPane().add(MaxBirdLable);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				animationspeedLable.setText(Integer.toString(slider.getValue()));
				Variables.animationspeed = slider.getValue();
			}
		});
		slider.setValue(0);
		slider.setMaximum(20);
		slider.setBounds(10, 48, 243, 23);
		frame.getContentPane().add(slider);

		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 26, 177, 25);
		frame.getContentPane().add(lblNewLabel);
		Variables.ColumSpace = 100;
		slider_1.setValue(Variables.ColumSpace);
		slider_1.setMaximum(350);
		slider_1.setMinimum(70);
		slider_1.setBounds(10, 111, 243, 23);
		frame.getContentPane().add(slider_1);

		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 81, 218, 27);
		frame.getContentPane().add(lblNewLabel_1);

		freespaceLable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		freespaceLable.setBounds(192, 81, 36, 27);
		frame.getContentPane().add(freespaceLable);

		animationspeedLable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		animationspeedLable.setBounds(149, 26, 38, 25);
		frame.getContentPane().add(animationspeedLable);

		JButton btnNewButton = new JButton(
				"\u041F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u044B \u0441\u043A\u0440\u044B\u0442\u044B\u0445 \u0441\u043B\u043E\u0435\u0432");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Variables.bestBird = null;

				String s;
				int[] inthiddenlayer;
				s = hiddenlayerTextField.getText();
				String[] hiddenLayerString = s.split(",");
				if (hiddenLayerString.length == 1 && hiddenLayerString[0].isEmpty()) {
					inthiddenlayer = new int[2];
					inthiddenlayer[0] = 4;
					inthiddenlayer[1] = 2;
				} else {

					inthiddenlayer = new int[hiddenLayerString.length + 2];
					inthiddenlayer[0] = 4;
					for (int i = 1; i < inthiddenlayer.length - 1; i++) {
						inthiddenlayer[i] = Integer.valueOf(hiddenLayerString[i - 1]);
					}
					inthiddenlayer[inthiddenlayer.length - 1] = 2;
				}
				Variables.hidenlayers = inthiddenlayer;
				Variables.populationcount = 0;
				Variables.maxcolumspassed = 0;
				Variables.newgame();
				Variables.sleep.start();
				Graphics gg = panel_1.getGraphics();
				panel_1.paint(gg);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton.setBounds(307, 174, 243, 23);
		frame.getContentPane().add(btnNewButton);

		hiddenlayerTextField = new JTextField();
		hiddenlayerTextField.setText("4");
		hiddenlayerTextField
				.setToolTipText("\u0427\u0435\u0440\u0435\u0437 \u0437\u0430\u043F\u044F\u0442\u0443\u044E");
		hiddenlayerTextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		hiddenlayerTextField.setBounds(307, 208, 243, 27);
		frame.getContentPane().add(hiddenlayerTextField);
		hiddenlayerTextField.setColumns(10);

		JButton pauseBTN = new JButton("\u041F\u0430\u0443\u0437\u0430");
		pauseBTN.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		pauseBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Variables.sleep.stop();
			}
		});

		pauseBTN.setBounds(379, 0, 89, 29);
		frame.getContentPane().add(pauseBTN);

		JButton startBTN = new JButton("\u0421\u0442\u0430\u0440\u0442");
		startBTN.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		startBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Variables.sleep.start();
			}
		});

		startBTN.setBounds(280, 0, 89, 29);
		frame.getContentPane().add(startBTN);

		JSlider slider_2 = new JSlider();
		slider_2.setMinimum(1);
		slider_2.setMaximum(3000);
		slider_2.setBounds(10, 162, 243, 23);
		Variables.population = 500;
		slider_2.setValue(Variables.population);
		MaxBirdNumbLable.setText(Integer.toString(Variables.population));
		frame.getContentPane().add(slider_2);
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Variables.population = slider_2.getValue();
				MaxBirdNumbLable.setText(Integer.toString(Variables.population));
			}
		});

		JButton CreateSimulationBTN = new JButton(
				"\u0421\u043E\u0437\u0434\u0430\u0442\u044C \u043D\u043E\u0432\u0443\u044E \u0441\u0438\u043C\u0443\u043B\u044F\u0446\u0438\u044E");
		CreateSimulationBTN.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		CreateSimulationBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Variables.populationcount = 0;
				Variables.bestBird = null;
				Variables.maxcolumspassed = 0;
				Variables.newgame();
				Variables.sleep.start();
			}
		});
		CreateSimulationBTN.setBounds(10, 0, 221, 27);
		frame.getContentPane().add(CreateSimulationBTN);

		JLabel lblNewLabel_2 = new JLabel("\u041F\u043E\u043F\u0443\u043B\u044F\u0446\u0438\u044F \u2116");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(280, 38, 94, 18);
		frame.getContentPane().add(lblNewLabel_2);

		numbPopLabl = new JLabel("");
		numbPopLabl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		numbPopLabl.setBounds(384, 38, 89, 18);
		frame.getContentPane().add(numbPopLabl);

		JLabel lblNewLabel_3 = new JLabel(
				"\u0427\u0438\u0441\u043B\u043E \u0436\u0438\u0432\u044B\u0445 \u043F\u0442\u0438\u0446:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(280, 67, 133, 25);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel(
				"\u041F\u0440\u043E\u0438\u0434\u0435\u043D\u043E \u043F\u0440\u0435\u043F\u044F\u0442\u0441\u0442\u0432\u0438\u0439: ");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(280, 103, 166, 19);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel(
				"\u041C\u0430\u043A\u0441\u0438\u043C\u0430\u043B\u044C\u043D\u043E \u043F\u0440\u043E\u0438\u0434\u0435\u043D\u043E \u043F\u0440\u0435\u043F\u044F\u0442\u0441\u0442\u0432\u0438\u0439: ");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(280, 138, 270, 25);
		frame.getContentPane().add(lblNewLabel_5);

		countlivebirdslbl = new JLabel("");
		countlivebirdslbl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		countlivebirdslbl.setBounds(423, 73, 46, 14);
		frame.getContentPane().add(countlivebirdslbl);

		prepiatlbl = new JLabel("");
		prepiatlbl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		prepiatlbl.setBounds(456, 105, 46, 14);
		frame.getContentPane().add(prepiatlbl);

		maxprepiatlbl = new JLabel("");
		maxprepiatlbl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		maxprepiatlbl.setBounds(544, 143, 46, 14);
		frame.getContentPane().add(maxprepiatlbl);

		JLabel chancemutatelbl = new JLabel();
		chancemutatelbl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		chancemutatelbl.setBounds(222, 190, 48, 17);
		frame.getContentPane().add(chancemutatelbl);

		JLabel maxweightchangelbl = new JLabel();
		maxweightchangelbl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		maxweightchangelbl.setBounds(263, 251, 60, 15);
		frame.getContentPane().add(maxweightchangelbl);

		panel_1 = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.GRAY);
				Graphics2D gg = (Graphics2D) g;
				g.setColor(Color.GRAY);
				int width = panel_1.getWidth();
				int height = panel_1.getHeight();
				g.fillRect(0, 0, width, height);
				int widthcell = width / (Variables.hidenlayers.length + 1);
				for (int layer = 1; layer < Variables.hidenlayers.length; layer++) {
					int heightcell = height / (Variables.hidenlayers[layer - 1] + 1);
					for (int neuron = 1; neuron <= Variables.hidenlayers[layer]; neuron++) {
						int nextheightcell = height / (Variables.hidenlayers[layer] + 1);
						for (int prevNeuron = 1; prevNeuron <= Variables.hidenlayers[layer - 1]; prevNeuron++) {
							BasicStroke stroke = null;
							if (Variables.bestBird != null) {
								if (Variables.bestBird.brain.weights[layer][neuron - 1][prevNeuron - 1] < 0) {
									gg.setColor(Color.BLUE);
									stroke = new BasicStroke(Math.round(
											-Variables.bestBird.brain.weights[layer][neuron - 1][prevNeuron - 1] * 4));
								} else if (Variables.bestBird.brain.weights[layer][neuron - 1][prevNeuron - 1] == 0) {
									gg.setColor(Color.YELLOW);
									stroke = new BasicStroke(3);
								} else {
									gg.setColor(Color.GREEN);
									stroke = new BasicStroke(Math.round(
											Variables.bestBird.brain.weights[layer][neuron - 1][prevNeuron - 1] * 4));
								}
								gg.setStroke(stroke);
								gg.drawLine((widthcell * layer) + 15, (heightcell * prevNeuron) + 15,
										(widthcell * (layer + 1)) + 15, (nextheightcell * neuron) + 15);
							}

						}
					}
				}

				for (int layer = 1; layer <= Variables.hidenlayers.length; layer++) {
					int heightcell = height / (Variables.hidenlayers[layer - 1] + 1);
					for (int neuron = 1; neuron <= Variables.hidenlayers[layer - 1]; neuron++) {
						if (layer == 1) {
							g.setColor(Color.MAGENTA.darker().darker());
						} else if (layer == Variables.hidenlayers.length) {
							g.setColor(Color.YELLOW);
						} else {
							g.setColor(Color.red);
						}
						g.fillOval(widthcell * layer - 1, heightcell * neuron, 30, 30);
					}
				}

			}
		};

		panel_1.setBounds(0, 301, 609, 325);
		frame.getContentPane().add(panel_1);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGap(0, 536, Short.MAX_VALUE));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGap(0, 270, Short.MAX_VALUE));
		panel_1.setLayout(gl_panel_1);

		JSlider slider_3 = new JSlider();
		slider_3.setMaximum(99);
		slider_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				chancemutatelbl.setText(Double.toString(slider_3.getValue()) + "%");
				Variables.chancemutate = slider_3.getValue();
			}
		});

		slider_3.setValue(10);
		slider_3.setBounds(10, 212, 243, 23);
		Variables.chancemutate = slider_3.getValue();
		frame.getContentPane().add(slider_3);

		JSlider slider_4 = new JSlider();
		slider_4.setValue(10);
		Variables.maxweightchage = slider_4.getValue();
		maxweightchangelbl.setText(Double.toString(Variables.maxweightchage) + "%");
		slider_4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				maxweightchangelbl.setText(Double.toString(Variables.maxweightchage) + "%");
				Variables.maxweightchage = slider_4.getValue();
			}
		});
		slider_4.setBounds(10, 267, 243, 23);
		frame.getContentPane().add(slider_4);

		JLabel lblNewLabel_6 = new JLabel(
				"\u0412\u0435\u0440\u043E\u044F\u0442\u043D\u043E\u0441\u0442\u044C \u043C\u0443\u0442\u0430\u0446\u0438\u0438 \u043D\u0435\u0439\u0440\u043E\u043D\u0430");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(10, 187, 218, 23);
		frame.getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_8 = new JLabel(
				"\u041C\u0430\u043A\u0441\u0438\u043C\u0430\u043B\u044C\u043D\u044B\u0439 \u0441\u0434\u0432\u0438\u0433 \u0432\u0435\u0441\u0430 \u043D\u0435\u0439\u0440\u043E\u043D\u0430");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_8.setBounds(10, 246, 243, 20);
		frame.getContentPane().add(lblNewLabel_8);

		
	}
}
