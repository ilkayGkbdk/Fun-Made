package main;

import entity.NPC_Wizard;
import item.Item_Chest;
import item.Item_Door;
import item.Item_Key;
import item.Item_Ring;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp_) {
        this.gp = gp_;
    }

    public void setAssets() {
        gp.items[0] = new Item_Key(gp);
        gp.items[0].worldX = gp.tileSize * 75;
        gp.items[0].worldY = gp.tileSize * 60;

        gp.items[1] = new Item_Key(gp);
        gp.items[1].worldX = gp.tileSize * 59;
        gp.items[1].worldY = gp.tileSize * 69;

        gp.items[2] = new Item_Ring(gp);
        gp.items[2].worldX = gp.tileSize * 37;
        gp.items[2].worldY = gp.tileSize * 72;

        gp.items[3] = new Item_Door(gp);
        gp.items[3].worldX = gp.tileSize * 59;
        gp.items[3].worldY = gp.tileSize * 48;

        gp.items[4] = new Item_Door(gp);
        gp.items[4].worldX = gp.tileSize * 59;
        gp.items[4].worldY = gp.tileSize * 38;

        gp.items[5] = new Item_Chest(gp);
        gp.items[5].worldX = gp.tileSize * 40;
        gp.items[5].worldY = gp.tileSize * 33;
    }

    public void setEntities() {
        gp.entities[0] = new NPC_Wizard(gp);
        gp.entities[0].worldX = gp.tileSize * 45;
        gp.entities[0].worldY = gp.tileSize * 50;
    }
}
