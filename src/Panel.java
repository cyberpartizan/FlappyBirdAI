import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.sql.*;

public class Panel {
    public JFrame frame;

    private JTextField hiddenLayerTextField;
    private JComboBox networkNamesComBox;
    public JLabel populationNumberLbl;
    public JLabel countBirdsLivesLbl;
    public JLabel obstacleLbl;
    public JLabel maxObstacleLbl;
    public JPanel panel;
    public static String saveAI = "INSERT INTO bird_brain(name, object, population, max_columns_passed) VALUES (?, ?, ?, ?)";
    public static String getAI = "SELECT object,population,max_columns_passed FROM bird_brain WHERE name = ?";
    public static String getAiNames = "SELECT name FROM bird_brain";
    Font myFont;
    Font buttonsFont;

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
        buttonsFont = new Font("Times New Roman", Font.PLAIN, 14);
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

        JButton hiddenLayersBtn = new JButton(
                "Выставить параметры скрытых слоев");
        hiddenLayersBtn.addActionListener(e -> {
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
                try{
                    for (int i = 1; i < intHiddenLayer.length - 1; i++) {
                        intHiddenLayer[i] = Integer.parseInt(hiddenLayerString[i - 1]);
                    }
                }catch (NumberFormatException  exception){
                    exception.printStackTrace();
                    return;
                }
                intHiddenLayer[intHiddenLayer.length - 1] = 2;
            }
            Variables.hiddenLayers = intHiddenLayer;
            Variables.populationCount = 0;
            Variables.maxColumnsPassed = 0;
            Variables.newGame();
            Variables.sleep.start();
            Graphics gg = panel.getGraphics();
            panel.paint(gg);
        });
        hiddenLayersBtn.setFont(myFont);
        hiddenLayersBtn.setBounds(280, 165, 300, 23);
        frame.getContentPane().add(hiddenLayersBtn);

        hiddenLayerTextField = new JTextField();
        hiddenLayerTextField.setText("4");
        hiddenLayerTextField
                .setToolTipText("Целые числа, через запятую, без пробелов");
        hiddenLayerTextField.setFont(myFont);
        hiddenLayerTextField.setBounds(307, 198, 243, 27);
        frame.getContentPane().add(hiddenLayerTextField);
        hiddenLayerTextField.setColumns(10);

        JButton pauseBtn = new JButton("Пауза");
        pauseBtn.setFont(myFont);
        pauseBtn.addActionListener(arg0 -> Variables.sleep.stop());

        pauseBtn.setBounds(379, 0, 89, 29);
        frame.getContentPane().add(pauseBtn);
        JButton saveNetworkBtn = new JButton("Сохранить ИИ");
        saveNetworkBtn.setFont(buttonsFont);
        saveNetworkBtn.addActionListener(arg0 -> {
            if (networkNamesComBox.getSelectedItem() != null && !networkNamesComBox.getSelectedItem().toString().equals("") && Variables.bestBird != null) {
                try {
                    Connection connection = Variables.con;
                    PreparedStatement pstmt = connection.prepareStatement(Panel.saveAI);
                    pstmt.setString(1, networkNamesComBox.getSelectedItem().toString());
                    pstmt.setObject(2, serialize(Variables.bestBird.brain));
                    pstmt.setObject(3, Variables.populationCount);
                    pstmt.setObject(4, Variables.maxColumnsPassed);
                    pstmt.executeUpdate();
                    int serialized_id = -1;
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        serialized_id = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();
                    System.out.println("Java object serialized to database. Object: "
                            + Variables.bestBird.brain + "serialized_id:" + serialized_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setNetworkNamesComBox(networkNamesComBox);
            }

        });

        saveNetworkBtn.setBounds(307, 230, 125, 25);
        frame.getContentPane().add(saveNetworkBtn);
        JButton restoreNetworkBtn = new JButton("Восстановить ИИ");
        restoreNetworkBtn.setFont(buttonsFont);
        restoreNetworkBtn.addActionListener(arg0 -> {
            if (networkNamesComBox.getSelectedItem() != null && !networkNamesComBox.getSelectedItem().toString().equals("")) {
                try {
                    Connection connection = Variables.con;
                    PreparedStatement pstmt = connection.prepareStatement(Panel.getAI);
                    pstmt.setString(1, networkNamesComBox.getSelectedItem().toString());
                    ResultSet rs = pstmt.executeQuery();
                    InputStream binaryStream = null;
                    if (rs.next()) {
                        binaryStream = rs.getBinaryStream(1);
                        Network dbNetwork = (Network) deserialize(binaryStream);
                        Variables.populationCount = rs.getInt(2)-1;
                        Variables.maxColumnsPassed = rs.getInt(3);
                        rs.close();
                        pstmt.close();
                        Variables.bestBird = new Bird();
                        Variables.bestBird.brain = dbNetwork;
                        Variables.hiddenLayers = dbNetwork.NETWORK_LAYER_SIZES;
                        Variables.clear();
                        Variables.generateFromBest();
                        Graphics gg = panel.getGraphics();
                        panel.paint(gg);
                    }

                } catch (Exception throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        restoreNetworkBtn.setBounds(435, 230, 155, 25);
        frame.getContentPane().add(restoreNetworkBtn);

        networkNamesComBox = new JComboBox();
        networkNamesComBox.setToolTipText("Введите название нового или существующего ИИ");
        networkNamesComBox.setBounds(307, 260, 243, 27);
        networkNamesComBox.setFont(myFont);
        networkNamesComBox.setEditable(true);
        frame.getContentPane().add(networkNamesComBox);

        setNetworkNamesComBox(networkNamesComBox);

        JButton startBTN = new JButton("Старт");
        startBTN.setFont(myFont);
        startBTN.addActionListener(e -> Variables.sleep.start());

        startBTN.setBounds(280, 0, 89, 29);
        frame.getContentPane().add(startBTN);

        JSlider populationSlider = new JSlider();
        populationSlider.setMinimum(2);
        populationSlider.setMaximum(3000);
        populationSlider.setBounds(10, 162, 243, 23);
        Variables.population = 500;
        populationSlider.setValue(Variables.population);
        MaxBirdNumbLbl.setText(Integer.toString(Variables.population));
        frame.getContentPane().add(populationSlider);
        populationSlider.addChangeListener(arg0 -> {
            Variables.population = populationSlider.getValue();
            MaxBirdNumbLbl.setText(Integer.toString(Variables.population));
        });

        JButton createSimulationBtn = new JButton(
                "Создать новую симуляцию");
        createSimulationBtn.setFont(myFont);
        createSimulationBtn.addActionListener(arg0 -> {
            Variables.populationCount = 0;
            Variables.bestBird = null;
            Variables.maxColumnsPassed = 0;
            Variables.newGame();
            Variables.sleep.start();
        });
        createSimulationBtn.setBounds(10, 0, 221, 27);
        frame.getContentPane().add(createSimulationBtn);

        JLabel populationLbl = new JLabel("Популяция №");
        populationLbl.setFont(myFont);
        populationLbl.setBounds(280, 38, 94, 18);
        frame.getContentPane().add(populationLbl);

        populationNumberLbl = new JLabel("");
        populationNumberLbl.setFont(myFont);
        populationNumberLbl.setBounds(384, 38, 89, 18);
        frame.getContentPane().add(populationNumberLbl);

        JLabel numberBirdsAliveLbl = new JLabel(
                "Число живых птиц:");
        numberBirdsAliveLbl.setFont(myFont);
        numberBirdsAliveLbl.setBounds(280, 67, 133, 25);
        frame.getContentPane().add(numberBirdsAliveLbl);

        JLabel obstaclesPassedLbl = new JLabel(
                "Препятствий пройдено: ");
        obstaclesPassedLbl.setFont(myFont);
        obstaclesPassedLbl.setBounds(280, 103, 170, 19);
        frame.getContentPane().add(obstaclesPassedLbl);

        JLabel maxObstaclesPassedLbl = new JLabel(
                "Максимально пройдено препятствий: ");
        maxObstaclesPassedLbl.setFont(myFont);
        maxObstaclesPassedLbl.setBounds(280, 138, 270, 25);
        frame.getContentPane().add(maxObstaclesPassedLbl);

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
        maxWeightChangeLbl.setBounds(255, 250, 60, 15);
        frame.getContentPane().add(maxWeightChangeLbl);

        panel = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.GRAY);
                Graphics2D gg = (Graphics2D) g;
                g.setColor(Color.GRAY);
                int width = panel.getWidth();
                int height = panel.getHeight();
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

        panel.setBounds(0, 301, 609, 325);
        frame.getContentPane().add(panel);
        GroupLayout groupLayout = new GroupLayout(panel);
        groupLayout
                .setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 536, Short.MAX_VALUE));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 270, Short.MAX_VALUE));
        panel.setLayout(groupLayout);

        JSlider chanceMutateSlider = new JSlider();
        chanceMutateSlider.setMaximum(99);
        chanceMutateSlider.addChangeListener(arg0 -> {
            chanceMutateLbl.setText(Double.toString(chanceMutateSlider.getValue()) + "%");
            Variables.chanceMutate = chanceMutateSlider.getValue();
        });

        chanceMutateSlider.setValue(10);
        chanceMutateSlider.setBounds(10, 212, 243, 23);
        Variables.chanceMutate = chanceMutateSlider.getValue();
        frame.getContentPane().add(chanceMutateSlider);

        JSlider maxWeightChangeSlider = new JSlider();
        maxWeightChangeSlider.setValue(10);
        Variables.maxWeightChange = maxWeightChangeSlider.getValue();
        maxWeightChangeLbl.setText(Variables.maxWeightChange + "%");
        maxWeightChangeSlider.addChangeListener(arg0 -> {
            maxWeightChangeLbl.setText(Variables.maxWeightChange + "%");
            Variables.maxWeightChange = maxWeightChangeSlider.getValue();
        });
        maxWeightChangeSlider.setBounds(10, 267, 243, 23);
        frame.getContentPane().add(maxWeightChangeSlider);
        JLabel chanceNeuronMutateLbl = new JLabel(
                "Вероятность мутации нейрона");
        chanceNeuronMutateLbl.setFont(myFont);
        chanceNeuronMutateLbl.setBounds(10, 187, 218, 23);
        frame.getContentPane().add(chanceNeuronMutateLbl);

        JLabel neuronMaxWeightDiff = new JLabel(
                "Максимальный сдвиг веса нейрона");
        neuronMaxWeightDiff.setFont(myFont);
        neuronMaxWeightDiff.setBounds(10, 246, 243, 20);
        frame.getContentPane().add(neuronMaxWeightDiff);


    }

    private static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return baos.toByteArray();
    }

    private static Object deserialize(InputStream stream) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(stream)) {
            Object obj = ois.readObject();
            return obj;
        }
    }

    private static void setNetworkNamesComBox(JComboBox networkNameComBox) {
        try {
            Connection connection = Variables.con;
            PreparedStatement pstmt = connection.prepareStatement(Panel.getAiNames);
            ResultSet rs = pstmt.executeQuery();
            networkNameComBox.removeAllItems();
            networkNameComBox.addItem("");
            while (rs.next()) {
                networkNameComBox.addItem(rs.getString(1));
            }
            rs.close();

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
