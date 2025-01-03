package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Wizard extends Entity {

    int counter = 0;

    public NPC_Wizard(GamePanel gp_) {
        super(gp_);

        setSprites("npc_wizard", 3);
        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
        mainSprite = sprites[0][0];

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void update() {
        super.update();

        spriteCounter++;
        if (spriteCounter == 10) {
            spriteCounter = 0;
            spriteIndex = (spriteIndex + 1) % 3; // Cycle through 8 sprites
        }
    }

    public void setAction() {
        Random random = new Random();

        if (counter == 120) {
            counter = 0;
            int direction = random.nextInt(4);

            switch (direction) {
                case 0:
                    this.direction = "up";
                    break;
                case 1:
                    this.direction = "down";
                    break;
                case 2:
                    this.direction = "left";
                    break;
                case 3:
                    this.direction = "right";
                    break;
            }
        }
        counter++;
    }
}
