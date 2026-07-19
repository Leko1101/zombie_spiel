import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;
import src.Zeichenfenster;

public class Main {
    public static void main(String[] args) {
        Color backgroundColor = Color.white;
        Zeichenfenster z = new Zeichenfenster("zombie_spiel", 1920, 1080, backgroundColor);
        z.show();

        int zombiesLength = 6;
        Player player = new Player(920, 500, 100);
        Zombie[] zombies = new Zombie[zombiesLength];
        Set<Integer> pressedKeys = new HashSet<>();

        for (int i = 0; i < zombies.length; i++) {
            zombies[i] = new Zombie(80 + i * 120, 80 + i * 60, 60);
        }

        update(z, player, zombies, zombies.length);

        Timer gameLoop = new Timer(40, e -> {
            for (Zombie zombie : zombies) {
                if (zombie != null) {
                    zombie.moveTowards(player, 3);
                }
            }

            int movementChange = 10;
            if (pressedKeys.contains(KeyEvent.VK_A)) {
                player.move(movementChange, "A");
            }
            if (pressedKeys.contains(KeyEvent.VK_D)) {
                player.move(movementChange, "D");
            }
            if (pressedKeys.contains(KeyEvent.VK_S)) {
                player.move(movementChange, "S");
            }
            if (pressedKeys.contains(KeyEvent.VK_W)) {
                player.move(movementChange, "W");
            }
            if (pressedKeys.contains(KeyEvent.VK_Q)) {
                player.updateAngle(-0.03);
            }
            if (pressedKeys.contains(KeyEvent.VK_E)) {
                player.updateAngle(0.03);
            }

            update(z, player, zombies, zombies.length);
        });
        gameLoop.start();

        z.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        z.requestFocus();
    }

    private static void update(Zeichenfenster z, Player player, Zombie[] zombies, int zombiesLength) {
        z.clearCanvas();
        player.show(z);

        for (int i = 0; i < zombiesLength; i++) {
            if (zombies[i] != null) {
                zombies[i].show(z);
            }
        }

        z.updateCanvas();
    }
}

