import java.util.ArrayList;

public interface GraphNodeInterface{
	public String getName();
	public ArrayList<GraphEdgeInterface> getEdges(); //rename depending on proposal
	public void addEdges(GraphEdge[] edges); //rename depending on proposal
	public void setName(String newName);
    public String toString();
}
