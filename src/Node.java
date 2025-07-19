import java.util.LinkedList;

public class Node {
    String id;
    String uid;
    String lat;
    String lon;
    LinkedList<Tag> tags = new LinkedList<Tag>();

    public Node(String id, String uid, String lat, String lon){
        this.id = id;
        this.uid = uid;
        this.lat = lat;
        this.lon = lon;
    }

    public void addTag(String k, String v) {
        tags.add(new Tag(k,v));
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}