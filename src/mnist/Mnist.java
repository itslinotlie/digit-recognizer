package mnist;

import neuralnetwork.*;
import trainset.TrainSet;

import java.io.*;

public class Mnist {
    static final int INPUT_SIZE = 784, OUTPUT_SIZE = 10;
    static TrainSet createTrainSet(int start, int end) throws IOException {
        TrainSet set = new TrainSet(INPUT_SIZE, OUTPUT_SIZE);
        String path = new File("").getAbsolutePath();
        MnistLabel label = new MnistLabel(path+"/res/train-labels.idx1-ubyte", "r");
        MnistImage image = new MnistImage(path+"/res/train-images.idx3-ubyte", "r");

        if (start<0 || end>label.count)
            throw new RuntimeException("Range is out of bounds");

        //Skipping the data from [0, start)
        for (int i=0;i<start;i++) {
            label.readLabel(); image.readImage();
        }

        //Adding MNIST data into trainset
        for (int i=start;i<=end;i++) {
            double in[] = new double[784], out[] = new double[10];
            out[label.readLabel()]++;
            //Divide by 255 to scale the values to [0, 1] (should keep everything balanced)
            for (int j=0;j<INPUT_SIZE;j++)
                in[j] = image.readImage()/255d;
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

    public static void main(String[] args) throws IOException {
        Network net = new Network(INPUT_SIZE, 50, 30, 20, OUTPUT_SIZE);
        TrainSet set = createTrainSet(1, 100);
        TrainSet test = createTrainSet(101, 200);
        net.train(set, 100000, 100, 0.3);
        System.out.println("Same: "+accuracy(net, set));
        System.out.println("New: "+accuracy(net, test));
    }
}
