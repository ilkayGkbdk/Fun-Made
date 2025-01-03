package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

        public Tile[] tiles;
        public int[][] indexMap;
        ArrayList<String> fileNames = new ArrayList<>();
        ArrayList<String> collisionStatus = new ArrayList<>();

        GamePanel gp;

        public TileManager(GamePanel gp_) {
            gp = gp_;

            // read tile data
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/data/tileData.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // getting tile name and collision info from tileData.txt
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    fileNames.add(line);
                    collisionStatus.add(br.readLine());
                }
                is.close();
                br.close();
            }
            catch (Exception e) {
                e.fillInStackTrace();
            }

            tiles = new Tile[fileNames.size()];
            setTileImages();

            // set the maxWorldCols and maxWorldRows
            is = Objects.requireNonNull(getClass().getResourceAsStream("/maps/map0.txt"));
            br = new BufferedReader(new InputStreamReader(is));

            try {
                String line2 = br.readLine();
                String[] maxTile = line2.split(" ");

                gp.maxWorldRows = maxTile.length;
                gp.maxWorldCols = maxTile.length;
                indexMap = new int[gp.maxWorldRows][gp.maxWorldCols];

                is.close();
                br.close();
            }
            catch (Exception e) {
                e.fillInStackTrace();
            }

            loadMap("/maps/map0.txt");
        }

        public void setTileImages() {
            for (int i = 0; i < fileNames.size(); i++) {
                String fileName;
                boolean isSolid;

                // get file name and collision status
                fileName = fileNames.get(i);
                isSolid = collisionStatus.get(i).equals("true");

                setup(i, isSolid, fileName);
            }
        }

        private void setup(int index, boolean isSolid, String path) {
            BufferedImage image = gp.imageLoader.loadImage("/tiles/" + path);
            tiles[index] = new Tile(isSolid, image);
        }

        public void loadMap(String path) {
            try {
                InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(path));
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                int col = 0;
                int row = 0;

                while (row < gp.maxWorldRows && col < gp.maxWorldCols) {
                    String line = br.readLine();

                    while (col < gp.maxWorldCols) {
                        String[] numbers = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);

                        indexMap[row][col] = num;
                        col++;
                    }

                    if (col == gp.maxWorldCols) {
                        col = 0;
                        row++;
                    }
                }
                is.close();
                br.close();
            }
            catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        public void draw(Graphics2D g2) {
            int worldCol = 0;
            int worldRow = 0;

            while (worldCol < gp.maxWorldCols && worldRow < gp.maxWorldRows) {
                int tileIndex = indexMap[worldRow][worldCol];
                Tile tile = tiles[tileIndex];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (gp.uTool.checkInPlayerScreen(worldX, worldY)) {
                    g2.drawImage(tile.image, screenX, screenY, null);
                }

                worldCol++;

                if (worldCol == gp.maxWorldCols) {
                    worldCol = 0;
                    worldRow++;
                }
            }
        }
}
