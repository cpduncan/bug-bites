package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.MapLoader;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

      final int originalTileSize = 16;
      public final int scale = 6;
      public final int tileSize = scale * originalTileSize;
      public final int maxScreenCol = 8;
      public final int maxScreenRow = 6;
      public final int screenWidth = maxScreenCol * tileSize;
      public final int screenHeight = maxScreenRow * tileSize;

      public int maxWorldCol;
      public int maxWorldRow;
      public int worldWidth = 96 * this.maxWorldCol;
      public int worldHeight = 576;

      public int time = 0;
      public int FPS = 60;

      TileManager tileManager = new TileManager(this, new MapLoader(), new ImagesetLoader(), "tiledMap2.tmx");
      KeyHandler keyHandler = new KeyHandler(this);
      Thread gameThread;
      public CollisionChecker colisionChecker = new CollisionChecker(this);
      public Player player = new Player(this, this.keyHandler, new ImagesetLoader("player"));

      public GamePanel() {
            // INITIALISATION
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setBackground(Color.black);
            this.setDoubleBuffered(true);
            this.addKeyListener(keyHandler);
            this.setFocusable(true);
      }

      public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
      }

      @Override
      public void run() {
            double frameTime = 1000000000 / FPS; // a 60th of a seccond
            double deltaTime = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long logTimer = 0;
            int drawCount = 0;

            while (gameThread != null) {

                  currentTime = System.nanoTime();
                  logTimer += (currentTime - lastTime);
                  deltaTime += (currentTime - lastTime) / frameTime;
                  lastTime = currentTime;

                  if (deltaTime >= 1) {
                        update();
                        repaint();

                        deltaTime--;
                        drawCount++;
                  }

                  if (logTimer >= 1000000000) {
                        logTimer = 0;
                        System.out.println("log fps:" + drawCount);
                        drawCount = 0;
                  }
            }
      }

      public void update() {

            player.update();

      }

      public void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            tileManager.draw(g2, time);

            player.draw(g2, time);

            g2.dispose();
      }
}