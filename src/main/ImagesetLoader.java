package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tile.Tile;

public class ImagesetLoader {

      String filePrefix;
      String entity;

      public ImagesetLoader(String entity) {
            this.filePrefix = "sprite";
            this.entity = entity;
      }

      public ImagesetLoader() {
            this.filePrefix = "tile";
      }

      public void loadTileset(Tile[] tileArr, String arrName) {
            for (int i = 0; i < tileArr.length; i++) {
                  tileArr[i] = new Tile();
                  try {
                        tileArr[i].image = ImageIO
                                    .read(getClass()
                                                .getResourceAsStream(
                                                            "/res/" + arrName + "/" + filePrefix + (i + 1) + ".png"));
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            }
            tileArr[22].collision = false;
      }

      public void loadSpriteset(BufferedImage[] spriteArr, int time) {
            for (int i = 0; i < spriteArr.length; i++) {
                  try {
                        spriteArr[i] = ImageIO
                                    .read(getClass()
                                                .getResourceAsStream(
                                                            "/res/" + entity + "/" + time + "/" + filePrefix + (i + 1)
                                                                        + ".png"));
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
            }
      }
}
