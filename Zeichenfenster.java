package src.code;

import java.awt.*;
import javax.swing.*;

public class Zeichenfenster {
    private final JFrame frame;
    private final CanvasPane canvas;
    private final JPanel steuerungOst, steuerungSued;
    private Graphics2D graphic;
    private final Color backgroundColor;
    private Image canvasImage;
    private final int width;
    private final int height;

    public Zeichenfenster(String titel, int width, int height, Color hintergrundFarbe) {
        this.width = width;
        this.height = height;
        this.backgroundColor = hintergrundFarbe;

        frame = new JFrame();
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(width, height));
        
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        steuerungOst = new JPanel();
        steuerungSued = new JPanel();
        steuerungOst.setLayout(new BoxLayout(steuerungOst, BoxLayout.Y_AXIS));
        steuerungSued.setLayout(new BoxLayout(steuerungSued, BoxLayout.X_AXIS));
        
        p1.add(steuerungOst, BorderLayout.NORTH);
        frame.getContentPane().add(p1, BorderLayout.EAST);
        frame.getContentPane().add(steuerungSued, BorderLayout.SOUTH);
        
        frame.setTitle(titel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
    }

    public int getWidth() {
        return canvas.getWidth() > 0 ? canvas.getWidth() : width;
    }

    public int getHeight() {
        return canvas.getHeight() > 0 ? canvas.getHeight() : height;
    }

    public Graphics2D getGraphic() {
        if (graphic == null) show();
        return graphic;
    }

    public void updateCanvas() {
        canvas.repaint();
    }

    public void drawTriangle(int[] x, int[] y, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.drawPolygon(x, y, 3);
        graphic.setColor(original);
    }

    public void fillTriangle(int[] x, int[] y, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.fillPolygon(x, y, 3);
        graphic.setColor(original);
    }

    public void drawRectangle(int[] x, int[] y, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.drawPolygon(x, y, 4);
        graphic.setColor(original);
    }

    public void fillRectangle(int[] x, int[] y, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.fillPolygon(x, y, 4);
        graphic.setColor(original);
    }

    public void drawLine(int[] x, int[] y, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.drawLine(x[0], y[0], x[1], y[1]);
        graphic.setColor(original);
    }

    public void drawCircle(int x, int y, int r, Color color) {
        if (graphic == null) show();
        Color original = 00,00