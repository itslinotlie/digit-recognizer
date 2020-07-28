package gui;

import java.io.*;
import java.util.*;

import neuralnetwork.Network;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class display extends JFrame {
    public int WIDTH=700, HEIGHT=700, GRID_SIZE=20, MARGIN=75;
    public String path = new File("").getAbsolutePath()+"/res/static/";
    public boolean draw=true, click=false, drag=false;

    public Network net = new Network(784, 75, 30, 10);

    public JPanel bg = new JPanel();
    public JLabel grid[][] = new JLabel[28][28], img[] = new JLabel[14];
    public ImageIcon icon[] = new ImageIcon[14];
    public Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    public display() {
        super("I'll take your #");
        setupFrame();
        setupPanel();
        setupLabel();
        repaint();
    }
    public void setupFrame() {
//        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);
    }
    public void setupPanel() {
        bg.setLayout(null);
        bg.setBackground(Color.lightGray);
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        add(bg);
    }
    public void setupLabel() {
        //the 28x28 grid
        for (int i=0;i<28;i++) {
            for (int j=0;j<28;j++) {
                grid[i][j] = new JLabel();
                grid[i][j].setOpaque(true);
                grid[i][j].setBackground(Color.WHITE);
                grid[i][j].setBounds(j*GRID_SIZE, i*GRID_SIZE, GRID_SIZE, GRID_SIZE);
                grid[i][j].setBorder(border);
                bg.add(grid[i][j]);
            }
        }
        //right bar 0 | (pencil, eraser, bin) --> 3 | (highlights)
        for (int i=0;i<6;i++) {
            createLabelInfo(i);
            int x = WIDTH-(int)(1.35*icon[i].getIconWidth()), y = MARGIN*(1+2*i);
            int x2 = x+5, y2 = y-6*MARGIN+5;
            int w = icon[i].getIconWidth(), h = icon[i].getIconHeight();
            img[i].setBounds(i>2? x2:x, i>2? y2:y, w, h);
        }
        //bottom bar 6 | (validate, left arrow, right arrow, bulb) --> 10 | (highlights)
        for (int i=6;i<14;i++) {
            createLabelInfo(i);
            int x = MARGIN*(-11+2*i), y = HEIGHT-(int)(1.45*icon[i].getIconHeight());
            int x2 = x-8*MARGIN+5, y2 = y+3;
            int w = icon[i].getIconWidth(), h = icon[i].getIconHeight();
            if(i==11) {x2-=15; y2-=3;}
            if(i==12) {x2+=5; y2-=3;}
            if(i==9) x+=50;
            if(i==13) x2+=50;
            img[i].setBounds(i>9? x2:x, i>9? y2:y, w, h);
        }
    }
    public void createLabelInfo(int x) {
        System.out.println(x);
        img[x] = new JLabel();
        icon[x] = new ImageIcon(getPath(x));
        Image scaleIcon = icon[x].getImage().getScaledInstance(icon[x].getIconWidth()*3/8, icon[x].getIconHeight()*3/8, Image.SCALE_DEFAULT);
        icon[x] = new ImageIcon(scaleIcon);
        img[x].setIcon(icon[x]);
        bg.add(img[x]);
    }
    public String getPath(int x) {
        switch(x) {
            case 0: return path+"pencil.png";
            case 1: return path+"eraser.png";
            case 2: return path+"bin.png";
            case 3: return path+"pencil-highlight.png";
            case 4: return path+"eraser-highlight.png";
            case 5: return path+"bin-highlight.png";
            case 6: return path+"check.png";
            case 7: return path+"left-arrow.png";
            case 8: return path+"right-arrow.png";
            case 9: return path+"bulb.png";
            case 10: return path+"check-highlight.png";
            case 11: return path+"left-arrow-highlight.png";
            case 12: return path+"right-arrow-highlight.png";
            case 13: return path+"bulb-highlight.png";
        } return null;
    }
    //Comment this out later (to follow code conventions)
    public static void main(String[] args) {
        new display();
    }
}
