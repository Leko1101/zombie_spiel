import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.Timer;
import src.Zeichenfenster;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Color backgroundColor = Color.white;
            Zeichenfenster z = new Zeichenfenster("zombie_spiel", 1920, 1080, backgroundColor);
        z.show();
        Player player = new Player(920, 500, 100);
        Color textColor = Color.BLACK;

        JButton restartButton = new JButton("Restart");
        restartButton.setFocusable(false);
        restartButton.setVisible(false);
        z.addButton(restartButton);

        Set<Integer> pressedKeys = new HashSet<>();
        ArrayList<Zombie> zombies = new ArrayList<>();
        ArrayList<Bullet> bullets = new ArrayList<>();
        int[] score = new int[]{0};

        int zombieR = 60;

        int[] spawnTicker = new int[]{0};
        int baseSpawnDelay = 75; // 125 ticks * 40ms = 5000ms (5 seconds) initial delay
        int minSpawnDelay = 1;   // 12 ticks * 40ms = 480ms minimum delay (cap max speed)  

        update(z, player, zombies,bullets);

        final Timer[] gameLoop = new Timer[1];

        gameLoop[0] = new Timer(40, e -> {
            
            spawnTicker[0]++;

            int dynamicDelay = (int) (baseSpawnDelay / (1.0 + (score[0] * 0.1)));
            dynamicDelay = Math.max(dynamicDelay, minSpawnDelay);

            if (spawnTicker[0] >= dynamicDelay) {
                
                int[] spawnXY = getRandomXY();

                if (spawnTicker[0] >= dynamicDelay) {
    
                    // Position suchen, die mindestens 200px vom Spieler entfernt ist
                    do {
                        spawnXY = getRandomXY();
                        int dx = player.getX() - spawnXY[0];
                        int dy = player.getY() - spawnXY[1];
                        if (Math.sqrt(dx * dx + dy * dy) >= 200) {
                            break;
                        }
                    } while (true);

                    zombies.add(new Zombie(spawnXY[0], spawnXY[1], zombieR));
                    spawnTicker[0] = 0; // Reset ticker
                }

            }

            
            for (Zombie zombie : zombies) {
                if (zombie != null) {
                    zombie.moveTowards(player, 3);
                }
            }

            for (Zombie zombie : zombies) {
                if (zombie != null) {
                    if(zombie.isTouching(player)) {
                        System.out.println("Game over");
                        z.clearCanvas();
                        z.drawString("GAME OVER", 960, 400, textColor);
                        z.drawString("Score: " + String.valueOf(score[0]), 960, 540, textColor);
                        z.updateCanvas();
                        restartButton.setVisible(true);
                        z.refresh();
                        gameLoop[0].stop();
                        return;
                    }
                }
            }

            
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                boolean bulletRemoved = false;
    
                for (int j = zombies.size() - 1; j >= 0; j--) {
                    Zombie zombie = zombies.get(j);

                    int dx = bullet.getX() - zombie.getX();
                    int dy = bullet.getY() - zombie.getY();

                    if (Math.sqrt(dx * dx + dy * dy) <= bullet.getR() + zombie.getR()) {
                        bullets.remove(i);  // Remove bullet by index
                        zombies.remove(j);  // Remove zombie by index
                        score[0] += 1;
                        bulletRemoved = true;
                        break; // Stop checking this bullet against other zombies since it's gone!
                    }
                }


                if(!bulletRemoved) {
                    if (bullet.getX() > 1920 + bullet.getR() || bullet.getX() < 0 - bullet.getR() || bullet.getY() > 1080 + bullet.getR() || bullet.getY() < 0 - bullet.getR()) {
                        bullets.remove(i);
                    }
                }
                update(z, player, zombies, bullets);
            }

            int movementChange = 10;
            if (pressedKeys.contains(KeyEvent.VK_A) && player.getX() > 0) {
                player.move(movementChange, "A");
            }
            if (pressedKeys.contains(KeyEvent.VK_D) && player.getX() < 1920) {
                player.move(movementChange, "D");
            }
            if (pressedKeys.contains(KeyEvent.VK_S) && player.getY() < 1080) {
                player.move(movementChange, "S");
            }
            if (pressedKeys.contains(KeyEvent.VK_W) && player.getY() > 0) {
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
            
            update(z, player, zombies, bullets);
            z.drawString("Score: " + String.valueOf(score[0]), 100, 100, textColor);
        });
        gameLoop[0].start();

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

        restartButton.addActionListener(e -> {
            bullets.clear();
            zombies.clear();
            score[0] = 0;
            spawnTicker[0] = 0;
            player.reset(920, 500, 0.5);
            restartButton.setVisible(false);
            z.clearCanvas();
            update(z, player, zombies, zombies.size(), bullets);
            z.refresh();
            z.requestFocus();
            if (!gameLoop[0].isRunning()) {
                gameLoop[0].start();
            }
        });

        z.requestFocus();
            });
    }

    private static int[] getRandomXY() {
        int x = (int) (Math.random() * 1920);
        int y = (int) (Math.random() * 1080);
        return new int[]{x,y};
    }

    private static void update(Zeichenfenster z, Player player, ArrayList<Zombie> zombies, ArrayList<Bullet> bullets) {
        z.clearCanvas();
        player.show(z);

        for (Bullet bullet : bullets) {
            if (bullet != null) {
                bullet.show(z);
            }
        }

        for (Zombie zombie : zombies) {
            if (zombie != null) {
                zombie.show(z);
            }
        }

        z.updateCanvas();
    }
}

