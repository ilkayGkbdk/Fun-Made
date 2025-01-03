package main;

import java.awt.*;

public class UI {

    GamePanel gp;
    public Font arial20 = new Font("Arial", Font.PLAIN, 20);
    public Font arial40B = new Font("Arial", Font.BOLD, 40);

    public String currentMessage = "";
    public boolean showMessage = false;
    public int messageCounter = 0;

    public boolean isGameOver = false;

    public UI(GamePanel gp_) {
        gp = gp_;
    }

    public void setMessage(String message) {
        currentMessage = message;
        showMessage = true;
    }

    public void draw(Graphics2D g2) {
        if (gp.keyHandler.debug) {
            drawDebugScreen(g2);
        }
    }

    public void drawDebugScreen(Graphics2D g2) {
        g2.setFont(arial20);
        g2.setColor(Color.WHITE);
        int x = gp.screenWidth - gp.tileSize * 10;
        g2.drawString("Player X: " + gp.player.worldX, x, gp.tileSize * 2);
        g2.drawString("Player Y: " + gp.player.worldY, x, gp.tileSize * 3);
        g2.drawString("Player Col: " + (gp.player.worldX / gp.tileSize + 1), x, gp.tileSize * 4);
        g2.drawString("Player Row: " + (gp.player.worldY / gp.tileSize + 1), x, gp.tileSize * 5);
        g2.drawString("Player Direction: " + gp.player.direction, x, gp.tileSize * 6);
        g2.drawString("Player Speed: " + gp.player.speed, gp.tileSize, x * 7);
        g2.drawString("Player Has Key: " + gp.player.hasKey, gp.tileSize, x * 8);
    }
}