
import java.awt.Color;
import src.Zeichenfenster;

public class Main {
    public static void main(String[] args) {
        Color backgroundColor = Color.white;
        Zeichenfenster z = new Zeichenfenster("zombie_spiel", 1920, 1080, backgroundColor);
        z.show();
        Player player = new Player(920, 500, 200);
        Zombie zombie2 = new Zombie(100, 100, 200);

        player.show(z);
        zombie2.show(z);
        z.updateCanvas();


    }
        
}
