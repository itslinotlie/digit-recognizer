package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class logicDisplay extends JFrame {
    public int WIDTH = 300, HEIGHT = 300, BRUSH_SIZE, a[] = {0, 0, 3, 8};
    public JSlider js;
    public logicDisplay(int size) {
        super("PIXEL Enlargement Center");
        setupFrame();
        setupVariable(size);
        setupSlider();
    }
    public void setupFrame() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
    }
    public void setupVariable(int size) {
        BRUSH_SIZE = size;
    }
    public void setupSlider() {
        js = new JSlider(JSlider.HORIZONTAL, 1, 3, BRUSH_SIZE);
        js.setMajorTickSpacing(1);
        js.setPaintTicks(true);

        Hashtable tbl = new Hashtable();
        tbl.put(1, new JLabel("<html>Smoll px<br>(1)<html>"));
        tbl.put(2, new JLabel("<html>px<br>(2)<html>"));
        tbl.put(3, new JLabel("<html>Big PX<br>(3)<html>"));

        js.setLabelTable(tbl);
        js.setPaintLabels(true);
        js.setVisible(true);
        js.setOpaque(true);
        add(js);
    }
    public int getBRUSH_SIZE() {
        return a[js.getValue()];
    }
}
