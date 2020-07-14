package mnist;

import java.io.*;

public abstract class MnistFile extends RandomAccessFile {
    public int count;
    //name==file path, mode={r, rw} where r==read && w==write
    public MnistFile(String name, String mode) throws IOException {
        super(name, mode);
        if (readInt()!=magicNumber())
            throw new RuntimeException("Wrong file. Number should have been "+magicNumber());
        count = readInt();
    }

    //every MNIST file-type contains a unique magic number
    protected abstract int magicNumber();
}
