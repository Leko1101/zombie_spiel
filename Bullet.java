import java.awt.Color;
import src.Zeichenfenster;

public class Bullet {
    double angle;
    Color color = Color.BLACK;
    int speed = 10;
    int x; 
    int y;

    public Bullet(int startX, int startY, int targetX, int targetY) {
        this.x = startX;
        this.y = startY;
        this.angle = Math.atan2(targetY - startY, targetX - startX);
    }

    public void show(Zeichenfenster z) {
        z.fillCircle(x, y, 10, color);
    }

    public void move() {
        int speedX = (int) Math.round(Math.cos(angle) * speed);
        int speedY = (int) Math.round(Math.sin(angle) * speed);
        x = x + speedX;
        y = y + speedY;
    }

}


