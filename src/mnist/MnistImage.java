package mnist;

import java.io.*;

public class MnistImage extends MnistFile {
    private int row, col;
    public MnistImage(String name, String mode) throws IOException  {
        super(name, mode);
        row = readInt();
        col = readInt();
    }

    public double readImage() throws IOException {return readByte();}

    protected int magicNumber() {return 2051;}
}
