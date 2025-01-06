package main;

import item.Item_Heart;
import item.SuperItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    GamePanel gp;

    public Font marMina;

    public String currentMessage = "";
    public String currentDialogue = "";
    public boolean showMessage = false;
    public int messageCounter = 0;

    public boolean isGameOver = false;
    public int commandNum = 0;

    public BufferedImage h_full, h_half, h_blank;

    public UI(GamePanel gp_) {
        gp = gp_;

        h_full = gp.imageLoader.loadImage("/items/heart_full.png", 1.5f);
        h_half = gp.imageLoader.loadImage("/items/heart_half.png", 1.5f);
        h_blank = gp.imageLoader.loadImage("/items/heart_blank.png", 1.5f);

        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/fonts/MaruMinya.ttf"));
            marMina = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 30);
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
        g2.setFont(marMina);
        g2.setColor(Color.WHITE);

        if (gp.mainState == GameState.MAIN_MENU) {
            drawMainMenuScreen(g2);
        }
        else if (gp.mainState == GameState.PAUSE) {
            if (gp.keyHandler.debug) {
                drawDebugScreen(g2);
            }
            d_PlayerLife(g2);
            drawPauseScreen(g2);
        }
        else if (gp.mainState == GameState.PLAY) {
            if (gp.keyHandler.debug) {
                drawDebugScreen(g2);
            }
            d_PlayerLife(g2);
            drawPlayScreen(g2);
        }
        else if (gp.mainState == GameState.DIALOGUE) {
            if (gp.keyHandler.debug) {
                drawDebugScreen(g2);
            }
            d_PlayerLife(g2);
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

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));

        g2.drawString("Player X: " + gp.player.worldX, x, gp.tileSize * 2);
        g2.drawString("Player Y: " + gp.player.worldY, x, gp.tileSize * 3);
        g2.drawString("Player Col: " + (worldX / gp.tileSize ), x, gp.tileSize * 4);
        g2.drawString("Player Row: " + (worldY / gp.tileSize), x, gp.tileSize * 5);
        g2.drawString("Player Direction: " + gp.player.direction, x, gp.tileSize * 6);
        // FPS AND UPS
        g2.setColor(Color.BLACK);
        g2.drawString("FPS: " + gp.avgFPS, gp.tileSize, gp.screenHeight - gp.tileSize * 2);
        g2.drawString("UPS: " + gp.avgUPS, gp.tileSize, gp.screenHeight - gp.tileSize);
    }

    private void d_PlayerLife(Graphics2D g2) {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while (i < gp.player.maxLife / 2) {
            g2.drawImage(h_blank, x, y, null);
            i++;
            x += (int) (gp.tileSize * 1.5f);
        }

        x = gp.tileSize / 2;
        i = 0;
        while (i < gp.player.life) {
            g2.drawImage(h_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(h_full, x, y, null);
            }

            i++;
            x += (int) (gp.tileSize * 1.5f);
        }
    }
}