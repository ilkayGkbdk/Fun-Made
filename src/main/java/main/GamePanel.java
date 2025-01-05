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

    // States
    public GameState mainState;

    // Game Elements
    public ImageLoader imageLoader = new ImageLoader(this);
    public UtilityTool uTool = new UtilityTool(this);
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // Entities
    public Player player = new Player(this);
    public SuperItem[] items = new SuperItem[10];
    public Entity[] entities = new Entity[10];

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void setup() {
        assetSetter.setAssets();
        assetSetter.setEntities();
        mainState = GameState.MAIN_MENU;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public double avgFPS;
    public double drawCount;

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                avgFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (mainState == GameState.PLAY) {
            player.update();

            for (Entity entity : entities) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (mainState != GameState.MAIN_MENU) {
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
        }

        // ui
        ui.draw(g2);
    }
}