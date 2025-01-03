package main;

import java.awt.*;

public class UtilityTool {

    GamePanel gp;

    public UtilityTool(GamePanel gp_) {
        gp = gp_;
    }

    public boolean checkInPlayerScreen(int worldX, int worldY) {
        return worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize;
    }

    public int getXForCenteredText(String text, Graphics2D g2) {
        return gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
    }
}
