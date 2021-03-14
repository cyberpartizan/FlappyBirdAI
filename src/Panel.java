import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Panel {
    public JFrame frame;

    private JTextField hiddenLayerTextField;
    public JLabel popNumberLbl;
    public JLabel countBirdsLivesLbl;
    public JLabel obstacleLbl;
    public JLabel maxObstacleLbl;
    public JPanel panel_1;
    Font myFont;
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
        JLabel animationSpeedLbl = new JLabel("");
        JLabel freeSpaceLbl = new JLabel("");
        JLabel lblNewLabel_1 = new JLabel(
                "Свободное пространство");
        JLabel lblNewLabel = new JLabel(
                "Скорость анимации");
        JSlider slider_1 = new JSlider();
        JSlider slider = new JSlider();

        slider_1.addChangeListener(e -> {
            freeSpaceLbl.setText(Integer.toString(slider_1.getValue()));
            Variables.columnSpace = slider_1.getValue();
        });
        myFont = new Font("Times New Roman", Font.PLAIN, 16);
        JLabel MaxBirdNumbLbl = new JLabel("");
        MaxBirdNumbLbl.setFont(myFont);
        MaxBirdNumbLbl.setBounds(235, 131, 60, 31);
        frame.getContentPane().add(MaxBirdNumbLbl);
        JLabel maxBirdLbl = new JLabel(
                "Максимальное количество птиц");
        maxBirdLbl.setFont(myFont);
        maxBirdLbl.setBounds(10, 133, 226, 29);
        frame.getContentPane().add(maxBirdLbl);

        slider.addChangeListener(arg0 -> {
            animationSpeedLbl.setText(Integer.toString(slider.getValue()));
            Variables.animationSpeed = slider.getValue();
        });
        slider.setValue(0);
        slider.setMaximum(20);
        slider.setBounds(10, 48, 243, 23);
        frame.getContentPane().add(slider);

        lblNewLabel.setFont(myFont);
        lblNewLabel.setBounds(10, 26, 177, 25);
        frame.getContentPane().add(lblNewLabel);
        Variables.columnSpace = 100;
        slider_1.setValue(Variables.columnSpace);
        slider_1.setMaximum(350);
        slider_1.setMinimum(70);
        slider_1.setBounds(10, 111, 243, 23);
        frame.getContentPane().add(slider_1);

        lblNewLabel_1.setFont(myFont);
        lblNewLabel_1.setBounds(10, 81, 218, 27);
        frame.getContentPane().add(lblNewLabel_1);

        freeSpaceLbl.setFont(myFont);
        freeSpaceLbl.setBounds(192, 81, 36, 27);
        frame.getContentPane().add(freeSpaceLbl);

        animationSpeedLbl.setFont(myFont);
        animationSpeedLbl.setBounds(149, 26, 38, 25);
        frame.getContentPane().add(animationSpeedLbl);

        JButton btnNewButton = new JButton(
                "Параметры скрытых слоев");
        btnNewButton.addActionListener(e -> {
            Variables.bestBird = null;

            String s;
            int[] intHiddenLayer;
            s = hiddenLayerTextField.getText();
            String[] hiddenLayerString = s.split(",");
            if (hiddenLayerString.length == 1 && hiddenLayerString[0].isEmpty()) {
                intHiddenLayer = new int[2];
                intHiddenLayer[0] = 4;
                intHiddenLayer[1] = 2;
            } else {

                intHiddenLayer = new int[hiddenLayerString.length + 2];
                intHiddenLayer[0] = 4;
                for (int i = 1; i < intHiddenLayer.length - 1; i++) {
                    intHiddenLayer[i] = Integer.parseInt(hiddenLayerString[i - 1]);
                }
                intHiddenLayer[intHiddenLayer.length - 1] = 2;
            }
            Variables.hiddenLayers = intHiddenLayer;
            Variables.populationCount = 0;
            Variables.maxColumnsPassed = 0;
            Variables.newGame();
            Variables.sleep.start();
            Graphics gg = panel_1.getGraphics();
            panel_1.paint(gg);
        });
        btnNewButton.setFont(myFont);
        btnNewButton.setBounds(307, 174, 243, 23);
        frame.getContentPane().add(btnNewButton);

        hiddenLayerTextField = new JTextField();
        hiddenLayerTextField.setText("4");
        hiddenLayerTextField
                .setToolTipText("Через запятую");
        hiddenLayerTextField.setFont(myFont);
        hiddenLayerTextField.setBounds(307, 208, 243, 27);
        frame.getContentPane().add(hiddenLayerTextField);
        hiddenLayerTextField.setColumns(10);

        JButton pauseBTN = new JButton("Пауза");
        pauseBTN.setFont(myFont);
        pauseBTN.addActionListener(arg0 -> Variables.sleep.stop());

        pauseBTN.setBounds(379, 0, 89, 29);
        frame.getContentPane().add(pauseBTN);
        JButton saveAI = new JButton("Сохранить ИИ");
        saveAI.setFont(myFont);
        saveAI.addActionListener(arg0 -> Variables.sleep.stop());

        saveAI.setBounds(315, 240, 200, 25);
        frame.getContentPane().add(saveAI);
        JButton restoreAI = new JButton("Восстановить ИИ");
        restoreAI.setFont(myFont);
        restoreAI.addActionListener(arg0 -> Variables.sleep.stop());

        restoreAI.setBounds(315, 270, 200, 25);
        frame.getContentPane().add(restoreAI);

        JButton startBTN = new JButton("Старт");
        startBTN.setFont(myFont);
        startBTN.addActionListener(e -> Variables.sleep.start());

        startBTN.setBounds(280, 0, 89, 29);
        frame.getContentPane().add(startBTN);

        JSlider slider_2 = new JSlider();
        slider_2.setMinimum(1);
        slider_2.setMaximum(3000);
        slider_2.setBounds(10, 162, 243, 23);
        Variables.population = 500;
        slider_2.setValue(Variables.population);
        MaxBirdNumbLbl.setText(Integer.toString(Variables.population));
        frame.getContentPane().add(slider_2);
        slider_2.addChangeListener(arg0 -> {
            Variables.population = slider_2.getValue();
            MaxBirdNumbLbl.setText(Integer.toString(Variables.population));
        });

        JButton CreateSimulationBTN = new JButton(
                "Создать новую симуляцию");
        CreateSimulationBTN.setFont(myFont);
        CreateSimulationBTN.addActionListener(arg0 -> {
            Variables.populationCount = 0;
            Variables.bestBird = null;
            Variables.maxColumnsPassed = 0;
            Variables.newGame();
            Variables.sleep.start();
        });
        CreateSimulationBTN.setBounds(10, 0, 221, 27);
        frame.getContentPane().add(CreateSimulationBTN);

        JLabel lblNewLabel_2 = new JLabel("Популяция №");
        lblNewLabel_2.setFont(myFont);
        lblNewLabel_2.setBounds(280, 38, 94, 18);
        frame.getContentPane().add(lblNewLabel_2);

        popNumberLbl = new JLabel("");
        popNumberLbl.setFont(myFont);
        popNumberLbl.setBounds(384, 38, 89, 18);
        frame.getContentPane().add(popNumberLbl);

        JLabel lblNewLabel_3 = new JLabel(
                "Число живых птиц:");
        lblNewLabel_3.setFont(myFont);
        lblNewLabel_3.setBounds(280, 67, 133, 25);
        frame.getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel(
                "Препятствий пройдено : ");
        lblNewLabel_4.setFont(myFont);
        lblNewLabel_4.setBounds(280, 103, 166, 19);
        frame.getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel(
                "Максимально пройдено препятствий: ");
        lblNewLabel_5.setFont(myFont);
        lblNewLabel_5.setBounds(280, 138, 270, 25);
        frame.getContentPane().add(lblNewLabel_5);

        countBirdsLivesLbl = new JLabel("");
        countBirdsLivesLbl.setFont(myFont);
        countBirdsLivesLbl.setBounds(423, 73, 46, 14);
        frame.getContentPane().add(countBirdsLivesLbl);

        obstacleLbl = new JLabel("");
        obstacleLbl.setFont(myFont);
        obstacleLbl.setBounds(456, 105, 46, 14);
        frame.getContentPane().add(obstacleLbl);

        maxObstacleLbl = new JLabel("");
        maxObstacleLbl.setFont(myFont);
        maxObstacleLbl.setBounds(544, 143, 46, 14);
        frame.getContentPane().add(maxObstacleLbl);

        JLabel chanceMutateLbl = new JLabel();
        chanceMutateLbl.setFont(myFont);
        chanceMutateLbl.setBounds(222, 190, 48, 17);
        frame.getContentPane().add(chanceMutateLbl);

        JLabel maxWeightChangeLbl = new JLabel();
        maxWeightChangeLbl.setFont(myFont);
        maxWeightChangeLbl.setBounds(263, 251, 60, 15);
        frame.getContentPane().add(maxWeightChangeLbl);

        panel_1 = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.GRAY);
                Graphics2D gg = (Graphics2D) g;
                g.setColor(Color.GRAY);
                int width = panel_1.getWidth();
                int height = panel_1.getHeight();
                g.fillRect(0, 0, width, height);
                int widthCell = width / (Variables.hiddenLayers.length + 1);
                //Раскраска линий нейросети
                for (int layer = 1; layer < Variables.hiddenLayers.length; layer++) {
                    int heightCell = height / (Variables.hiddenLayers[layer - 1] + 1);
                    for (int neuron = 1; neuron <= Variables.hiddenLayers[layer]; neuron++) {
                        int nextHeightCell = height / (Variables.hiddenLayers[layer] + 1);
                        for (int prevNeuron = 1; prevNeuron <= Variables.hiddenLayers[layer - 1]; prevNeuron++) {
                            BasicStroke stroke;
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
                                gg.drawLine((widthCell * layer) + 15, (heightCell * prevNeuron) + 15,
                                        (widthCell * (layer + 1)) + 15, (nextHeightCell * neuron) + 15);
                            }

                        }
                    }
                }

                for (int layer = 1; layer <= Variables.hiddenLayers.length; layer++) {
                    int heightCell = height / (Variables.hiddenLayers[layer - 1] + 1);
                    for (int neuron = 1; neuron <= Variables.hiddenLayers[layer - 1]; neuron++) {
                        if (layer == 1) {
                            g.setColor(Color.MAGENTA.darker().darker());
                        } else if (layer == Variables.hiddenLayers.length) {
                            g.setColor(Color.YELLOW);
                        } else {
                            g.setColor(Color.red);
                        }
                        g.fillOval(widthCell * layer - 1, heightCell * neuron, 30, 30);
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
        slider_3.addChangeListener(arg0 -> {
            chanceMutateLbl.setText(Double.toString(slider_3.getValue()) + "%");
            Variables.chanceMutate = slider_3.getValue();
        });

        slider_3.setValue(10);
        slider_3.setBounds(10, 212, 243, 23);
        Variables.chanceMutate = slider_3.getValue();
        frame.getContentPane().add(slider_3);

        JSlider slider_4 = new JSlider();
        slider_4.setValue(10);
        Variables.maxWeightChange = slider_4.getValue();
        maxWeightChangeLbl.setText(Variables.maxWeightChange + "%");
        slider_4.addChangeListener(arg0 -> {
            maxWeightChangeLbl.setText(Variables.maxWeightChange + "%");
            Variables.maxWeightChange = slider_4.getValue();
        });
        slider_4.setBounds(10, 267, 243, 23);
        frame.getContentPane().add(slider_4);
        JLabel lblNewLabel_6 = new JLabel(
                "Вероятность мутации нейрона");
        lblNewLabel_6.setFont(myFont);
        lblNewLabel_6.setBounds(10, 187, 218, 23);
        frame.getContentPane().add(lblNewLabel_6);

        JLabel lblNewLabel_8 = new JLabel(
                "Максимальный сдвиг веса нейрона");
        lblNewLabel_8.setFont(myFont);
        lblNewLabel_8.setBounds(10, 246, 243, 20);
        frame.getContentPane().add(lblNewLabel_8);


    }
}
