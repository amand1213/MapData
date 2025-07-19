import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MapDisplay {
    String title;
    int screenWidth, screenHeight;

    double minLat, minLon, maxLat, maxLon, latRange, lonRange;

    JFrame frame = null;
    MapPanel mapPanel = null;

    public MapDisplay(String title, int width, int height, Double minLat, Double minLon, Double maxLat, Double maxLon) {
        this.title = title;
        this.screenWidth = width;
        this.screenHeight = height;

        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
        this.latRange = maxLat - minLat;
        this.lonRange = maxLon - minLon;
    }

    public void start() {
        frame = new JFrame(title);
        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapPanel = new MapPanel();
        frame.getContentPane().add(mapPanel);

        frame.setVisible(true);
    }

    public void displayAllWays(ArrayList<Way> ways) {
        for (Way way : ways) {
            ArrayList<Node> nodes = way.getNodes();

            Line currentLine = null;

            for (int i = 0; i < nodes.size(); i++) {
                Node currentNode = nodes.get(i);

                double lat = Double.parseDouble(currentNode.getLat());
                double lon = Double.parseDouble(currentNode.getLon());

                if (currentLine != null) {
                    currentLine.setEnd(lat, lon);
                    mapPanel.addLine(currentLine);
                }

                currentLine = new Line();
                currentLine.setStart(lat, lon);

                mapPanel.addNode(currentNode);
            }
        }
    }

    public void displayAllNodes(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            mapPanel.addNode(node);
        }
        mapPanel.repaint();
    }

    private class MapPanel extends JPanel {
        private ArrayList<Node> nodes = new ArrayList<>();
        private ArrayList<Line> lines = new ArrayList<>();

        public MapPanel() {
            setBackground(Color.BLACK);
            setOpaque(true);
        }

        public void addNode(Node node) {
            synchronized(nodes) {
                nodes.add(node);
            }
        }

        public void addLine(Line line) {
            synchronized(lines) {
                lines.add(line);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.YELLOW);
            synchronized (lines) {
                for (Line line : lines) {
                    drawLine(g2d, line);
                }
            }
            synchronized (nodes) {
                for (Node node : nodes) {
                    drawNode(g2d, node);
                }
            }
        }

        private int[] latLonToScreen(double lat, double lon) {
            int[] point = new int[2];

            point[0] = (int) Math.floor(((lon - minLon) / lonRange) * screenWidth);
            point[1] = (int) Math.floor(((maxLat - lat) / latRange) * screenHeight);

            return point;
        }

        private void drawNode(Graphics2D g2d, Node node) {
            try {
                double lat = Double.parseDouble(node.getLat());
                double lon = Double.parseDouble(node.getLon());

                int[] point = latLonToScreen(lat, lon);

                g2d.fillRect(point[0],point[1], 1, 1);
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinate: " + e.getMessage());
            }
        }
        private void drawLine(Graphics2D g2d, Line line) {
            double[] start = line.getStart();
            double[] end = line.getEnd();

            int[] pointStart = latLonToScreen(start[0], start[1]);
            int[] pointEnd = latLonToScreen(end[0], end[1]);
            g2d.drawLine(pointStart[0], pointStart[1], pointEnd[0], pointEnd[1]);
        }
    }
}
