package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public GamePanel gp;

    public int worldX, worldY;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public Entity(GamePanel gp_) {
        gp = gp_;
    }

    public void draw(Graphics2D g2) {}
}
