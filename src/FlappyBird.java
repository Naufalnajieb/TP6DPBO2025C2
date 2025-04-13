/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 6
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipelineImage;
    Image upperPipelineImage;

    // Player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // Pipes attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 44;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // Game logic
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    // Untuk Tambahan Fitur
    boolean gameOver = false;
    int score = 0;
    JLabel scoreLabel;
    Font scoreFont = new Font("Arial", Font.BOLD, 36);

    // Tambahkan method checkCollision()
    public boolean checkCollision() {
        // Check if player hits the ground
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            return true;
        }

        // Check collision with pipes
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(),
                player.getWidth(), player.getHeight());

        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(),
                    pipe.getWidth(), pipe.getHeight());
            if (playerRect.intersects(pipeRect)) {
                return true;
            }
        }

        return false;
    }

    // Modifikasi method move()
    public void move() {
        if (!gameOver) {
            player.setVelocityY(player.getVelocityY() + gravity);
            player.setPosY(player.getPosY() + player.getVelocityY());
            player.setPosY(Math.max(player.getPosY(), 0));

            // Untuk melacak pasangan pipa yang sudah dilewati
            boolean passedPair = false;

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

                // Cek pasangan pipa (atas dan bawah)
                if (i % 2 == 0 && i + 1 < pipes.size()) { // Cek pipa atas
                    Pipe upperPipe = pipes.get(i);
                    Pipe lowerPipe = pipes.get(i+1);

                    if (!upperPipe.isPassed() && !lowerPipe.isPassed() &&
                            upperPipe.getPosX() + upperPipe.getWidth() < player.getPosX()) {

                        upperPipe.setPassed(true);
                        lowerPipe.setPassed(true);
                        score++;
                        passedPair = true;
                    }
                }
            }

            // Update score label jika ada perubahan
            if (passedPair) {
                SwingUtilities.invokeLater(() -> {
                    scoreLabel.setText(String.valueOf(score));
                });
            }

            if (checkCollision()) {
                gameOver = true;
                gameLoop.stop();
                pipesCooldown.stop();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, frameWidth, frameHeight);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", frameWidth/2 - 80, frameHeight/2 - 30);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, frameWidth/2 - 40, frameHeight/2);
            g.drawString("Press R to restart", frameWidth/2 - 80, frameHeight/2 + 30);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Method untuk menangani input keyboard ketika tombol diketik
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) return;
            player.setVelocityY(-10);
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    public void restartGame() {
        gameOver = false;
        score = 0;
        scoreLabel.setText("Score: 0");
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        gameLoop.start();
        pipesCooldown.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Method untuk menangani input keyboard ketika tombol dilepas
    }

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);

        // Score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel, BorderLayout.NORTH);


        // load images
        backgroundImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/bird.png")).getImage();
        lowerPipelineImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/lowerPipe.png")).getImage();
        upperPipelineImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/upperPipe.png")).getImage();

        playerWidth = 34;  // Lebar asli sprite burung
        playerHeight = 24; // Tinggi asli sprite burung
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pipe created");
                placePipes();
            }
        });

        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    // Modifikasi method draw():
    public void draw(Graphics g) {
        // Draw background
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        // Draw pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                    pipe.getWidth(), pipe.getHeight(), null);
        }

        // Draw player
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                player.getWidth(), player.getHeight(), null);

        // Draw live score (lebih besar dan di tengah atas)
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.setColor(Color.WHITE);
        // Hitung lebar teks untuk posisi tengah
        FontMetrics fm = g.getFontMetrics();
        String scoreText = String.valueOf(score);
        int scoreWidth = fm.stringWidth(scoreText);
        g.drawString(scoreText, frameWidth/2 - scoreWidth/2, 50);

        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, frameWidth, frameHeight);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", frameWidth/2 - 80, frameHeight/2 - 30);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, frameWidth/2 - 40, frameHeight/2);
            g.drawString("Press R to restart", frameWidth/2 - 80, frameHeight/2 + 30);
        }
    }

    public void placePipes() {
        // Jarak antar pipa lebih nyaman (sedang)
        int randomPosY = (int) (pipeStartPosY - pipeHeight/3 - Math.random() * (pipeHeight/3));
        int openingSpace = frameHeight/3;  // Lebih lebar dari sebelumnya

        // Kecepatan pipa
        int pipeSpeed = -3; // Sedikit lebih lambat

        // Upper pipe
        Pipe upperPipe = new Pipe(
                pipeStartPosX,
                randomPosY,
                pipeWidth,
                pipeHeight,
                upperPipelineImage
        );
        upperPipe.setVelocityX(pipeSpeed);
        pipes.add(upperPipe);

        // Lower pipe
        Pipe lowerPipe = new Pipe(
                pipeStartPosX,
                (randomPosY + openingSpace + pipeHeight),
                pipeWidth,
                pipeHeight,
                lowerPipelineImage
        );
        lowerPipe.setVelocityX(pipeSpeed);
        pipes.add(lowerPipe);

        // Jarak spawn pipa berikutnya
        pipesCooldown.setDelay(2000); // 2 detik antara pipa
    }
}
