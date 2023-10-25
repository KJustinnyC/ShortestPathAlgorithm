// --== CS400 File Header Information ==--
// Name: Andrew Kim
// Email: akim98@wisc.edu
// Group and Team: CF: Red
// Group TA: Karen
// Lecturer: Florian
// Notes to Grader: 

import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
This class is the Algorithm Engineer's GraphDataStructure class
It represents a graph and uses Nodes and Edges to build the graph
Its special features is the dijkstra shortest path algorithm and finding a minimum spanning tree
**/
public class GraphDataStructureAE <NodeType, EdgeType extends Number> 
    implements GraphADT<NodeType, EdgeType>, GraphDataStructureInterface<NodeType,EdgeType>{

	/**
	* This is the node object that make up the vertecies in the graph
	* Contains the NodeType data of the Node and the list of edges entering as 
	* well as leaving the node.
	**/
	public class Node {
		public NodeType data;
		public List <Edge> edgesLeaving = new LinkedList<>();
		public List <Edge> edgesEntering = new LinkedList<>();
		public Node(NodeType data) { this.data = data; }
	}

	/**
	* The is the edge objet that connect node objects in the graph
	* Contains the weight of the connection, and the predessecor and successor nodes
	**/
	public class Edge {
		public EdgeType data;
		public Node predecessor;
		public Node successor;
		public Edge(EdgeType data, Node pred, Node succ) {
			this.data = data;
			predecessor = pred;
			successor = succ;
		}
	}

	// Hashtable of the nodes in the graph
	protected Hashtable<NodeType, Node> nodes = new Hashtable();

	// Private int variables for keeping track of the number of edges and nodes
	private int edgeCount;
	private int nodeCount;

	// Constructor of the GraphDataStructure class
	public GraphDataStructureAE() {
		edgeCount = 0;
		nodeCount = 0;
	}

	@Override
	/**
	* Insert a new node into the graph
	* @param data is the data to be stored in the new node
	* @return true if the data is unique, false if not
	* @throws NullPointerException if data null
	**/ 
	public boolean insertNode(NodeType data) {
		if(nodes.containsKey(data)) {
			return false;
		}
		nodes.put(data, new Node(data));
		nodeCount++;
		return true;
	}

	@Override 
	/**
	* @param NodeType data of the Node to remove
	* @return true if the node was removed from the graph successfuly
	**/
	public boolean removeNode(NodeType data) {
		// remove this node from nodes 
		if(!nodes.containsKey(data)) {
			return false;
		}
		Node oldNode = nodes.remove(data);
		// remove all edges entering neighboring nodes from this node
		for(Edge edge : oldNode.edgesLeaving) {
			edge.successor.edgesEntering.remove(edge);
			edgeCount --;
		}
		for(Edge edge : oldNode.edgesEntering) {
			edge.predecessor.edgesLeaving.remove(edge);
			edgeCount --;
		}
		nodeCount --;
		return true;
	}

	@Override
	/**
	* @param NodeType data of the Node to look for
	* @return true if the node is in the graph
	**/
	public boolean containsNode(NodeType data) {
		return nodes.containsKey(data);
	}

	@Override
	/**
	* @return the number of nodes in the graph
	**/
	public int getNodeCount() {
		return nodeCount;
	}

	@Override
	/**
	* Inset an edge into the graph
	* @param NodeType pred where the edge is from
	* @param NodeType succ where the edge is going to
	* @param EdgeType weight of the edge cost
	* @return true if insertion was successful
	**/
	public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight) {
		Node predNode = nodes.get(pred);
		Node succNode = nodes.get(succ);
		if(predNode == null || succNode == null) {
			return false;
		}
		try {
			// When an edge already exists in the graph
			Edge existingEdge = getEdgeHelper(pred, succ);
			existingEdge.data = weight;
		} catch(NoSuchElementException e) {
			// Crete a new edge
			Edge newEdge = new Edge(weight, predNode, succNode);
			this.edgeCount++;
			predNode.edgesLeaving.add(newEdge);
			succNode.edgesEntering.add(newEdge);
		}
		return true;
	}

	/**
	* Helper class to find edges within the graph
	* @param NodeType pred of the preceding node of the edge
	* @param NodeType succ of the succeding node of the edge
	* @return the Edge found
	* @throws NoSuchElementException if no such Edge is found
	**/
	protected Edge getEdgeHelper(NodeType pred, NodeType succ) {
		Node predNode = nodes.get(pred);
		for(Edge edge : predNode.edgesLeaving) {
			if(edge.successor.data.equals(succ)) {
				return edge;
			}
		}
		// Throw exception
		throw new NoSuchElementException("No edge");
	}
	
	@Override
	/**
	* Removes edge from the graph
	* @param NodeType pred of the preceding node of the edge
	* @param NodeType succ of the succeding node of the edge
	* @return true if the edge is successfuly removed from the graph
	**/
	public boolean removeEdge(NodeType pred, NodeType succ) {
		try {
			// Check if edge exists
			Edge oldEdge = getEdgeHelper(pred, succ);
			// Remove the edge from the lists
			oldEdge.predecessor.edgesLeaving.remove(oldEdge);
			oldEdge.successor.edgesEntering.remove(oldEdge);
			this.edgeCount--;
			return true;
		} catch(NoSuchElementException e) {
			// return false if the edge does not exist
			return false;
		}
	}

	@Override 
	/**
	* Checks if the edge is contained within the graph
	* @param NodeType pred of the preceding node of the edge
	* @param NodeType succ of the succeding node of the edge
	* @return true if the edge is contained within the graph
	**/
	public boolean containsEdge(NodeType pred, NodeType succ) {
		try {
			getEdgeHelper(pred, succ);
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}

	@Override 
	/**
	* @param NodeType pred of the preceding node of the edge
	* @param NodeType succ of the succeding node of the edge
	* @return the Edge in the graph
	* @throws NoSuchElementException if the edge is not within the graph
	**/
	public EdgeType getEdge(NodeType pred, NodeType succ) {
		return getEdgeHelper(pred, succ).data;	
	}

	@Override
	/**
	* Get the number of the edges within the graph
	* @return int of edgeCount
	**/
	public int getEdgeCount() {
		return edgeCount; 
	}

	/**
	* Helper class to implement dijkstra shortest path algorithm
	**/
	protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }
	
	/**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
	protected SearchNode computeShortestPath(NodeType start, NodeType end) {
		// Check that the start and end nodes are within the path
		if((!this.nodes.containsKey(start) || 
			!this.nodes.containsKey(end))) {
			throw new NoSuchElementException("There is either no start or end vertex");
		} 
		Node n = nodes.get(start);

		// Create the priority queue and add base path to start
		PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
		SearchNode path = new SearchNode(n, 0, null);
		pq.add(path);
		Hashtable<NodeType, Node> visited = new Hashtable<NodeType, Node>();
		visited.put(start, n);
		
		// Loop until Priority Queue is empty
		while(!pq.isEmpty()) {
			SearchNode p = pq.poll();
			visited.put(p.node.data, p.node);
			
			// If path most recent node is the end node, return path
			if(p.node.data.equals(end)) {
				return p;
			}
			
			// Loop through all of the edges leaving the most recent node in the path
			for(int i = 0; i < p.node.edgesLeaving.size(); i++) {
				// If a node has not been visited, add it to the priority queue
				if(!visited.containsKey(p.node.edgesLeaving.get(i).successor.data)) {
					SearchNode toAdd = new SearchNode(p.node.edgesLeaving.get(i).successor, 
						(double) p.node.edgesLeaving.get(i).data.doubleValue() + p.cost, p);
					pq.add(toAdd);
				}
			}
		}
		
		// If the priortiy queue has no more elements and no path has been found, throw exception
		throw new NoSuchElementException("There is no path from start to end");
    }

	@Override
	/**
	* Finds the shortest path from two Nodes in the graph using computeShortestPath method
	* @param NodeType start of the path
	* @param NodeType end of the path
	* @return List<NodeType> of nodes that make up the path
	* @throws NoSuchElementException if there is no possible path between the two nodes
	**/
	public List<NodeType> shortestPathData(NodeType start, NodeType end) {
		// Run the shortest path algorithm
    	List<NodeType> path = new LinkedList<NodeType>();
    	SearchNode result = computeShortestPath(start, end);
		// Add all of the elements into a list to return
    	while(result != null) {
    		path.add(result.node.data);
    		result = result.predecessor;
    	}
		// Reverse the order so that the path goes from start -> finish
    	Collections.reverse(path);
        return path;
	}

	@Override
	/**
	* Finds the total cost of the shortest path between two nodes
	* @param NodeType start of the path
	* @param NodeType end of the path
	* @return double of the total cost of the path
	* @throws NoSuchElementException if there is no path between the nodes
	**/
	public double shortestPathCost(NodeType start, NodeType end) {
    	SearchNode result = computeShortestPath(start, end);
        return result.cost;
	}

	/**
	* Use Prims Algorithm to find the minimum spanning tree of an undirected graph
	* @param NodeType start of where to start the minimum spanning tree
	* @return List<Edge> that make up the minimum spanning tree
	* @throws NoSuchElementException if there are certain nodes that cannot be reached
	**/
	public List<EdgeData<NodeType,EdgeType>> getMST(NodeType start) {
		// Check if the Graph is empty
		if(nodes.size() <= 1) {
			return null;
		}
		// Check that the node exists within the graph
		if(!containsNode(start)) {
			throw new NoSuchElementException();
		}
		
		// Create lists and hashtable for keeping track of visited and possible edge connections
		ArrayList<Node> frontier = new ArrayList<>();
		ArrayList<EdgeData<NodeType,EdgeType>> mst = new ArrayList<>();
		Hashtable<NodeType, Node> visited = new Hashtable<NodeType, Node>();
		frontier.add(nodes.get(start));	
		visited.put(nodes.get(start).data, nodes.get(start));
		
		// Run until all the of the nodes have been visited
		while(visited.size() < nodes.size()) {
			Edge edge = null;
            EdgeData<NodeType,EdgeType> toAdd = null;
			int minEdge = 99999;
			// Find the smallest edge that is currently available from the nodes that have 
			// already been visited
			for(int i = 0; i < frontier.size(); i++) {
				for(int k = 0; k < frontier.get(i).edgesLeaving.size(); k++) {
					Node n = frontier.get(i);
					if( (Integer) n.edgesLeaving.get(k).data < minEdge &&
						!visited.containsKey(n.edgesLeaving.get(k).successor.data)) {
						edge = n.edgesLeaving.get(k);
						minEdge = (Integer) n.edgesLeaving.get(k).data;
                        toAdd = new EdgeData<NodeType,EdgeType>(edge.predecessor.data,edge.successor.data,edge.data);
					}
				}
			}
			
			if(edge != null) {
				mst.add(toAdd);
				frontier.add(edge.successor);
				visited.put(edge.successor.data, edge.successor);
			} else {
				throw new NoSuchElementException("There are no possible connections to certain nodes");
			}
			
		}
		
		return mst;
	}

	/**
	* Find the total distance of the minimum spanning tree
	* @return the total distance 
	* @throws NoSuchElementException
	**/
	public double totalDistance(NodeType start) {
		double totalDistance = 0;
		List<EdgeData<NodeType,EdgeType>> list = new ArrayList<>();
		list = getMST(start);
		for(EdgeData<NodeType,EdgeType> e : list) {
			totalDistance += (Double) e.weight;
		}
		return totalDistance;
	}
	
}

