package src;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Zeichenfenster {
    private final JFrame frame;
    private final CanvasPane canvas;
    private final JPanel steuerungOst, steuerungSued;
    private Graphics2D graphic;
    private final Color backgroundColor;
    private BufferedImage canvasImage;
    private final int width;
    private final int height;
    private final boolean headlessMode;

    public Zeichenfenster(String titel, int width, int height, Color hintergrundFarbe) {
        this.width = width;
        this.height = height;
        this.backgroundColor = hintergrundFarbe;
        this.headlessMode = GraphicsEnvironment.isHeadless();

        if (headlessMode) {
            frame = null;
            canvas = new CanvasPane();
            canvas.setPreferredSize(new Dimension(width, height));
            steuerungOst = new JPanel();
            steuerungSued = new JPanel();
            return;
        }

        frame = new JFrame();
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(true);

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

    public void addKeyListener(KeyListener listener) {
        canvas.addKeyListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
        canvas.addMouseListener(listener);
    }

    public void requestFocus() {
        canvas.requestFocusInWindow();
    }

    public void updateCanvas() {
        if (frame != null) {
            canvas.repaint();
        }
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

        int x0 = Math.min(x[0], x[1]);
        int y0 = Math.min(y[0], y[1]);
        int width = Math.abs(x[1] - x[0]);
        int height = Math.abs(y[1] - y[0]);

        graphic.drawRect(x0, y0, width, height);
        graphic.setColor(original);
    }

    public void fillRectangle(int[] x, int[] y, Color color) {
        if (graphic == null) show();
        Color original = graphic.getColor();
        graphic.setColor(color);

        int x0 = Math.min(x[0], x[1]);
        int y0 = Math.min(y[0], y[1]);
        int width = Math.abs(x[1] - x[0]);
        int height = Math.abs(y[1] - y[0]);

        graphic.fillRect(x0, y0, width, height);
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
        if (graphic == null) {
            int w = width;
            int h = height;

            canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            graphic = canvasImage.createGraphics();

            graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, w, h);
            graphic.setColor(Color.black);
        }

        if (frame != null) {
            frame.setVisible(true);
        }
    }

    public void closeWindow() {
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
        }
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

        