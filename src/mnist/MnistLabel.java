package mnist;

import java.io.*;

public class MnistLabel extends MnistFile {
    public MnistLabel(String name, String mode) throws IOException {
        super(name, mode);
    }

    public int readLabel() throws IOException {return readByte();}

    protected int magicNumber() {return 2049;}
}
