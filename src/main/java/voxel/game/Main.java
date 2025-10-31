package voxel.game;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args)
    {
        // spawn ui
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Voxel Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.setVisible(true);

            // 60fps loop
            int delayMs = 1000 / 60; // ms
            new Timer(delayMs, e -> gamePanel.updateAndRepaint()).start();
        });
    }
}