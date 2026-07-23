import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import src.Zeichenfenster;

public class Zombie {
    private int x;
    private int y;
    private final Color color = Color.GREEN;
    private final int r;
    private final BufferedImage sprite;

    public Zombie(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.sprite = loadSprite("zombie.png");
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


    public void moveTowards(Player player, int speed) {
        int dx = player.getX() - x;
        int dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            x += (int) Math.round(dx / distance * speed);
            y += (int) Math.round(dy / distance * speed);
        }
    }

    public boolean isTouching(Player player) {
        int dx = player.getX() - x;
        int dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= player.getR() + r) {
            return true;
        }
        return false;
    }

    public void show(Zeichenfenster z) {
        if (sprite != null) {
            int imgW = sprite.getWidth();
            int imgH = sprite.getHeight();
            double targetSize = 2 * r;
            double scale = Math.min(targetSize / imgW, targetSize / imgH);

            AffineTransform at = new AffineTransform();
            at.translate(x - (imgW * scale) / 2.0, y - (imgH * scale) / 2.0);
            at.scale(scale, scale);

            z.drawImage(sprite, at);
        } else {
            z.fillCircle(x, y, r, color);
        }
    }
}
