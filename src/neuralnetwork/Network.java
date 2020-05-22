package neuralnetwork;

import java.util.*;

public class Network {
    public final int[] NETWORK_LAYER;
    public final int NETWORK_LAYER_SIZE, INPUT_SIZE, OUTPUT_SIZE;

    public double[][] bias, output;
    public double[][][] weight;

    //Arrays are 0-indexed
    public Network(int... NETWORK_LAYER) {
        this.NETWORK_LAYER = NETWORK_LAYER;
        NETWORK_LAYER_SIZE = NETWORK_LAYER.length;
        INPUT_SIZE = NETWORK_LAYER[0];
        OUTPUT_SIZE = NETWORK_LAYER[NETWORK_LAYER_SIZE-1];

        bias = new double[NETWORK_LAYER_SIZE][];
        output = new double[NETWORK_LAYER_SIZE][];
        weight = new double[NETWORK_LAYER_SIZE][][];
        for (int layer=0;layer<NETWORK_LAYER_SIZE;layer++) {
            bias[layer] = new double[NETWORK_LAYER[layer]];
            output[layer] = new double[NETWORK_LAYER[layer]];
            if (layer!=0) weight[layer] = new double[NETWORK_LAYER[layer-1]][NETWORK_LAYER[layer]];
        }
    }

    public double[] calculate(double... input) {
        if (input.length != INPUT_SIZE) return null;
        output[0] = input;
        for (int layer=1;layer<NETWORK_LAYER_SIZE;layer++) {
            for (int neuron=0;neuron<NETWORK_LAYER[layer];neuron++) {
                double sum = bias[layer][neuron];
                for (int prevNeuron=0;prevNeuron<NETWORK_LAYER[layer-1];prevNeuron++) {
                    sum+=output[layer-1][prevNeuron]*weight[layer][prevNeuron][neuron];
                }
                output[layer][neuron] = sigmoid(sum);
            }
        }
        return output[NETWORK_LAYER_SIZE-1];
    }

    public double sigmoid(double x) {return 1d/(1+Math.exp(-x));}

    //testing
    public static void main(String[] args) {
        Network net = new Network(3, 2, 2);
        System.out.println(Arrays.toString(net.calculate(0.3, 0.6, 0.9)));
    }
}
