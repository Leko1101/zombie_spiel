import java.awt.Color;
import src.Zeichenfenster;

public class Zombie {
    private int x;
    private int y;
    private final Color color = Color.GREEN;
    private final int size;

    public Zombie(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
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

    public void show(Zeichenfenster z) {
        z.fillRectangle(new int[]{x, x + size}, new int[]{y, y + size}, color);
    }
}
