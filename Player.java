import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import src.Zeichenfenster;

public class Player {
    private int x;
    private int y;
    private final Color color = Color.RED;
    private final int r;
    private double angle; //0-360° = 0-1
    private int tipX;
    private int tipY;
    private final BufferedImage sprite;

    public Player(int x, int y, int r) {
        this.r = r;
        this.x = x;
        this.y = y;
        this.angle = 0.5;
        this.sprite = loadSprite("player.png");
    }

    private BufferedImage loadSprite(String filename) {
        try (InputStream in = getClass().getResourceAsStream('/' + filename)) {
            if (in != null) {
                return ImageIO.read(in);
            }
        } catch (IOException ignored) {
        }

        try {
            return ImageIO.read(new File(filename));
        } catch (IOException ignored) {
        }

        try {
            return ImageIO.read(new File("zombie_spiel/" + filename));
        } catch (IOException ignored) {
        }

        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getTipX() {
        return tipX;
    }

    public int getTipY() {
        return tipY;
    }

    public double getRads() {
        return angle * 2 * Math.PI; 
    }

    public void updateAngle(double change) {
        angle = angle + change;
    }

    public void reset(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void move(int change, String keyPressed) {
        switch (keyPressed.toUpperCase()) {
            case "A":
                x = x - change;
                break;
            case "D":
                x = x + change;
                break;
            case "S":
                y = y + change;
                break;
            case "W":
                y = y - change;
                break;
            default:
                break;
        }
    }

    public void show(Zeichenfenster z) {
        double rad = angle * 2 * Math.PI;

        if (sprite != null) {
            int imgW = sprite.getWidth();
            int imgH = sprite.getHeight();
            double targetSize = 2 * r;
            double scale = Math.min(targetSize / imgW, targetSize / imgH);

            AffineTransform at = new AffineTransform();
            at.translate(x, y);
            at.rotate(rad);
            at.scale(scale, scale);
            at.translate(-imgW / 2.0, -imgH / 2.0);

            z.drawImage(sprite, at);
        } else {
            z.fillCircle(x, y, r, color);

            int leftX = (int) Math.round(x - Math.cos(rad) * 15 - Math.sin(rad) * 10);
            int leftY = (int) Math.round(y - Math.sin(rad) * 15 + Math.cos(rad) * 10);

            int rightX = (int) Math.round(x - Math.cos(rad) * 15 + Math.sin(rad) * 10);
            int rightY = (int) Math.round(y - Math.sin(rad) * 15 - Math.cos(rad) * 10);

            z.fillTriangle(
                new int[]{leftX, rightX, x + (int) Math.round(Math.cos(rad) * (r + 30))},
                new int[]{leftY, rightY, y + (int) Math.round(Math.sin(rad) * (r + 30))},
                color
            );
        }

        tipX = (int) Math.round(x + Math.cos(rad) * (r + 30));
        tipY = (int) Math.round(y + Math.sin(rad) * (r + 30));
    }

}


