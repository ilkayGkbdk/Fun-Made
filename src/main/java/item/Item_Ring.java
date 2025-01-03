package item;

import main.GamePanel;

public class Item_Ring extends SuperItem{

    public Item_Ring(GamePanel gp_) {
        super(gp_);
        name = "Ring";
        image = gp.imageLoader.loadImage("/items/ring.png");
    }
}
