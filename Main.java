
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

        z.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.out.println("Q pressed");
                    player.updateAngle(-0.1);
                    z.updateCanvas();
                    z.clearCanvas();
                    player.show(z);
                } else if (e.getKeyCode() == KeyEvent.VK_E) {
                    System.out.println("E pressed");
                    player.updateAngle(0.1);
                    z.updateCanvas();
                    z.clearCanvas();
                    player.show(z);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        z.requestFocus();
    }
}
