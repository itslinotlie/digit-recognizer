package gui;

import mnist.MnistImage;

import java.io.File;
import java.io.IOException;

public class MnistImageGUI  {
    public String path = new File("").getAbsolutePath(), train = "/res/train-images.idx3-ubyte", test = "/res/t10k-images.idx3-ubyte";
    public String tag;
    MnistImage img;
    public MnistImageGUI(String x) throws IOException {
        if(x.equals(train)) tag = train;
        else if (x.equals(test)) tag = test;
        else {System.out.print("ERROR"); return;}
        img = new MnistImage(path+tag, "r");
    }
    public double[] readImage() throws IOException {
        double arr[] = new double[784];
        for (int i=0;i<28;i++) {
            for (int j=0;j<28;j++) {
                arr[i*28+j] = img.read()==0? 0:1;
            }
        }
        return arr;
    }
    public void prev() throws IOException {
        if(getFilePointer()-784>=16)
            img.seek(getFilePointer()-784);
    }
    public boolean hasNext() throws IOException {
        return tag.equals("/res/train-images.idx3-ubyte")? getFilePointer()<=47040016:getFilePointer()<=7840016;
    }
    public boolean hasPrev() throws IOException {
        return getFilePointer()-getLength()>=getLength()+getHeader();
    }
    public long getFilePointer() throws IOException {return img.getFilePointer();}
    public int getHeader() {return 16;}
    public int getLength() {return 784;}
}
