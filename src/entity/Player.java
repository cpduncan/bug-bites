package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.ImagesetLoader;
import main.KeyHandler;

public class Player extends Entity {

      GamePanel gp;
      KeyHandler keyHandler;
      ImagesetLoader imagesetLoader;

      public final int screenX;
      public final int screenY;

      BufferedImage[] sprites0, sprites1, sprites2, sprites3;

      public Player(GamePanel gp, KeyHandler keyHandler, ImagesetLoader imagesetLoader) {

            this.gp = gp;
            this.keyHandler = keyHandler;
            this.imagesetLoader = imagesetLoader;

            screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
            screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

            solidArea = new Rectangle();
            solidArea.x = 8;
            solidArea.y = 16;
            solidArea.width = 32;
            solidArea.height = 32;

            setDefaultValues();
            loadSprites(imagesetLoader);

      }

      public void setDefaultValues() {
            worldX = gp.tileSize * 2; // STARTING POINT X
            worldY = gp.tileSize * 2; // STARTING POINT Y
            speed = (gp.tileSize / 26 * (60 / gp.FPS));
            direction = "down";
      }

      public void loadSprites(ImagesetLoader imagesetLoader) {
            imagesetLoader.loadSpriteset(sprites0 = new BufferedImage[8], 0);
            imagesetLoader.loadSpriteset(sprites1 = new BufferedImage[8], 1);
            imagesetLoader.loadSpriteset(sprites2 = new BufferedImage[8], 2);
            imagesetLoader.loadSpriteset(sprites3 = new BufferedImage[8], 3);
      }

      public void update() {
            if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
                  if (keyHandler.upPressed) {
                        direction = "up";
                        worldY -= speed;
                  } else if (keyHandler.downPressed) {
                        direction = "down";
                        worldY += speed;
                  } else if (keyHandler.rightPressed) {
                        direction = "right";
                        worldX += speed;
                  } else if (keyHandler.leftPressed) {
                        direction = "left";
                        worldX -= speed;
                  }

                  collisionOn = false;
                  gp.colisionChecker.checkTile(this);

                  spriteCounter++;
                  if (spriteCounter > 8 / (60 / gp.FPS)) {
                        if (spriteNum == 0) {
                              spriteNum++;
                        } else if (spriteNum == 1) {
                              spriteNum--;
                        }
                        spriteCounter = 0;
                  }
            }
      }

      public void draw(Graphics2D g2, int time) {
            g2.drawImage(getSprite(direction, time), screenX, screenY, gp.tileSize, gp.tileSize, null);
      }

      private BufferedImage getSprite(String direction, int time) {
            switch (time) {
                  case 0:
                        return getSprite(direction, sprites0);
                  case 1:
                        return getSprite(direction, sprites1);
                  case 2:
                        return getSprite(direction, sprites2);
                  case 3:
                        return getSprite(direction, sprites3);
                  default:
                        System.out.println("player time invalid");
                        return null;
            }
      }

      private BufferedImage getSprite(String direction, BufferedImage[] spriteArr) {
            switch (direction) {
                  case "down":
                        return spriteArr[0 + spriteNum];
                  case "left":
                        return spriteArr[2 + spriteNum];
                  case "right":
                        return spriteArr[4 + spriteNum];
                  case "up":
                        return spriteArr[6 + spriteNum];
                  default:
                        System.out.println("player direction invalid");
                        return null;
            }
      }
}
