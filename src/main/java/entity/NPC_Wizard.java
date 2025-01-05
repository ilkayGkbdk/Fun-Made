package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Wizard extends Entity {

    public NPC_Wizard(GamePanel gp_) {
        super(gp_);

        setSprites("npc_wizard", 3);
        setDefaultValues();
        setDialogues();
    }

    public void setDefaultValues() {
        speed = 1;
        direction = "down";
        mainSprite = sprites[0][0];

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDialogues() {
        dialogues[0] = "Whoa! Who are you?";
        dialogues[1] = "Ohh.. I knew your father. What a brave \nmaster!";
        dialogues[2] = "I'm not looking that old huh? Haha! Kid, \ni almost 130 years old.";
        dialogues[3] = "Actually i knew your grandpa too but.. \nAnyway you had to help us!";
    }

    public void update() {
        super.update();

        spriteCounter++;
        if (spriteCounter == 10) {
            spriteCounter = 0;
            spriteIndex = (spriteIndex + 1) % 3; // Cycle through 3 sprites
        }
    }

    public void setAction() {
        Random random = new Random();

        if (actionLockCounter == 120) {
            actionLockCounter = 0;
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
        actionLockCounter++;
    }

    public void speak() {
        super.speak();
    }
}
