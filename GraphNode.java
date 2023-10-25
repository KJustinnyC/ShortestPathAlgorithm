import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing a node in a graph
 */
public class GraphNode implements GraphNodeInterface {
    String name;
    ArrayList<GraphEdgeInterface> nodeEdges;
    HashMap<Integer, GraphEdge> hash;

    /**
     * Constructor for the node
     * @param name the name of the node
     */
    public GraphNode(String name){
        this.name = name;
        nodeEdges = new ArrayList<GraphEdgeInterface>();
        hash = new HashMap<Integer, GraphEdge>(100);
    }

    /**
     * Getter for name
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for nodeEdges
     */
    public ArrayList<GraphEdgeInterface> getEdges(){
        return nodeEdges;
    }

    /**
     * Adds edges to nodeEdges, skipping any that are duplicates
     * Duplicates determined via hashCode()
     */
    public void addEdges(GraphEdge[] edges){
        for(GraphEdge edge: edges){
            if(hash.get(edge.hashCode()) == null){
                nodeEdges.add(edge);
                hash.put(edge.hashCode(), edge);
            }
        }
    }

    /**
     * Setter for name
     */
    public void setName(String newName){
        name = newName;
    }

    public String toString(){
        return name;
    }
}
