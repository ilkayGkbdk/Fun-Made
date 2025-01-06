package item;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperItem extends Entity {

    public String name;
    public BufferedImage[] images = new BufferedImage[10];
    public boolean isSolid = true;

    public SuperItem(GamePanel gp_) {
        super(gp_);

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.uTool.checkInPlayerScreen(worldX, worldY)) {
            g2.drawImage(images[0], screenX, screenY, null);
        }
    }
}
