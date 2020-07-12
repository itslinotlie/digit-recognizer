package neuralnetwork;

import trainset.TrainSet;

import java.util.*;

public class Network {
    public final int[] NETWORK_LAYER;
    public final int NETWORK_LAYER_SIZE, INPUT_SIZE, OUTPUT_SIZE;

    public double[][] bias, output;
    public double[][][] weight;

    public double[][] output_deri, delta;

    //Arrays are 0-indexed
    public Network(int... NETWORK_LAYER) {
        this.NETWORK_LAYER = NETWORK_LAYER;
        NETWORK_LAYER_SIZE = NETWORK_LAYER.length;
        INPUT_SIZE = NETWORK_LAYER[0];
        OUTPUT_SIZE = NETWORK_LAYER[NETWORK_LAYER_SIZE-1];

        bias = new double[NETWORK_LAYER_SIZE][];
        output = new double[NETWORK_LAYER_SIZE][];
        weight = new double[NETWORK_LAYER_SIZE][][];

        output_deri = new double[NETWORK_LAYER_SIZE][]; // ak(1-ak)
        delta = new double[NETWORK_LAYER_SIZE][]; // (ak-tk) * output_deri || output_deri * ∑delta*weight

        //Bias + Output values should be modified to the point
        //where it is possible to find the global minimum (cost function)
        for (int layer=0;layer<NETWORK_LAYER_SIZE;layer++) {
            bias[layer] = NetworkTools.createRndArray(NETWORK_LAYER[layer], -1, 1);
            output[layer] = new double[NETWORK_LAYER[layer]];
            output_deri[layer] = new double[NETWORK_LAYER[layer]];
            delta[layer] = new double[NETWORK_LAYER[layer]];
            if (layer!=0) weight[layer] = NetworkTools.createRndArray(NETWORK_LAYER[layer-1], NETWORK_LAYER[layer], -1, 1);
        }
    }

    public void train(TrainSet set, int loops, int batch_size, double eta) {
        if (set.getINPUT_SIZE()!=INPUT_SIZE || set.getOUTPUT_SIZE()!=OUTPUT_SIZE) return;
        for (int i=1;i<=loops;i++) {
            TrainSet x = set.extractBatch(batch_size);
            for (int batch=0;batch<batch_size;batch++) {
                train(x.getInput(batch), x.getOutput(batch), eta);
            }
        }
    }

    public void train(double[] input, double[] target, double eta) {
        if (input.length!=INPUT_SIZE || target.length!=OUTPUT_SIZE) return;
        calculate(input);
        backPropagate(target);
        update(eta);
    }

    public double[] calculate(double... input) {
        if (input.length != INPUT_SIZE) return null;
        output[0] = input;
        //layer = 0 is input layer, so feed-forward starts at layer = 1
        for (int layer=1;layer<NETWORK_LAYER_SIZE;layer++) {
            for (int neuron=0;neuron<NETWORK_LAYER[layer];neuron++) {
                double sum = bias[layer][neuron];
                for (int prevNeuron=0;prevNeuron<NETWORK_LAYER[layer-1];prevNeuron++) {
                    sum += output[layer-1][prevNeuron]*weight[layer][prevNeuron][neuron];
                }
                output[layer][neuron] = sigmoid(sum);
                output_deri[layer][neuron] = output[layer][neuron]*(1-output[layer][neuron]);
            }
        }
        return output[NETWORK_LAYER_SIZE-1];
    }

    public void backPropagate(double[] target) {
        if (target.length != OUTPUT_SIZE) return;
        //Output Neuron Calculations
        for (int neuron=0;neuron<NETWORK_LAYER[NETWORK_LAYER_SIZE-1];neuron++) {
            delta[NETWORK_LAYER_SIZE-1][neuron] = (output[NETWORK_LAYER_SIZE-1][neuron]-target[neuron])
                    *output_deri[NETWORK_LAYER_SIZE-1][neuron];
        }
        //Hidden Neuron Calculations
        for (int layer=NETWORK_LAYER_SIZE-2;layer>0;layer--) {
            for (int neuron=0;neuron<NETWORK_LAYER[layer];neuron++) {
                double sum = 0;
                for (int nextNeuron=0;nextNeuron<NETWORK_LAYER[layer+1];nextNeuron++) {
                    sum += delta[layer+1][nextNeuron]*weight[layer+1][neuron][nextNeuron];
                }
                delta[layer][neuron] = sum*output_deri[layer][neuron];
            }
        }
    }

    public void update(double eta) {
        for (int layer=1;layer<NETWORK_LAYER_SIZE;layer++)  {
            for (int neuron=0;neuron<NETWORK_LAYER[layer];neuron++) {
                for (int prevNeuron=0;prevNeuron<NETWORK_LAYER[layer-1];prevNeuron++) {
                    weight[layer][prevNeuron][neuron] += -eta*delta[layer][neuron] * output[layer-1][prevNeuron];
                }
                bias[layer][neuron] += -eta*delta[layer][neuron];
            }
        }
    }

    public double sigmoid(double x) {return 1d/(1+Math.exp(-x));}

    //updated testing
    public static void main(String[] args) {
        Network net = new Network(4, 3, 2, 2);
        TrainSet set = new TrainSet(4, 2);

        set.addData(new double[]{0.1, 0.2, 0.3, 0.6}, new double[]{0.1, 0.9});
        set.addData(new double[]{0.9, 0.8, 0.7, 0.4}, new double[]{0.9, 0.1});
        set.addData(new double[]{0.3, 0.7, 0.5, 0.2}, new double[]{0.2, 0.6});
        set.addData(new double[]{0.9, 0.1, 0.6, 0.0}, new double[]{0.9, 0.8});

        net.train(set, 100000, 4, 0.3);
        for (int i=0;i<set.size();i++) {
            System.out.println(Arrays.toString(net.calculate(set.getInput(i))));
        }

    }

    //testing
//    public static void main(String[] args) {
//        Network net = new Network(3, 3, 2);
//        double[] input = {0.3, 0.6, 0.9};
//        double[] target = {0, 1};
//        for (int i=0;i<100000;i++) {
//            //eta/learning rate usually goes up/down in increments of a factor of 3
//            net.train(input, target, 0.3);
//        }
//    }
}
