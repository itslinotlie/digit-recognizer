package gui;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class display extends JFrame {
    public int WIDTH=700, HEIGHT=700, GRID_SIZE=20, MARGIN=80;
    public String path = new File("").getAbsolutePath()+"/res/static/";

    public JPanel bg = new JPanel();
    public static JLabel grid[][] = new JLabel[28][28], img[] = new JLabel[16], mask[] = new JLabel[8], digit[] = new JLabel[10];
    public ImageIcon icon[] = new ImageIcon[16], digitIcon[] = new ImageIcon[10];
    public Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    public Border borderr = BorderFactory.createLineBorder(Color.BLACK, 3);
    public logic brain = new logic();

    public display() throws IOException {
        super("I'll take your #");
        setupFrame();
        setupPanel();
        setupLabel();
        setupDigit();
        repaint();
    }
    public void setupFrame() {
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
                brain.createGridAction(grid[i][j]);
                bg.add(grid[i][j]);
            }
        }
        //right bar 0 | (pencil, eraser, bin) --> 3 | (highlights)
        for (int i=0;i<6;i++) {
            img[i] = new JLabel();
            createLabelInfo(img[i], i);
            int x = WIDTH-(int)(1.35*icon[i].getIconWidth()), y = MARGIN*(1+2*i);
            int x2 = x+5, y2 = y-6*MARGIN+5;
            int w = icon[i].getIconWidth(), h = icon[i].getIconHeight();
            if(i>3) img[i].setVisible(false);
            img[i].setBounds(i>2? x2:x, i>2? y2:y, w, h);
        }
        //bottom bar 6 | (validate, digit, left arrow, right arrow, bulb) --> 11 | (highlights)
        for (int i=6;i<16;i++) {
            img[i] = new JLabel();
            createLabelInfo(img[i], i);
            int x = GRID_SIZE*(-41+7*i), y = HEIGHT-(int)(1.45*icon[i].getIconHeight());
            int x2 = GRID_SIZE*(-76+7*i)+5, y2 = y+3;
            int w = icon[i].getIconWidth(), h = icon[i].getIconHeight();
            //fine-tuning label placements
            if(i==11) {y2+=2;}
            if(i==13) {x2-=13; y2-=3;}
            if(i==14) {x2+=3;y2-=3;}
            img[i].setBounds(i>10? x2:x, i>10? y2:y, w, h);
            if(i==7 || i==12 || (i>10 && i!=14)) img[i].setVisible(false);
            //Removing Light Bulb Feature
            if(i==10 || i==15) img[i].setVisible(false);
        }
        //mask (invisible layer above graphics)
        for (int i=0;i<8;i++) {
            mask[i] = new JLabel();
            createLabelInfo(mask[i], i);
            mask[i].setIcon(null);
            int x = WIDTH-(int)(1.35*icon[i].getIconWidth()), y = MARGIN*(1+2*i);
            int x2 = GRID_SIZE*(-20+7*i), y2 = HEIGHT-(int)(1.45*icon[i].getIconHeight());
            int w = icon[i].getIconWidth(), h = icon[i].getIconHeight();
            mask[i].setBounds(i>2? x2:x, i>2? y2:y, w, h);
//            mask[i].setBorder(border);
            brain.createLabelAction(mask[i]);
            //Removing Light Bulb Feature
            if(i==7 || i==4) mask[i].setVisible(false);
        }
    }
    public void setupDigit() {
        for (int i=0;i<10;i++) {
            digit[i] = new JLabel();
            digitIcon[i] = new ImageIcon(path+i+".png");
            Image scaleIcon = digitIcon[i].getImage().getScaledInstance(digitIcon[i].getIconWidth()*9/10, digitIcon[i].getIconHeight()*9/10, Image.SCALE_DEFAULT);
            digitIcon[i] = new ImageIcon(scaleIcon);
            digit[i].setIcon(digitIcon[i]);
            int x = GRID_SIZE*8, y = HEIGHT-(int)(1.45*digitIcon[i].getIconHeight());
            int w = digitIcon[i].getIconWidth(), h = digitIcon[i].getIconHeight();
            digit[i].setBounds(x, y, w, h);
            digit[i].setVisible(false);
            bg.add(digit[i]);
        }
    }
    public void createLabelInfo(JLabel lbl, int x) {
        icon[x] = new ImageIcon(getPath(x));
        int w1 = icon[x].getIconWidth()*3/8, h1 = icon[x].getIconHeight()*3/8;
        int w2 = icon[x].getIconWidth()*9/10, h2 = icon[x].getIconHeight()*9/10;
        Image scaleIcon = icon[x].getImage().getScaledInstance(x==7? w2:w1, x==7? h2:h1, Image.SCALE_DEFAULT);
        icon[x] = new ImageIcon(scaleIcon);
        lbl.setIcon(icon[x]);
        bg.add(lbl);
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
            case 7: return path+brain.getDigit()+".png";
            case 8: return path+"left-arrow.png";
            case 9: return path+"right-arrow.png";
            case 10: return path+"bulb.png";
            case 11: return path+"check-highlight.png";
            //case 12 never gets applied, but it allows for easy transitions
            case 12: return path+brain.getDigit()+"-highlight.png";
            case 13: return path+"left-arrow-highlight.png";
            case 14: return path+"right-arrow-highlight.png";
            case 15: return path+"bulb-highlight.png";
        } return null;
    }
}
