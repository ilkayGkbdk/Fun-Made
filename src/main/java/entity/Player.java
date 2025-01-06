package entity;

import main.GamePanel;
import main.GameState;

import java.awt.*;

public class Player extends Mob {

    public final int screenX, screenY;

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

        maxLife = 8;
        life = maxLife;

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
            int itemIndex = gp.collisionHandler.checkItem(this, true);
            int mobIndex = gp.collisionHandler.checkMob(this, gp.mobs);
            pickUpItem(itemIndex);
            interactEntity(mobIndex);

            super.move();

            spriteCounter++;
            if (spriteCounter == 6) {
                spriteCounter = 0;
                spriteIndex = (spriteIndex + 1) % 8; // Cycle through 8 sprites
            }

            super.setMainSprite();
        }
    }

    public void pickUpItem(int index) {
        if (index != -1) {

        }
    }

    public void interactEntity(int index) {
        if (index != -1) {
            if (gp.keyHandler.enterPressed) {
                gp.mainState = GameState.DIALOGUE;
                gp.mobs[index].speak();
                life--;
            }
        }
        gp.keyHandler.enterPressed = false;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(mainSprite, screenX, screenY, null);
    }
}
