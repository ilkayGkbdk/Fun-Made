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

    public void drawCommandBar(Graphics2D g2, int keyIndex, int x, int y) {
        if (gp.ui.commandNum == keyIndex) {
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawDialogueBox(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRect(x, y, width, height);
        int margin = 5;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3f));
        g2.drawRect(x + margin, y + margin, width - margin * 2, height - margin * 2);
    }
}
