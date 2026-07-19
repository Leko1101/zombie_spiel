import java.awt.Color;
import src.Zeichenfenster;

public class Player {
    private int x;
    private int y;
    private final Color color = Color.RED;
    private final int r;
    private double angle; //0-360° = 0-1

    public Player(int x, int y, int r) {
        this.r = r;
        this.x = x;
        this.y = y;
        this.angle = 0.5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void updateAngle(double change) {
        angle = angle + change;
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
        z.fillCircle(x, y, r, color);

        double rad = angle * 2 * Math.PI;

        int tipX = (int) Math.round(x + Math.cos(rad) * (r + 30));
        int tipY = (int) Math.round(y + Math.sin(rad) * (r + 30));

        int leftX = (int) Math.round(x - Math.cos(rad) * 15 - Math.sin(rad) * 10);
        int leftY = (int) Math.round(y - Math.sin(rad) * 15 + Math.cos(rad) * 10);

        int rightX = (int) Math.round(x - Math.cos(rad) * 15 + Math.sin(rad) * 10);
        int rightY = (int) Math.round(y - Math.sin(rad) * 15 - Math.cos(rad) * 10);

        z.fillTriangle(
            new int[]{leftX, rightX, tipX},
            new int[]{leftY, rightY, tipY},
            color
        );
    }

}


