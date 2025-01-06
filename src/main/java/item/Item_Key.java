package item;

import main.GamePanel;

public class Item_Key extends SuperItem {

    public Item_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        images[0] = gp.imageLoader.loadImage("/items/key.png");
    }
}
