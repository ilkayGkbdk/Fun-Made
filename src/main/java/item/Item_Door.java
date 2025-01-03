package item;

import main.GamePanel;

public class Item_Door extends SuperItem {

    public Item_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        image = gp.imageLoader.loadImage("/items/door.png");
    }
}
