package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {

    GamePanel gp;

    public ImageLoader(GamePanel gp_) {
        gp = gp_;
    }

    public BufferedImage loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResource(path)));
            BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, image.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(image, 0, 0, gp.tileSize, gp.tileSize, null);

            return scaledImage;
        }
        catch (IOException e) {
            e.fillInStackTrace();
            System.exit(1);
        }
        return null;
    }

    public BufferedImage loadImage(String path, float scalar) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResource(path)));
            BufferedImage scaledImage = new BufferedImage((int) (gp.tileSize * scalar), (int) (gp.tileSize * scalar), image.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(image, 0, 0, (int) (gp.tileSize * scalar), (int) (gp.tileSize * scalar), null);

            return scaledImage;
        }
        catch (IOException e) {
            e.fillInStackTrace();
            System.exit(1);
        }
        return null;
    }
}
