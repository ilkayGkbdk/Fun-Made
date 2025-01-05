package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    GamePanel gp;

    public Font arial20;
    public Font arial40B;
    public Font maruMinya;

    public String currentMessage = "";
    public String currentDialogue = "";
    public boolean showMessage = false;
    public int messageCounter = 0;

    public boolean isGameOver = false;
    public int commandNum = 0;

    public UI(GamePanel gp_) {
        gp = gp_;

        arial20 = new Font("Arial", Font.PLAIN, 20);
        arial40B = new Font("Arial", Font.BOLD, 40);

        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/fonts/MaruMinya.ttf"));
            maruMinya = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 30);
        }
        catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMessage(String message) {
        currentMessage = message;
        showMessage = true;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(maruMinya);
        g2.setColor(Color.WHITE);

        if (gp.mainState == GameState.MAIN_MENU) {
            drawMainMenuScreen(g2);
        }
        else if (gp.mainState == GameState.PAUSE) {
            drawPauseScreen(g2);
        }
        else if (gp.mainState == GameState.PLAY) {
            drawPlayScreen(g2);
        }
        else if (gp.mainState == GameState.DIALOGUE) {
            drawDialogueScreen(g2);
        }
    }

    public void drawMainMenuScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75f));
        // Background
        g2.setColor(new Color(60, 100, 90));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        String text = "WIZARD GAME";
        int x = gp.uTool.getXForCenteredText(text, g2);
        int y = gp.screenHeight / 2 - gp.tileSize * 5;

        g2.setColor(Color.BLACK); // Shadow
        g2.drawString(text, x + 4, y + 4);
        g2.setColor(Color.WHITE); // Text
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));

        // Player Image
        x = gp.screenWidth / 2 - gp.tileSize;
        y += gp.tileSize * 3;
        g2.drawImage(gp.player.sprites[0][0], x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        text = "New Game";
        x = gp.uTool.getXForCenteredText(text, g2);
        y += gp.tileSize * 5;
        g2.drawString(text, x, y);
        gp.uTool.drawCommandBar(g2, 0, x, y);

        text = "Load Game";
        x = gp.uTool.getXForCenteredText(text, g2);
        y += (int) (gp.tileSize * 1.5);
        g2.drawString(text, x, y);
        gp.uTool.drawCommandBar(g2, 1, x, y);

        text = "Exit";
        x = gp.uTool.getXForCenteredText(text, g2);
        y += (int) (gp.tileSize * 1.5f);
        g2.drawString(text, x, y);
        gp.uTool.drawCommandBar(g2, 2, x, y);
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60));
        String text = "PAUSED";
        int x = gp.uTool.getXForCenteredText(text, g2);
        int y = gp.screenHeight / 2 - gp.tileSize;
        g2.drawString(text, x, y);
    }

    public void drawPlayScreen(Graphics2D g2) {
        if (gp.keyHandler.debug) {
            drawDebugScreen(g2);
        }
    }

    public void drawDialogueScreen(Graphics2D g2) {
        int x = gp.tileSize * 4;
        int y = gp.tileSize;
        int width = gp.screenWidth - gp.tileSize * 8;
        int height = gp.tileSize * 6;
        gp.uTool.drawDialogueBox(g2, x, y, width, height);

        x += (int) (gp.tileSize * 1.5);
        y += (int) (gp.tileSize * 1.8);
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += (int) (gp.tileSize * 1.5);
        }
    }

    public void drawDebugScreen(Graphics2D g2) {
        int x = gp.screenWidth - gp.tileSize * 10;
        int worldX = gp.player.worldX + gp.tileSize / 2;
        int worldY = gp.player.worldY + gp.tileSize / 2;

        g2.drawString("Player X: " + gp.player.worldX, x, gp.tileSize * 2);
        g2.drawString("Player Y: " + gp.player.worldY, x, gp.tileSize * 3);
        g2.drawString("Player Col: " + (worldX / gp.tileSize ), x, gp.tileSize * 4);
        g2.drawString("Player Row: " + (worldY / gp.tileSize), x, gp.tileSize * 5);
        g2.drawString("Player Direction: " + gp.player.direction, x, gp.tileSize * 6);
        g2.setColor(Color.RED);
        g2.drawString("FPS: " + gp.avgFPS, gp.tileSize, gp.tileSize * 8);
    }
}