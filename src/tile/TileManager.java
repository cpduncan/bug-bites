package tile;

import main.GamePanel;
import main.ImagesetLoader;

import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.io.File;

public class TileManager {

      // DEPENDENCY INJECTION
      GamePanel gp;
      MapLoader mapLoader;
      ImagesetLoader imagesetLoader;

      Tile[] tileImagesDawn,
                  tileImagesDay,
                  tileImagesDusk,
                  tileImagesNight1,
                  tileImagesNight2,
                  tileImagesNight3;
      File map;
      int mapTile[][];

      public TileManager(GamePanel gp, MapLoader mapLoader, ImagesetLoader imagesetLoader, String mapFilename) {

            this.gp = gp;
            this.mapLoader = mapLoader;
            this.imagesetLoader = imagesetLoader;
            mapTile = new int[gp.maxWorldCol][gp.maxWorldRow];

            loadTileImages(imagesetLoader); // LOAD ALL TILES
            mapTile = mapLoader.loadMap(gp, mapFilename); // LOAD A MAP
      }

      private void loadTileImages(ImagesetLoader imagesetLoader) {
            imagesetLoader.loadTileset(tileImagesDawn = new Tile[52], "tileImagesDawn");
            imagesetLoader.loadTileset(tileImagesDay = new Tile[52], "tileImagesDay");
            imagesetLoader.loadTileset(tileImagesDusk = new Tile[52], "tileImagesDusk");
            imagesetLoader.loadTileset(tileImagesNight1 = new Tile[52], "tileImagesNight1");
            imagesetLoader.loadTileset(tileImagesNight2 = new Tile[52], "tileImagesNight2");
            imagesetLoader.loadTileset(tileImagesNight3 = new Tile[52], "tileImagesNight3");
      }

      public void draw(Graphics2D g2, int time) {
            int minCol = ((gp.player.worldX - gp.player.screenX) / gp.tileSize);
            int maxCol = ((gp.player.worldX + gp.player.screenX) / gp.tileSize) + 2;
            int minRow = ((gp.player.worldY - gp.player.screenY) / gp.tileSize);
            int maxRow = ((gp.player.worldY + gp.player.screenY) / gp.tileSize) + 2;
            for (int mapRow = minRow; mapRow < maxRow; mapRow++) {
                  for (int mapCol = minCol; mapCol < maxCol; mapCol++) {
                        if (gp.maxWorldRow - 1 < mapRow || mapRow < 0 || gp.maxWorldCol - 1 < mapCol || mapCol < 0) {
                              continue;
                        }
                        drawTile(g2, time, mapRow, mapCol, mapTile[mapCol][mapRow], mapCol * gp.tileSize,
                                    mapRow * gp.tileSize, mapCol * gp.tileSize - gp.player.worldX + gp.player.screenX,
                                    mapRow * gp.tileSize - gp.player.worldY + gp.player.screenY);
                  }
            }
      }

      private void drawTile(Graphics2D g2, int time, int mapRow, int mapCol, int tileId, int worldX, int worldY,
                  int screenX, int screenY) {
            BufferedImage finalImg = null;
            switch (time) {
                  case 0:
                        finalImg = tileImagesDawn[tileId].image;
                        break;
                  case 1:
                        finalImg = tileImagesDay[tileId].image;
                        break;
                  case 2:
                        finalImg = tileImagesDusk[tileId].image;
                        break;
                  case 3:
                        finalImg = getNightTile(tileId, worldX, worldY); // REFACTORED FOR (LIGHTING) CULLING
                        break;
                  default:
                        System.out.println("Time not valid, cant draw tiles");
                        break;
            }
            try {
                  g2.drawImage(finalImg, screenX, screenY, gp.tileSize, gp.tileSize, null);
                  if (finalImg == null) {
                        System.out.println("Tile " + mapRow + "," + mapCol + " null");
                  }
            } catch (Exception e) {
                  e.getStackTrace();
            }
      }

      private BufferedImage getNightTile(int tileNum, int worldX, int worldY) {
            double light1Radius = gp.tileSize * 1.5;
            double light2Radius = gp.tileSize * 2.5;
            if (worldX > gp.player.worldX - light1Radius &&
                        worldX < gp.player.worldX + light1Radius &&
                        worldY > gp.player.worldY - light1Radius &&
                        worldY < gp.player.worldY + light1Radius) {
                  return tileImagesNight1[tileNum].image;
            }
            if (worldX > gp.player.worldX - light2Radius &&
                        worldX < gp.player.worldX + light2Radius &&
                        worldY > gp.player.worldY - light2Radius &&
                        worldY < gp.player.worldY + light2Radius) {
                  return tileImagesNight2[tileNum].image;
            }
            return tileImagesNight3[tileNum].image;
      }
}