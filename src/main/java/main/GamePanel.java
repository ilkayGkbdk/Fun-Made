package main;

import entity.Entity;
import entity.Player;
import item.SuperItem;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    public final int tileSize = 32;
    public final int screenCols = 32;
    public final int screenRows = 18;

    public final int screenWidth = tileSize * screenCols;
    public final int screenHeight = tileSize * screenRows;

    // World Settings
    public int maxWorldCols;
    public int maxWorldRows;

    // Game Variables
    public Thread gameThread;
    private final int FPS = 60;

    // Game Elements
    public ImageLoader imageLoader = new ImageLoader(this);
    public UtilityTool uTool = new UtilityTool(this);
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler();
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // Entities
    public Player player = new Player(this);
    public SuperItem[] items = new SuperItem[10];
    public Entity[] entities = new Entity[10];

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.PINK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void setup() {
        assetSetter.setAssets();
        assetSetter.setEntities();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
        }
    }

    public void update() {
        // Update game state here
        for (Entity entity : entities) {
            if (entity != null) {
                entity.update();
            }
        }

        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Draw game elements here

        // tiles
        tileManager.draw(g2);

        // items
        for (SuperItem item : items) {
            if (item != null) {
                item.draw(g2);
            }
        }

        // entities
        for (Entity entity : entities) {
            if (entity != null) {
                entity.draw(g2);
            }
        }

        player.draw(g2);

        // ui
        ui.draw(g2);
    }
}