import java.util.List;
public class GraphDataStructureBD<NodeType, EdgeType extends Number> implements GraphDataStructureInterface<NodeType,EdgeType> {

    private int nodeCount;
    private int edgeCount;
    public GraphDataStructureBD(){
        nodeCount =0;
        edgeCount = 0;
    }
	public boolean insertNode(NodeType data){
        if(data.equals("BAD")) return false;
        nodeCount++;
        return true;
    }
	public boolean removeNode(NodeType data){
        if(data.equals("BAD")) return false;
        nodeCount--;
        return true;
    }
    public boolean containsNode(NodeType data){
        if(data.equals("BAD")) return false;
        return true;
    }
	public int getNodeCount(){
        return nodeCount;
    }
	public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight){
        if(pred.equals("BAD")) return false;
        edgeCount++;
        return true;
    }
	public boolean removeEdge(NodeType pred, NodeType succ){
        if(pred.equals("BAD")) return false;
        edgeCount--;
        return true;
    }
	public boolean containsEdge(NodeType pred, NodeType succ){
        if(pred.equals("BAD")) return false;
        return true;
    }
	public EdgeType getEdge(NodeType pred, NodeType succ){
        return null;
    }
	public int getEdgeCount(){
        return edgeCount;
    }
	public List<NodeType> shortestPathData(NodeType start, NodeType end){
        return null;
    }
	public double shortestPathCost(NodeType start, NodeType end){
        return 0.0;
    }
	
    // New for GraphDataStructureInterface
    public List<EdgeData<NodeType,EdgeType>> getMST(NodeType start){
        return null;
    }       // Return edges of MST
    public double totalDistance(NodeType start){
        return 10.0;
    }// Return total distance of all edges
}

