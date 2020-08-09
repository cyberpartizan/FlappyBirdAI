
import java.util.Random;

public class Network {

	private double[][] output;
	public double[][][] weights;
	private double[][] bias;

	private double[][] error_signal;
	private double[][] output_derivative;

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

		this.error_signal = new double[NETWORK_SIZE][];
		this.output_derivative = new double[NETWORK_SIZE][];

		for (int i = 0; i < NETWORK_SIZE; i++) {
			this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.error_signal[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];

			this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], -0.5, 0.7);

			if (i > 0) {
				weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i - 1], -1, 1);
			}
		}
	}

	/*
	 * public Network(Network net) { this.INPUT_SIZE = net.INPUT_SIZE;
	 * this.NETWORK_LAYER_SIZES = net.NETWORK_LAYER_SIZES; this.NETWORK_SIZE =
	 * net.NETWORK_SIZE; this.OUTPUT_SIZE = net.OUTPUT_SIZE;
	 * 
	 * this.output = net.output; this.weights = net.weights; this.bias = net.bias;
	 * 
	 * this.error_signal = net.error_signal; this.output_derivative =
	 * net.output_derivative;
	 * 
	 * for (int i = 0; i < NETWORK_SIZE; i++) {
	 * 
	 * this.output[i] = new double[NETWORK_LAYER_SIZES[i]]; this.error_signal[i] =
	 * new double[NETWORK_LAYER_SIZES[i]]; this.output_derivative[i] = new
	 * double[NETWORK_LAYER_SIZES[i]];
	 * 
	 * this.bias[i] = net.bias[i];
	 * 
	 * if (i > 0) { this.weights[i] = net.weights[i]; } } }
	 */
	public Network(Network other) {
		this.output = copy2d(other.output);
		this.weights = copy3d(other.weights);
		this.bias = copy2d(other.bias);
		this.error_signal = copy2d(other.error_signal);
		this.output_derivative = copy2d(other.output_derivative);

		this.NETWORK_LAYER_SIZES = copy1dInt(other.NETWORK_LAYER_SIZES);
		this.INPUT_SIZE = other.INPUT_SIZE;
		this.OUTPUT_SIZE = other.OUTPUT_SIZE;
		this.NETWORK_SIZE = other.NETWORK_SIZE;
	}

	private static double[][][] copy3d(double[][][] original) {
		double[][][] copy = new double[original.length][][];
		for (int i = 0; i < original.length; i++) {
			if (i > 0) {
				copy[i] = copy2d(original[i]);
			}
		}
		return copy;
	}

	private static double[][] copy2d(double[][] original) {
		double[][] copy = new double[original.length][];
		for (int i = 0; i < original.length; i++) {
			copy[i] = copy1d(original[i]);
		}
		return copy;
	}

	private static double[] copy1d(double[] original) {
		int length = original.length;
		double[] copy = new double[length];
		System.arraycopy(original, 0, copy, 0, length);
		return copy;
	}

	private static int[] copy1dInt(int[] original) {
		int length = original.length;
		int[] copy = new int[length];
		System.arraycopy(original, 0, copy, 0, length);
		return copy;
	}

	public double[] calculate(double... input) {
		if (input.length != this.INPUT_SIZE)
			return null;
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
		}
		return output[NETWORK_SIZE - 1];
	}

	/*
	 * public void train(double[] input, double[] target, double eta) { if
	 * (input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) return;
	 * calculate(input); backpropError(target); updateWeights(eta); }
	 */

	/*
	 * public void backpropError(double[] target) { for (int neuron = 0; neuron <
	 * NETWORK_LAYER_SIZES[NETWORK_SIZE - 1]; neuron++) { error_signal[NETWORK_SIZE
	 * - 1][neuron] = (output[NETWORK_SIZE - 1][neuron] - target[neuron])
	 * output_derivative[NETWORK_SIZE - 1][neuron]; } for (int layer = NETWORK_SIZE
	 * - 2; layer > 0; layer--) { for (int neuron = 0; neuron <
	 * NETWORK_LAYER_SIZES[layer]; neuron++) { double sum = 0; for (int nextNeuron =
	 * 0; nextNeuron < NETWORK_LAYER_SIZES[layer + 1]; nextNeuron++) { sum +=
	 * weights[layer + 1][nextNeuron][neuron] * error_signal[layer + 1][nextNeuron];
	 * } this.error_signal[layer][neuron] = sum * output_derivative[layer][neuron];
	 * } } }
	 */

	/*
	 * public void updateWeights(double eta) { for (int layer = 1; layer <
	 * NETWORK_SIZE; layer++) { for (int neuron = 0; neuron <
	 * NETWORK_LAYER_SIZES[layer]; neuron++) {
	 * 
	 * double delta = -eta * error_signal[layer][neuron]; bias[layer][neuron] +=
	 * delta;
	 * 
	 * for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1];
	 * prevNeuron++) { weights[layer][neuron][prevNeuron] += delta * output[layer -
	 * 1][prevNeuron]; } } } }
	 */

	public void mutate(double eta, long seed) {
		Random dice = new Random();
		dice.setSeed(seed);
		for (int layer = 1; layer < NETWORK_SIZE; layer++) {
			for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
				if (dice.nextDouble() < (Variables.chancemutate/100)) {
					double gausrandbiasdev=(101 - Variables.maxweightchage)/100;
					double gausrandbias = dice.nextGaussian() * gausrandbiasdev;
					bias[layer][neuron] += gausrandbias;
				}
				if (bias[layer][neuron] > 1) {
					bias[layer][neuron] = 1;
				} else if (bias[layer][neuron] < -1) {
					bias[layer][neuron] = -1;
				}
				for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
					if (dice.nextDouble() < (Variables.chancemutate/100)) {
						double gausrandweight = dice.nextGaussian() * ((101 - Variables.maxweightchage)/100);
						weights[layer][neuron][prevNeuron] += gausrandweight;
					}

					if (weights[layer][neuron][prevNeuron] > 1) {
						weights[layer][neuron][prevNeuron] = 1;
					} else if (weights[layer][neuron][prevNeuron] < -1) {
						weights[layer][neuron][prevNeuron] = -1;
					}

				}
			}
		}
		System.out.println("");
	}

	private double sigmoid(double x) {
		return 1d / (1 + Math.exp(-x));
	}

	/*
	 * public static void main(String[] args) { Network net = new Network(2, 5, 1);
	 * Random dice = new Random(); int select; double[][] input = new double[][] { {
	 * 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }; double[][] output = new double[][] {
	 * { 0 }, { 1 }, { 1 }, { 0 } }; for (int i = 0; i < 10000; i++) { select =
	 * dice.nextInt(4); net.train(input[select], output[select], 0.5); } for (int i
	 * = 0; i < 4; i++) {
	 * System.out.println(Arrays.toString(net.calculate(input[i]))); } }
	 */

}
