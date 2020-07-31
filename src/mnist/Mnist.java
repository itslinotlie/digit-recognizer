package mnist;

import gui.MnistImageGUI;
import neuralnetwork.*;
import trainset.TrainSet;

import java.io.*;
import java.util.Arrays;

public class Mnist {
    static final int INPUT_SIZE = 784, OUTPUT_SIZE = 10;
    static TrainSet createTrainSet(int start, int end, String lbl, String img) throws IOException {
        TrainSet set = new TrainSet(INPUT_SIZE, OUTPUT_SIZE);
        String path = new File("").getAbsolutePath();
        MnistLabel label = new MnistLabel(path+lbl, "r");
        //Normal digits
        MnistImage image = new MnistImage(path+img, "r");
        //Modified digits to be b/w only
//        MnistImageGUI image = new MnistImageGUI(img);

        //Skipping the data from [0, start)
        for (int i=0;i<start;i++) {
            label.readLabel(); image.readImage();
        }

        //Adding MNIST data into trainset
        for (int i=start;i<=end;i++) {
            double in[] = new double[INPUT_SIZE], out[] = new double[OUTPUT_SIZE];
            in = image.readImage();
            out[label.readLabel()]++;
            set.addData(in, out);
        }
        return set;
    }

    static String accuracy(Network net, TrainSet set) {
        double top = 0, bot = 0;
        for (int i=0;i<set.size();i++) {
            int actual = NetworkTools.getBestId(net.calculate(set.getInput(i)));
            int target = NetworkTools.getBestId(set.getOutput(i));
            if (actual==target) top++;
            bot++;
        }
        return "Top: "+top+" Bot: "+bot+" Total: "+top/bot;
    }

    static void trainData(Network net, TrainSet set, TrainSet test, int epochs, int loops, int batch_size, double eta, String path) throws IOException {
        for (int i=1;i<=epochs;i++) {
            net.train(set, loops, batch_size, eta);
            System.out.println("<<<<<"+i+">>>>>");
            System.out.println("Set: "+accuracy(net, set));
            System.out.println("Test: "+accuracy(net, test));
            net.saveNetwork(net, path);
        }
    }
    public static void main(String[] args) throws IOException {
        Network net = new Network(784, 75, 30, 10);
        //Change the path to switch between training either models (remember to change createTrainSet settings as well)
        String path = new File("").getAbsolutePath()+"/res/gui-network.txt";
        if (net.matchesFile(net, path)) net = net.loadNetwork(path);
        TrainSet set = createTrainSet(0, 59999, "/res/train-labels.idx1-ubyte", "/res/train-images.idx3-ubyte");
        System.out.println("Set is done loading");
        TrainSet test = createTrainSet(0, 9999, "/res/t10k-labels.idx1-ubyte", "/res/t10k-images.idx3-ubyte");
        System.out.println("Test is done loading");
        trainData(net, set, test, 1000, 5, 60000, 0.3, path);
    }
}
