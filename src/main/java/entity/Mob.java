package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mob extends Entity {

    public int speed;
    public String direction = "down";

    public int maxLife;
    public int life;

    public BufferedImage[][] sprites;
    public BufferedImage mainSprite;
    public int spriteIndex = 0;
    public int spriteCounter = 0;

    public int actionLockCounter = 0;

    public boolean isColliding = false;

    public String[] dialogues = new String[10];
    public int dialogueIndex = 0;

    public Mob(GamePanel gp_) {
        super(gp_);
    }

    public void setSprites(String entityName, int spriteCount) {
        sprites = new BufferedImage[4][spriteCount];
        String[] directions = {"down", "up", "left", "right"};

        try {
            for (int i = 0; i < directions.length; i++) {
                for (int j = 0; j < spriteCount; j++) {
                    String path = String.format("/%s/walk/%s%d.png", entityName, directions[i], j + 1);
                    sprites[i][j] = gp.imageLoader.loadImage(path);
                }
            }
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void setAction() {}

    public void update() {
        setAction();

        isColliding = gp.collisionHandler.checkTile(this);
        gp.collisionHandler.checkItem(this, false);
        gp.collisionHandler.checkMob(this, new Mob[]{gp.player});

        move();
        setMainSprite();
    }

    void move() {
        if (!isColliding) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
    }

    void setMainSprite() {
        switch (direction) {
            case "down":
                mainSprite = sprites[0][spriteIndex];
                break;
            case "up":
                mainSprite = sprites[1][spriteIndex];
                break;
            case "left":
                mainSprite = sprites[2][spriteIndex];
                break;
            case "right":
                mainSprite = sprites[3][spriteIndex];
                break;
        }
    }

    public void setDialogues() {}

    public void speak() {
        faceToPlayer();
        gp.ui.currentDialogue = dialogues[dialogueIndex];

        dialogueIndex++;
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
    }

    private void faceToPlayer() {
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.uTool.checkInPlayerScreen(worldX, worldY)) {
            g2.drawImage(mainSprite, screenX, screenY, null);
        }
    }
}
