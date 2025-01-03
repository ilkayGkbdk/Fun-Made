package item;

import main.GamePanel;

public class Item_Key extends SuperItem {

    public Item_Key(GamePanel gp) {
        super(gp);
        name = "Key";
        image = gp.imageLoader.loadImage("/items/key.png");
    }
}
