package trainset;

import neuralnetwork.NetworkTools;

import java.util.*;

//This class allows you to easily scale your training data
//to properly train your network (in preparations for MNIST)
public class TrainSet {
    public final int INPUT_SIZE, OUTPUT_SIZE;
    private List<double[][]> data = new ArrayList();

    public TrainSet(int INPUT_SIZE, int OUTPUT_SIZE) {
        this.INPUT_SIZE = INPUT_SIZE;
        this.OUTPUT_SIZE = OUTPUT_SIZE;
    }

    //Provides the option for "Mini-batch gradiennt descent"
    public TrainSet extractBatch(int amt) {
        if(0>=amt || amt>data.size()) return this;
        TrainSet x = new TrainSet(INPUT_SIZE, OUTPUT_SIZE);
        int rnd[] = NetworkTools.createRndIDArray(amt, data.size());
        for (int i:rnd) x.addData(getInput(i), getOutput(i));
        return x;
    }

    //For testing purposes
    public String toString() {
        String x = "Size: "+data.size()+" | In: "+INPUT_SIZE+"  Out: "+OUTPUT_SIZE+"\n";
        for (int i=0;i<data.size();i++)
            x+=Arrays.toString(getInput(i))+" | "+Arrays.toString(getOutput(i))+"\n";
        return x;
    }

    //Testing functions
    public static void main(String[] args) {
        TrainSet set = new TrainSet(3, 2);
        for (int i=1;i<=8;i++) {
            double[] in = new double[3], out = new double[2];
            for (int j=0;j<3;j++) {
                if (j<2) out[j] = (int)(Math.random()*10) / 10d;
                in[j] = (int)(Math.random()*10) / 10d;
            }
            set.addData(in, out);
        }
        System.out.println(set);
        System.out.println(set.extractBatch(4));
    }

    public void addData(double in[], double out[]) {data.add(new double[][]{in, out});}

    public double[] getInput(int id) {return data.get(id)[0];}

    public double[] getOutput(int id) {return data.get(id)[1];}

    public int size() {return data.size();}
}
