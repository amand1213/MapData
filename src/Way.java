import java.util.ArrayList;

public class Way {
    String id;
    String uid;

    ArrayList<String> nodeIds = new ArrayList<String>();
    ArrayList<Tag> tags = new ArrayList<Tag>();
    ArrayList<Node> nodes = new ArrayList<Node>();

    public Way(String id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public void addNodeId(String id) {
        nodeIds.add(id);
    }

    public void addTag(String k, String v) {
        tags.add(new Tag(k,v));
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public ArrayList<String> getNodeIds() {
        return nodeIds;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    public void clearRefs() {
        nodeIds = null;
    }
}
