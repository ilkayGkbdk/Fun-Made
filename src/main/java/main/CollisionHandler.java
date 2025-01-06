package main;

import entity.Entity;
import entity.Mob;
import item.SuperItem;

public class CollisionHandler {

    GamePanel gp;

    public CollisionHandler(GamePanel gp_) {
        gp = gp_;
    }

    public boolean checkTile(Mob mob) {
        boolean collision = false;

        int leftX = mob.worldX + mob.solidArea.x;
        int rightX = leftX + mob.solidArea.width;
        int topY = mob.worldY + mob.solidArea.y;
        int bottomY = topY + mob.solidArea.height;

        int leftCol = leftX / gp.tileSize;
        int rightCol = rightX / gp.tileSize;
        int topRow = topY / gp.tileSize;
        int bottomRow = bottomY / gp.tileSize;

        int tileNum1, tileNum2;
        switch (mob.direction) {
            case "up":
                topRow = (topY - mob.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[topRow][leftCol];
                tileNum2 = gp.tileManager.indexMap[topRow][rightCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + mob.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[bottomRow][leftCol];
                tileNum2 = gp.tileManager.indexMap[bottomRow][rightCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
            case "left":
                leftCol = (leftX - mob.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[topRow][leftCol];
                tileNum2 = gp.tileManager.indexMap[bottomRow][leftCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
            case "right":
                rightCol = (rightX + mob.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[topRow][rightCol];
                tileNum2 = gp.tileManager.indexMap[bottomRow][rightCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
        }

        return collision;
    }

    public int checkItem(Mob mob, boolean isPlayer) {
        int itemIndex = -1;

        for (int i = 0; i < gp.items.length; i++) {
            SuperItem item = gp.items[i];
            if (item != null) {
                mob.solidArea.x += mob.worldX;
                mob.solidArea.y += mob.worldY;
                item.solidArea.x += item.worldX;
                item.solidArea.y += item.worldY;

                switch (mob.direction) {
                    case "up":
                        mob.solidArea.y -= mob.speed;
                        break;
                    case "down":
                        mob.solidArea.y += mob.speed;
                        break;
                    case "left":
                        mob.solidArea.x -= mob.speed;
                        break;
                    case "right":
                        mob.solidArea.x += mob.speed;
                        break;
                }

                if (mob.solidArea.intersects(item.solidArea)) {
                    if (item.isSolid) {
                        mob.isColliding = true;
                    }
                    if (isPlayer) {
                        itemIndex = i;
                    }
                }

                mob.solidArea.x = mob.solidAreaDefaultX;
                mob.solidArea.y = mob.solidAreaDefaultY;
                item.solidArea.x = item.solidAreaDefaultX;
                item.solidArea.y = item.solidAreaDefaultY;
            }
        }

        return itemIndex;
    }

    public int checkMob(Mob mob, Mob[] target) {
        int mobIndex = -1;

        for (int i = 0; i < target.length; i++) {
            Entity targetEntity = target[i];
            if (targetEntity != null) {
                mob.solidArea.x += mob.worldX;
                mob.solidArea.y += mob.worldY;
                targetEntity.solidArea.x += targetEntity.worldX;
                targetEntity.solidArea.y += targetEntity.worldY;

                switch (mob.direction) {
                    case "up":
                        mob.solidArea.y -= mob.speed;
                        break;
                    case "down":
                        mob.solidArea.y += mob.speed;
                        break;
                    case "left":
                        mob.solidArea.x -= mob.speed;
                        break;
                    case "right":
                        mob.solidArea.x += mob.speed;
                        break;
                }

                if (mob.solidArea.intersects(targetEntity.solidArea)) {
                    mob.isColliding = true;
                    mobIndex = i;
                }

                mob.solidArea.x = mob.solidAreaDefaultX;
                mob.solidArea.y = mob.solidAreaDefaultY;
                targetEntity.solidArea.x = targetEntity.solidAreaDefaultX;
                targetEntity.solidArea.y = targetEntity.solidAreaDefaultY;
            }
        }

        return mobIndex;
    }
}
