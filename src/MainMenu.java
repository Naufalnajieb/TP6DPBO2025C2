/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 6
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Flappy Bird");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel utama dengan background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gambar background
                ImageIcon background = new ImageIcon(getClass().getResource("FlappyBirdAssets/background.png"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

                // Gambar burung di tengah (dikecilkan)
                ImageIcon bird = new ImageIcon(getClass().getResource("FlappyBirdAssets/bird.png"));
                int birdWidth = 60; // Ukuran lebih kecil
                int birdHeight = 45;
                int birdX = getWidth() / 2 - birdWidth / 2;
                int birdY = getHeight() / 2 - birdHeight / 2;
                g.drawImage(bird.getImage(), birdX, birdY, birdWidth, birdHeight, this);
            }
        };
        panel.setLayout(new BorderLayout());

        // Tombol start
        JButton startButton = new JButton("Start Game!"); // Unicode play symbol
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(46, 139, 87));
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                startGame();
            }
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }

    private void startGame() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}