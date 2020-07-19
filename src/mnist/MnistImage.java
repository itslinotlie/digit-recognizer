package mnist;

import java.io.*;

public class MnistImage extends MnistFile {
    private int row, col;
    public MnistImage(String name, String mode) throws IOException  {
        super(name, mode);
        row = readInt();
        col = readInt();
    }

    public double[] readImage() throws IOException {
        double arr[] = new double[row*col];
        //Divide by 255 to scale the values to [0, 1] (should keep everything balanced)
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
                arr[i*row+j] = read()/255d;
            }
        }
        return arr;
    }

    protected int magicNumber() {return 2051;}
}
