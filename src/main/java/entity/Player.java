package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Objects;

public class Player extends Entity{

    public final int screenX, screenY;
    public int hasKey = 0;

    public Player(GamePanel gp_) {
        super(gp_);

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        setSprites("player", 8);
        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 35;
        worldY = gp.tileSize * 55;
        speed = 4;
        direction = "down";
        mainSprite = sprites[0][0];

        solidArea = new Rectangle(8, 16, 16, 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void update() {
        if (gp.keyHandler.up || gp.keyHandler.down || gp.keyHandler.left || gp.keyHandler.right) {
            if (gp.keyHandler.up) {
                direction = "up";
            }
            else if (gp.keyHandler.down) {
                direction = "down";
            }
            else if (gp.keyHandler.left) {
                direction = "left";
            }
            else if (gp.keyHandler.right) {
                direction = "right";
            }

            isColliding = gp.collisionHandler.checkTile(this);
            int index = gp.collisionHandler.checkItem(this, true);
            interactItem(index);

            super.move();

            spriteCounter++;
            if (spriteCounter == 6) {
                spriteCounter = 0;
                spriteIndex = (spriteIndex + 1) % 8; // Cycle through 8 sprites
            }

            super.setMainSprite();
        }
    }

    public void interactItem(int index) {
        if (index != -1) {
            if (Objects.equals(gp.items[index].name, "Key")) {
                hasKey++;
                gp.items[index] = null;
                gp.ui.setMessage("Key collected!");
            }
            else if (Objects.equals(gp.items[index].name, "Door")) {
                if (hasKey > 0) {
                    hasKey--;
                    gp.items[index] = null;
                    gp.ui.setMessage("Door unlocked!");
                }
                else {
                    gp.ui.setMessage("You need a key to unlock this door!");
                }
            }
            else if (Objects.equals(gp.items[index].name, "Ring")) {
                gp.player.speed += 2;
                gp.items[index] = null;
                gp.ui.setMessage("Speed increased!");
            }
            else if (Objects.equals(gp.items[index].name, "Chest")) {
                gp.ui.isGameOver = true;
                gp.gameThread = null;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(mainSprite, screenX, screenY, null);
    }
}
