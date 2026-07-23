import java.awt.Color;
import src.Zeichenfenster;

public class Bullet {
    private double angle;
    private Color color = Color.BLACK;
    private int speed = 30;
    private int x; 
    private int y;
    private int r = 10;

    public Bullet(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void show(Zeichenfenster z) {
        z.fillCircle(x, y, r, color);
    }

    public void move() {
        int speedX = (int) Math.round(Math.cos(angle) * speed);
        int speedY = (int) Math.round(Math.sin(angle) * speed);
        x = x + speedX;
        y = y + speedY;
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

}


