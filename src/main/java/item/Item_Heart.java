package item;

import main.GamePanel;

public class Item_Heart extends SuperItem {

    public Item_Heart(GamePanel gp_) {
        super(gp_);

        name = "Heart";
        images[0] = gp.imageLoader.loadImage("/items/heart_full.png");
        images[1] = gp.imageLoader.loadImage("/items/heart_half.png");
        images[2] = gp.imageLoader.loadImage("/items/heart_blank.png");
    }
}
