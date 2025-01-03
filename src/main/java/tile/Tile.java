package tile;

import java.awt.image.BufferedImage;

public class Tile {

    public boolean isSolid;
    public BufferedImage image;

    public Tile(boolean isSolid_, BufferedImage image_) {
        isSolid = isSolid_;
        image = image_;
    }
}
