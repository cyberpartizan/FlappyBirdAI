import java.io.Serializable;
import java.util.Random;

public class Network implements Serializable {

    private final double[][] output;
    public double[][][] weights;
    private final double[][] bias;

    private final double[][] output_derivative;

    public final int[] NETWORK_LAYER_SIZES;
    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;
    public final int NETWORK_SIZE;

    public Network(int[] NETWORK_LAYER_SIZES) {
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE - 1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];

        this.output_derivative = new double[NETWORK_SIZE][];

        for (int i = 0; i < NETWORK_SIZE; i++) {
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], -0.5, 0.7);
            if (i > 0) {
                weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1], -1, 1);
            }
        }
    }

    public Network(Network other) {//Создание независимой копии нейросети
        this.output = NetworkTools.copy2d(other.output);
        this.weights = NetworkTools.copy3d(other.weights);
        this.bias = NetworkTools.copy2d(other.bias);
        this.output_derivative = NetworkTools.copy2d(other.output_derivative);

        this.NETWORK_LAYER_SIZES = NetworkTools.copy1dInt(other.NETWORK_LAYER_SIZES);
        this.INPUT_SIZE = other.INPUT_SIZE;
        this.OUTPUT_SIZE = other.OUTPUT_SIZE;
        this.NETWORK_SIZE = other.NETWORK_SIZE;
    }


    public double[] calculate(double... input) {
        if (input.length != this.INPUT_SIZE) return null;
        this.output[0] = input;
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
                double sum = bias[layer][neuron];
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    sum += output[layer - 1][prevNeuron] * weights[layer][neuron][prevNeuron];
                }
                output[layer][neuron] = sigmoid(sum);
                output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);
            }
        }return output[NETWORK_SIZE - 1]; }

    public void mutate(long seed) {//Мутация нейрона
        Random dice = new Random();
        dice.setSeed(seed);
        for (int layer = 1; layer < NETWORK_SIZE; layer++) {
            for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
                if (dice.nextDouble() < (Variables.chanceMutate / 100)) {
                    double gaussRandBiasDev = (101 - Variables.maxWeightChange) / 100;
                    double gaussRandBias = dice.nextGaussian() * gaussRandBiasDev;
                    bias[layer][neuron] += gaussRandBias;
                }
                if (bias[layer][neuron] > 1) {
                    bias[layer][neuron] = 1;
                } else if (bias[layer][neuron] < -1) {
                    bias[layer][neuron] = -1;
                }
                for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
                    if (dice.nextDouble() < (Variables.chanceMutate / 100)) {
                        double gaussRandWeight = dice.nextGaussian() * ((101 - Variables.maxWeightChange) / 100);
                        weights[layer][neuron][prevNeuron] += gaussRandWeight;
                    }

                    if (weights[layer][neuron][prevNeuron] > 1) {
                        weights[layer][neuron][prevNeuron] = 1;
                    } else if (weights[layer][neuron][prevNeuron] < -1) {
                        weights[layer][neuron][prevNeuron] = -1;
                    }

                }
            }
        }
    }

    private double sigmoid(double x) { return 1d / (1 + Math.exp(-x)); }

}
