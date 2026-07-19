import java.awt.Color;
import src.Zeichenfenster;

public class Zombie {
    private int x;
    private int y;
    private final Color color = Color.GREEN;
    private final int r;

    public Zombie(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
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
        z.fillCircle(x, y, r, color);
    }
}
