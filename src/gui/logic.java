package gui;

import neuralnetwork.Network;
import neuralnetwork.NetworkTools;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class logic {
    public final int MARGIN = 80, GRID_SIZE = 20;
    public boolean draw=true, drag=false;
    public String train = "/res/train-images.idx3-ubyte", test = "/res/t10k-images.idx3-ubyte";
    public Timer timer;

    public logicDisplay info;
    public MnistImageGUI brain;
    public static Network net = new Network(784, 75, 30, 10);

    public logic() throws IOException {
        setupLogic("gui-network.txt");
    }
    public void setupLogic(String file) throws IOException {
        String path = new File("").getAbsolutePath()+"/res/"+file;
        if (net.matchesFile(net, path)) net = net.loadNetwork(path);
        brain = new MnistImageGUI(train);
        info = new logicDisplay(2);
    }
    public void draw(JLabel lbl) {
        int m[][] = {{0,-1},{-1,0},{0,1},{1,0}};
        int r = lbl.getY()/20, c = lbl.getX()/20, d = 0;
        int lim = 1, stp = 0, turn = 0, size = info.getBRUSH_SIZE();
        display.grid[r][c].setBackground(draw? Color.BLACK:Color.WHITE);
        for (int i=0;i<size;i++) {
            if(stp<lim) {
                r+=m[d][0]; c+=m[d][1];
                int y=r,x=c;
                if(y>27) y = 27; if(y<0) y = 0;
                if(x>27) x = 27; if(x<0) x = 0;
                display.grid[y][x].setBackground(draw? Color.BLACK:Color.WHITE);
                stp++;
            } else {
                d = (d+1)%4;
                turn++; stp = 0;
                if(turn==2) {lim++;turn=0;}
                i--;
            }
        }
    }
    public void createGridAction(JLabel x) {
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {drag = true; draw(x);}
            @Override
            public void mouseReleased(MouseEvent e) {drag = false;}
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!drag) return;
                draw(x);
            }
            @Override
            public void mouseExited(MouseEvent e) {}
        };
        MouseMotionListener mml = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {drag = true;}
            @Override
            public void mouseMoved(MouseEvent e) {}
        };
        x.addMouseListener(ml);
        x.addMouseMotionListener(mml);
    }
    public void createLabelAction(JLabel x) {
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                //increments of 2*MARGIN
                switch(x.getY()) {
                    //Draw
                    case MARGIN:
                        draw = true;
                        display.img[3].setVisible(true);
                        display.img[4].setVisible(false);
                        return;
                    //Erase
                    case 3*MARGIN:
                        draw = false;
                        display.img[3].setVisible(false);
                        display.img[4].setVisible(true);
                        return;
                    //Clear
                    case 5*MARGIN:
                        draw = true;
                        display.img[3].setVisible(true);
                        display.img[4].setVisible(false);
                        clearGrid();
                        startTimer(500, display.img[5]);
                        return;
                }
                //increments of 7*GRID_SIZE
                switch ((x.getX())) {
                    //Validate and display number
                    case GRID_SIZE:
                        hideDigit();
                        int val = getDigit();
                        display.digit[val].setVisible(true);
                        startTimer(500, display.img[11]);
                        return;
                    //Show previous MNSIT number
                    case 15*GRID_SIZE:
                        try {
                            brain.prev(); brain.prev();
                            showGrid(brain.readImage());
                            display.img[13].setVisible(brain.hasPrev()? true:false);
                            display.img[14].setVisible(brain.hasNext()? true:false);
                        } catch (IOException ioE) {System.out.println("IO Error");}
                        return;
                    //Show next MNSIT number
                    case 22*GRID_SIZE:
                        try {
                            showGrid(brain.readImage());
                            display.img[13].setVisible(brain.hasPrev()? true:false);
                            display.img[14].setVisible(brain.hasNext()? true:false);
                        } catch (IOException ioE) {System.out.println("IO Error");}
                        return;
                }
                //Logic Display
                startTimer(500, display.img[15]);
                info.setVisible(true);
                return;
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };
        x.addMouseListener(ml);
    }
    public void hideDigit() {
        for (int i=0;i<10;i++) {
            display.digit[i].setVisible(false);
        }
    }
    public int getDigit() {
        //Commented out lines --> Valuable insight in how number prediction works
//        System.out.println("ans: "+NetworkTools.getBestId(net.calculate(getGrid())));
//        System.out.println(Arrays.toString(net.calculate(getGrid())));
        return NetworkTools.getBestId(net.calculate(getGrid()));
    }
    public void showGrid(double[] x) {
        for (int i=0;i<28;i++) {
            for (int j=0;j<28;j++) {
                display.grid[i][j].setBackground(x[i*28+j]==0? Color.WHITE:Color.BLACK);
            }
        }
    }
    public void clearGrid() {
        for (int i=0;i<28;i++) {
            for (int j=0;j<28;j++) {
                display.grid[i][j].setBackground(Color.WHITE);
            }
        }
    }
    public double[] getGrid() {
        double arr[] = new double[784];
        for (int i=0;i<28;i++) {
            for (int j=0;j<28;j++) {
                arr[i*28+j] = display.grid[i][j].getBackground().equals(Color.WHITE)? 0:1;
            }
        }
        return arr;
    }
    public void startTimer(int x, JLabel lbl) {
        timer = new Timer(x, e -> {
            lbl.setVisible(false);
            timer.stop();
            return;
        });
        lbl.setVisible(true);
        timer.start();
    }
}