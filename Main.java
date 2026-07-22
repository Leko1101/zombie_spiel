import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;
import src.Zeichenfenster;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Color backgroundColor = Color.white;
        Zeichenfenster z = new Zeichenfenster("zombie_spiel", 1920, 1080, backgroundColor);
        z.show();
        Player player = new Player(920, 500, 100);
        
        Set<Integer> pressedKeys = new HashSet<>();
        ArrayList<Zombie> zombies = new ArrayList<>();
        ArrayList<Bullet> bullets = new ArrayList<>();
        int[] score = new int[]{0};

        for (int i = 0; i < 6; i++) {
            zombies.add(new Zombie((int) (Math.random() * 1860) + 30, (int) (Math.random() * 1020) + 30, 60));
        }

        update(z, player, zombies, zombies.size(), bullets);

        Timer gameLoop = new Timer(40, e -> {
            for (Zombie zombie : zombies) {
                if (zombie != null) {
                    zombie.moveTowards(player, 3);
                }
            }

            for (Zombie zombie : zombies) {
                if (zombie != null) {
                    if(zombie.isTouching(player)) {
                        System.out.println("Game over");
                        z.closeWindow();
                        System.exit(0); //display a game over screen
                    }
                }
            }

            
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
    
                for (int j = zombies.size() - 1; j >= 0; j--) {
                    Zombie zombie = zombies.get(j);

                    int dx = bullet.getX() - zombie.getX();
                    int dy = bullet.getY() - zombie.getY();

                    if (Math.sqrt(dx * dx + dy * dy) <= bullet.getR() + zombie.getR()) {
                        bullets.remove(i);  // Remove bullet by index
                        zombies.remove(j);  // Remove zombie by index
                        score[0] += 1;
                        break; // Stop checking this bullet against other zombies since it's gone!
                    }
                }
                if (bullet.getX() > 1920 + bullet.getR() || bullet.getX() < 0 - bullet.getR() || bullet.getY() > 1080 + bullet.getR() || bullet.getY() < 0 - bullet.getR()) {
                    bullets.remove(i);
                }
                update(z, player, zombies, zombies.size(), bullets);
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

            for (Bullet bullet : bullets) {
                if (bullet != null) {
                    bullet.move();
                }
            }

            update(z, player, zombies, zombies.size(), bullets);
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

        z.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Bullet bullet = new Bullet(player.getTipX(), player.getTipY(), player.getRads());
                    bullets.add(bullet);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        z.requestFocus();
    }

    private static void update(Zeichenfenster z, Player player, ArrayList<Zombie> zombies, int zombiesLength, ArrayList<Bullet> bullets) {
        z.clearCanvas();
        player.show(z);

        for (int i = 0; i < zombiesLength; i++) {
            if (zombies.get(i) != null) {
                zombies.get(i).show(z);
            }
        }

        for (Bullet bullet : bullets) {
            if (bullet != null) {
                bullet.show(z);
            }
        }

        z.updateCanvas();
    }
}

