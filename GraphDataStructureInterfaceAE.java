// --== CS400 File Header Information ==--
// Name: Andrew Kim
// Email: akim98@wisc.edu
// Group and Team: CF: Red
// Group TA: Karen
// Lecturer: Florian
// Notes to Grader: 

import java.util.List;
import java.util.NoSuchElementException;

public interface GraphDataStructureInterfaceAE <NodeType, EdgeType extends Number>  
	extends GraphADT<NodeType, EdgeType> {
	
	public boolean insertNode(NodeType data);
	public boolean removeNode(NodeType data);
	public boolean containsNode(NodeType data);
	public int getNodeCount();
	public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight);
	public boolean removeEdge(NodeType pred, NodeType succ);
	public boolean containsEdge(NodeType pred, NodeType succ);
	public EdgeType getEdge(NodeType pred, NodeType succ);
	public int getEdgeCount();
	public List<NodeType> shortestPathData(NodeType start, NodeType end);
	public double shortestPathCost(NodeType start, NodeType end);
	public List<Object> getMST(NodeType start);
	public int totalDistance(NodeType start);
}
