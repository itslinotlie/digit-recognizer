package neuralnetwork;

import trainset.TrainSet;

import java.util.*;
import java.io.*;

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
        if (set.INPUT_SIZE!=INPUT_SIZE || set.OUTPUT_SIZE!=OUTPUT_SIZE) return;
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

    //testing
    public static void main(String[] args) throws IOException {
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

        net.saveNetwork(net, path);
        Network test = new Network(4, 3, 2, 2);
        if(matchesFile(test, path)) test = test.loadNetwork(path);
        for (int i=0;i<set.size();i++) {
            System.out.println(Arrays.toString(test.calculate(set.getInput(i))));
        }
    }

    static String path = new File("").getAbsolutePath()+"/res/network.txt";
    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    public static boolean matchesFile(Network net, String path) throws IOException {
        if (!new java.io.File(path).exists()) return false;
        br = new BufferedReader(new FileReader(path));
        int arr[] = new int[readInt()];
        for (int i=0;i<arr.length;i++) {
            if (readInt()!=net.NETWORK_LAYER[i]) return false;
        } return true;
    }

    public static Network loadNetwork(String path) throws IOException {
        br = new BufferedReader(new FileReader(path));
        int arr[] = new int[readInt()];
        for (int i=0;i<arr.length;i++) arr[i] = readInt();
        Network net = new Network(arr);
        for (int layer=1;layer<net.NETWORK_LAYER_SIZE;layer++) {
            for (int neuron=0;neuron<net.NETWORK_LAYER[layer];neuron++) {
                for (int prevNeuron=0;prevNeuron<net.NETWORK_LAYER[layer-1];prevNeuron++) {
                    net.weight[layer][prevNeuron][neuron] = readDouble();
                }
            }
            for (int neuron=0;neuron<net.NETWORK_LAYER[layer];neuron++) {
                net.bias[layer][neuron] = readDouble();
            }
        }
        return net;
    }

    public static void saveNetwork(Network net, String path) throws IOException {
        pw = new PrintWriter(new BufferedWriter(new FileWriter(path)));
        pw.println(net.NETWORK_LAYER_SIZE);
        for (int i=0;i<net.NETWORK_LAYER_SIZE;i++) {
            pw.print(i==net.NETWORK_LAYER_SIZE-1? net.NETWORK_LAYER[i]+"\n":net.NETWORK_LAYER[i]+" ");
        }
        for (int layer=1;layer<net.NETWORK_LAYER_SIZE;layer++) {
            for (int neuron=0;neuron<net.NETWORK_LAYER[layer];neuron++) {
                for (int prevNeuron=0;prevNeuron<net.NETWORK_LAYER[layer-1];prevNeuron++) {
                    pw.print(prevNeuron==net.NETWORK_LAYER[layer-1]-1? net.weight[layer][prevNeuron][neuron]+"\n":net.weight[layer][prevNeuron][neuron]+" ");
                }
            }
            for (int neuron=0;neuron<net.NETWORK_LAYER[layer];neuron++) {
                pw.print(neuron==net.NETWORK_LAYER[layer]-1? net.bias[layer][neuron]+"\n":net.bias[layer][neuron]+" ");
            }
        }
        pw.close();
    }

    static String next() throws IOException {
        while(st==null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine().trim());
        return st.nextToken();
    }
    static int readInt() throws IOException {return Integer.parseInt(next());}
    static double readDouble() throws IOException {return Double.parseDouble(next());}
}
