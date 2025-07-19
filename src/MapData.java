import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MapData {
    public static void main(String[] args) {
        try {
            int screenWidth = 1280;
            int screenHeight = 720;

            OsmParser osmParser = new OsmParser("C:/Users/Alex/Downloads/test.osm");

            double minLat = Double.parseDouble(osmParser.getMinLat());
            double minLon = Double.parseDouble(osmParser.getMinLon());
            double maxLat = Double.parseDouble(osmParser.getMaxLat());
            double maxLon = Double.parseDouble(osmParser.getMaxLon());

            MapDisplay display = new MapDisplay("Map", screenWidth, screenHeight, minLat, minLon, maxLat, maxLon);
            ArrayList<Node> nodes = osmParser.getNodes();
            ArrayList<Way> ways = osmParser.getWays();

            // Loop through ways
            for (int i = 0; i < ways.size(); i++) {
                Way currentWay = ways.get(i);
                ArrayList<String> nodeIds = currentWay.getNodeIds();

                // Loop through node Ids in the way
                for (int j = 0; j < nodeIds.size(); j++) {
                    String currentRef = nodeIds.get(j);

                    // Find the node for the id
                    for (int k = 0; k < nodes.size(); k++) {
                        Node currentNode = nodes.get(k);
                        String currentId = currentNode.id;
                        if (currentRef.equals(currentId)) {
                            currentWay.addNode(currentNode);
                        }
                    }
                }

                currentWay.clearRefs();
            }
            System.out.println("Starting Display...");
            display.start();

            display.displayAllWays(ways);

        } catch (NumberFormatException e) {
                System.out.println("Invalid string format for integer conversion");
                return;
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}