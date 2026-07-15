package src;

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
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.drawOval(x - r, y - r, 2 * r, 2 * r);
        graphic.setColor(original);
    }

    public void fillCircle(int x, int y, int r, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);
        graphic.fillOval(x - r, y - r, 2 * r, 2 * r);
        graphic.setColor(original);
    }

    public void show() {
        frame.setVisible(true);
        if (graphic == null) {
            // Use the actual size of the canvas component
            int w = canvas.getWidth() > 0 ? canvas.getWidth() : width;
            int h = canvas.getHeight() > 0 ? canvas.getHeight() : height;
            
            canvasImage = canvas.createImage(w, h);
            graphic = (Graphics2D) canvasImage.getGraphics();
            
            // Enable Anti-aliasing for smoother lines
            graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, w, h);
            graphic.setColor(Color.black);
        }
    }

    public void closeWindow() {
        frame.setVisible(false);
        frame.dispose();
    }

    public void clearCanvas() {
        if (graphic == null) return;
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphic.setColor(original);
    }

    public void wait(int zeit) {
        try {
            Thread.sleep(zeit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void drawString(String text, int x, int y, Color color) {
        if (graphic == null) show();
        graphic.setColor(color);
        if (graphic == null) show();
        graphic.drawString(text, x, y);
    }

    private class CanvasPane extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (canvasImage != null) {
                g.drawImage(canvasImage, 0, 0, this);
            }
        }
    }

}

        