package item;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperItem {

    GamePanel gp;

    public String name;
    public BufferedImage image;
    public boolean isSolid = true;

    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public SuperItem(GamePanel gp_) {
        gp = gp_;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.uTool.checkInPlayerScreen(worldX, worldY)) {
            g2.drawImage(image, screenX, screenY, null);
        }
    }
}
