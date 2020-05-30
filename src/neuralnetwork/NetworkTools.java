package neuralnetwork;

public class NetworkTools {
    public static double[] createRndArray(int size, double lower_bound, double upper_bound) {
        double arr[] = new double[size];
        for (int i=0;i<size;i++)
            arr[i] = Math.random()*upper_bound + lower_bound;
        return arr;
    }

    public static double[][] createRndArray(int sizeY, int sizeX, double lower_bound, double upper_bound) {
        double arr[][] = new double[sizeY][sizeX];
        for (int i=0;i<sizeY;i++)
            arr[i] = createRndArray(sizeX, lower_bound, upper_bound);
        return arr;
    }
}
