package main;

import entity.Entity;
import entity.Mob;
import entity.Player;
import item.SuperItem;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private final double limit = 60d;
    private final double updateRate = 1.0d / limit;
    private long nextStatTime;
    private int fps, ups;
    public int avgFPS, avgUPS;

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
    public Mob[] mobs = new Mob[10];
    public ArrayList<Entity> entityList = new ArrayList<>();

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

    @Override
    public void run() {
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (gameThread != null) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            while (accumulator > updateRate) {
                update();
                repaint();
                accumulator -= updateRate;
                ups++;
            }

            fps++;
            if (System.currentTimeMillis() > nextStatTime ) {
                avgFPS = fps;
                avgUPS = ups;
                fps = 0;
                ups = 0;
                nextStatTime = System.currentTimeMillis() + 1000;
            }
        }
    }

    public void update() {
        if (mainState == GameState.PLAY) {
            player.update();

            for (Mob mob : mobs) {
                if (mob != null) {
                    mob.update();
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

            makeEntityList();

            // Sort entities
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            emptyEntityList();
        }

        // ui
        ui.draw(g2);
    }

    public void makeEntityList() {
        entityList.add(player);

        for (Entity item : items) {
            if (item != null) {
                entityList.add(item);
            }
        }

        for (Entity mob : mobs) {
            if (mob != null) {
                entityList.add(mob);
            }
        }
    }

    public void emptyEntityList() {
        entityList.clear();
    }
}