package main;

import entity.Entity;
import item.SuperItem;

public class CollisionHandler {

    GamePanel gp;

    public CollisionHandler(GamePanel gp_) {
        gp = gp_;
    }

    public boolean checkTile(Entity entity) {
        boolean collision = false;

        int leftX = entity.worldX + entity.solidArea.x;
        int rightX = leftX + entity.solidArea.width;
        int topY = entity.worldY + entity.solidArea.y;
        int bottomY = topY + entity.solidArea.height;

        int leftCol = leftX / gp.tileSize;
        int rightCol = rightX / gp.tileSize;
        int topRow = topY / gp.tileSize;
        int bottomRow = bottomY / gp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "up":
                topRow = (topY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[topRow][leftCol];
                tileNum2 = gp.tileManager.indexMap[topRow][rightCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[bottomRow][leftCol];
                tileNum2 = gp.tileManager.indexMap[bottomRow][rightCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
            case "left":
                leftCol = (leftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[topRow][leftCol];
                tileNum2 = gp.tileManager.indexMap[bottomRow][leftCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
            case "right":
                rightCol = (rightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.indexMap[topRow][rightCol];
                tileNum2 = gp.tileManager.indexMap[bottomRow][rightCol];
                if (gp.tileManager.tiles[tileNum1].isSolid || gp.tileManager.tiles[tileNum2].isSolid) {
                    collision = true;
                }
                break;
        }

        return collision;
    }

    public int checkItem(Entity entity, boolean isPlayer) {
        int itemIndex = -1;

        for (int i = 0; i < gp.items.length; i++) {
            SuperItem item = gp.items[i];
            if (item != null) {
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                item.solidArea.x += item.worldX;
                item.solidArea.y += item.worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(item.solidArea)) {
                    if (item.isSolid) {
                        entity.isColliding = true;
                    }
                    if (isPlayer) {
                        itemIndex = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                item.solidArea.x = item.solidAreaDefaultX;
                item.solidArea.y = item.solidAreaDefaultY;
            }
        }

        return itemIndex;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int entityIndex = -1;

        for (int i = 0; i < target.length; i++) {
            Entity targetEntity = target[i];
            if (targetEntity != null) {
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                targetEntity.solidArea.x += targetEntity.worldX;
                targetEntity.solidArea.y += targetEntity.worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(targetEntity.solidArea)) {
                    entity.isColliding = true;
                    entityIndex = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                targetEntity.solidArea.x = targetEntity.solidAreaDefaultX;
                targetEntity.solidArea.y = targetEntity.solidAreaDefaultY;
            }
        }

        return entityIndex;
    }
}
