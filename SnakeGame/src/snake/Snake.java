package snake;

import javax.swing.*;
import java.awt.*;

public class Snake {
    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            JFrame frame = new JFrame("Ã∞≥‘…ﬂ–°”Œœ∑");
            frame.setSize(900, 800);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
