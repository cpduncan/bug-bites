package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
      public int worldX, worldY;
      public int speed;
      public BufferedImage up1time0, up2time0, down1time0, down2time0, left1time0, left2time0, right1time0, right2time0,
                  up1time3, up2time3, down1time3, down2time3, left1time3, left2time3, right1time3, right2time3;
      public String direction;
      public int spriteCounter = 0;
      public int spriteNum = 0;
      public Rectangle solidArea;
      public boolean collisionOn = false;
}