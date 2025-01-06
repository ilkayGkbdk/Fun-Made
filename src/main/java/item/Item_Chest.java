package item;

import main.GamePanel;

public class Item_Chest extends SuperItem{

    public Item_Chest(GamePanel gp) {
        super(gp);

        name = "Chest";
        images[0] = gp.imageLoader.loadImage("/items/chest.png");
    }
}
