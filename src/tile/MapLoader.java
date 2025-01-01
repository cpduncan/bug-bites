package tile;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import main.GamePanel;

public class MapLoader {

      Document document;

      public void initTmxReader(String fileName) {
            try {
                  InputStream inputStream = getClass().getClassLoader().getResourceAsStream("res/maps/" + fileName);

                  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                  DocumentBuilder builder = factory.newDocumentBuilder();
                  document = builder.parse(inputStream);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                  e.printStackTrace();
            }
      }

      public int[][] loadMap(GamePanel gp, String fileName) {

            initTmxReader(fileName);

            NodeList layers = document.getElementsByTagName("layer");
            Element layer = (Element) layers.item(0);
            gp.maxWorldRow = Integer.parseInt(layer.getAttribute("height"));
            gp.maxWorldCol = Integer.parseInt(layer.getAttribute("width"));

            NodeList datas = document.getElementsByTagName("data");
            Element data = (Element) datas.item(0);

            String[] ids = data.getTextContent().split(",");

            int[][] finalIds = new int[gp.maxWorldCol][gp.maxWorldRow];
            int col = 0;
            int row = 0;
            int i = 0;
            while (row < gp.maxWorldRow) {
                  while (col < gp.maxWorldCol) {
                        finalIds[col][row] = Integer.parseInt(ids[i].replaceAll("\\D", "")) - 1;
                        i++;
                        col++;
                  }
                  col = 0;
                  row++;
            }
            return finalIds;
      }
}