package main;

import entity.NPC_Wizard;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp_) {
        this.gp = gp_;
    }

    public void setAssets() {

    }

    public void setEntities() {
        gp.entities[0] = new NPC_Wizard(gp);
        gp.entities[0].worldX = gp.tileSize * 45;
        gp.entities[0].worldY = gp.tileSize * 50;
    }
}
