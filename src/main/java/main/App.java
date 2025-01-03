package main;

import javax.swing.*;

public class App extends JFrame {

    public App() {
        setTitle("funny app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        gamePanel.setup();
        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        new App();
    }
}
