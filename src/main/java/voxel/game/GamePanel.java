package voxel.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel
{
    private int playerX = 100;
    private int playerY = 100;
    private final int playerSize = 32;
    private boolean up, down, left, right;

        public GamePanel()
        {
            setFocusable(true);
            requestFocusInWindow();

            addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    setKey(e, true);
                }

                @Override
                public void keyReleased(KeyEvent e)
                {
                    setKey(e, false);
                }

                private void setKey(KeyEvent e, boolean pressed)
                {
                    switch (e.getKeyCode())
                    {
                        case KeyEvent.VK_W, KeyEvent.VK_UP -> up = pressed;
                        case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = pressed;
                        case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = pressed;
                        case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = pressed;
                    }
                }
            });
        }

    public void updateAndRepaint() {
        int speed = 4;
        if (up) playerY -= speed;
        if (down) playerY += speed;
        if (left) playerX -= speed;
        if (right) playerX += speed;

        // bounds clamp
        playerX = Math.max(0, Math.min(getWidth() - playerSize, playerX));
        playerY = Math.max(0, Math.min(getHeight() - playerSize, playerY));

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // background
        g.setColor(new Color(50, 120, 50));
        g.fillRect(0, 0, getWidth(), getHeight());

        // placeholder grid to hint at voxel grid
        g.setColor(new Color(0, 0, 0, 30));
        int cell = 32;
        for (int x = 0; x < getWidth(); x += cell) g.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += cell) g.drawLine(0, y, getWidth(), y);

        // draw player rectangle
        g.setColor(Color.ORANGE);
        g.fillRect(playerX, playerY, playerSize, playerSize);

        // HUD text
        g.setColor(Color.WHITE);
        g.drawString("Use WASD / Arrow keys to move the square", 10, 20);
        }
}
