package neuralnetwork;

import java.util.*;

public class NetworkTools {
    public static double[] createRndArray(int size, double lower_bound, double upper_bound) {
        double arr[] = new double[size];
        for (int i=0;i<size;i++)
            arr[i] = Math.random()*(upper_bound-lower_bound) + lower_bound;
        return arr;
    }

    public static double[][] createRndArray(int sizeY, int sizeX, double lower_bound, double upper_bound) {
        double arr[][] = new double[sizeY][sizeX];
        for (int i=0;i<sizeY;i++)
            arr[i] = createRndArray(sizeX, lower_bound, upper_bound);
        return arr;
    }

    public static int[] createRndIDArray(int size, int upper_bound) {
        List<Integer> values = new ArrayList();
        int arr[] = new int[size];
        for (int i=0;i<upper_bound;i++) values.add(i);
        Collections.shuffle(values);
        for (int i=0;i<size;i++) arr[i] = values.get(i);
        return arr;
    }

    public static int getBestId(double[] arr) {
        double max = arr[0]; int id = 0;
        for (int i=1;i<arr.length;i++) {
            if(arr[i]>max) {
                max = arr[i];
                id = i;
            }
        }
        return id;
    }
}
