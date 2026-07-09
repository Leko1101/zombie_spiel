import java.awt.Color;
import src.code.Zeichenfenster;

public class Player {
    int[] x;
    int[] y;
    Color color = Color.RED;
    int size;

    public Player(int x, int y, int size) {
        this.size = size;
        this.x = new int[2];
        this.y = new int[2];
        this.x[0] = x;
        this.x[1] = x + size;
        this.y[0] = y;
        this.y[1] = y + size;
        this.size = size;
    }

    public void show(Zeichenfenster z) {
        z.fillRectangle(x, y, color);
    }

}
