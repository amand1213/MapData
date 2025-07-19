import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class OsmParser {
    File file;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc;

    String minlat;
    String minlon;
    String maxlat;
    String maxlon;

    public OsmParser(String filePath) {
        try{
            file = new File(filePath);
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();

            // Get Bounds
            NodeList boundsList = doc.getElementsByTagName("bounds");

            Element bounds = (Element) boundsList.item(0);
            minlat = bounds.getAttribute("minlat");
            minlon = bounds.getAttribute("minlon");
            maxlat = bounds.getAttribute("maxlat");
            maxlon = bounds.getAttribute("maxlon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        System.out.println("Getting Nodes...");
        
        // Get all <node> elements
        NodeList nodeList = doc.getElementsByTagName("node");

        //System.out.println("Number of <node> elements: " + nodeList.getLength());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeElement = (Element) nodeList.item(i);

            String id = nodeElement.getAttribute("id");
            String uid = nodeElement.getAttribute("uid");
            String lat = nodeElement.getAttribute("lat");
            String lon = nodeElement.getAttribute("lon");

            //System.out.println("Node ID: " + id + " | lat: " + lat + " | lon: " + lon + " | user: " + user);
            Node node = new Node(id,uid,lat,lon);
            // If the node contains <tag> children, process them
            NodeList tagList = nodeElement.getElementsByTagName("tag");
            for (int j = 0; j < tagList.getLength(); j++) {
                Element tag = (Element) tagList.item(j);
                String key = tag.getAttribute("k");
                String value = tag.getAttribute("v");
                //System.out.println("  Tag: " + key + " = " + value);
                node.addTag(key, value);
            }
            nodes.add(node);
        }
        System.out.println("Done");
        return nodes;
    }

    public ArrayList<Way> getWays() {
        ArrayList<Way> ways = new ArrayList<>();
        System.out.println("Getting Ways...");

        // Get all <node> elements
        NodeList wayList = doc.getElementsByTagName("way");

        //System.out.println("Number of <way> elements: " + wayList.getLength());

        for (int i = 0; i < wayList.getLength(); i++) {
            Element nodeElement = (Element) wayList.item(i);

            String id = nodeElement.getAttribute("id");
            String uid = nodeElement.getAttribute("uid");

            Way way = new Way(id,uid);

            // Get Node ids
            NodeList nodeList = nodeElement.getElementsByTagName("nd");
            for (int k = 0; k < nodeList.getLength(); k++) {
                Element node = (Element) nodeList.item(k);
                String refId = node.getAttribute("ref");
                way.addNodeId(refId);
            }

            // If the node contains <tag> children, process them
            NodeList tagList = nodeElement.getElementsByTagName("tag");
            for (int j = 0; j < tagList.getLength(); j++) {
                Element tag = (Element) tagList.item(j);
                String key = tag.getAttribute("k");
                String value = tag.getAttribute("v");
                //System.out.println("  Tag: " + key + " = " + value);
                way.addTag(key, value);
            }
            ways.add(way);
        }
        System.out.println("Done");
        return ways;
    }

    public String getMinLat() {
        return minlat;
    }
    public String getMinLon() {
        return minlon;
    }
    public String getMaxLat() {
        return maxlat;
    }
    public String getMaxLon() {
        return maxlon;
    }
}   