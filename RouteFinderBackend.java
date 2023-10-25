import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class RouteFinderBackend implements RouteFinderBackendInterface {

    private GraphLoaderInterface loader;
    private GraphDataStructureInterface<String, Double> graph;
    public RouteFinderBackend(GraphLoaderInterface loader, GraphDataStructureInterface<String,Double> graph){
        this.loader = loader;
        this.graph = graph;
    }
    /**
     * Uses the algorithm engineer's getNodeCount() and getEdgeCount() methods to get info on the
     * graph to be used by the Frontend. For example, if there are 5 nodes and 4 edge, then this
     * method would return "Number of Buildings: 5\nNumber of Walks: 4\n"
     * 
     * @return a string representation of the number of nodes and edges in the graph
     */
    public String getInfo(){
        return "Number of Buildings: " + graph.getNodeCount() + 
            "\nNumber of Walks: " + graph.getEdgeCount() +
            "\n";
    }

    /**
     * Uses the Data Wrangler's GraphLoader class to load nodes and edges into the graph
     * 
     * @param file the filepath to the DOT file
     */
    public void loadFile(String file) throws IOException{
        ArrayList<String> nodes = loader.loadDOTNodes(file);
        ArrayList<EdgeData<String,Double>> edges = loader.loadDOTEdges(file);
        for(String node : nodes){
            graph.insertNode(node);
        }
        for(EdgeData<String,Double> edge : edges){
            graph.insertEdge(edge.from, edge.to, edge.weight);
        }
    }

    /**
     * Finds how long it will take to get from one building to another in minutes
     * 
     * @param start the building (String) that you start at
     * @param end the building (String) you want to get to quickly
     * @return the minimum number of minutes it takes to get to the end from the start
     */
    public double getShortestPathLength(String start, String end){
        return graph.shortestPathCost(start,end);
    }

    /**
     * Gets the path from building to building of the shortest path to get from one building to
     * another. For example, to get to Union South from Chamberlin, it might say
     * "Chamberlin, Discovery Building, Union South"
     * 
     * @param start the building (String) that you start at
     * @param end the building (String) you want to get to quickly
     * @return a string list of the buildings you need to go to in order to get to the end
     */
    public List<String> getShortestPath(String start, String end){
        return graph.shortestPathData(start,end);
    }

    /**
     * Adds a new building to the graph using the algorithm engineer's insertNode() method
     * 
     * @param building the building to add
     * @return true if the building was successfully inserted, false otherwise
     */
    public boolean addBuilding(String building){
        return graph.insertNode(building);
    }

    /**
     * Adds a walk between two buildings in the graph, allowing custom specification of the time it
     * takes to walk
     * 
     * @param from the building the edge starts at
     * @param to the building the edge ends at
     * @param weight how much time it takes to walk the edge
     * @return true if the edge was successfully added, false otherwise
     */
    public boolean addWalk(String from, String to, double time){
        return graph.insertEdge(from,to,time);
    }

    /**
     * Removes a building from the graph
     * 
     * @param building the building to remove
     * @return true if the building was successfully removed, false otherwise
     */
    public boolean removeBuilding(String building){
        return graph.removeNode(building);
    }

    /**
     * Removes the walk between the two specified buildings
     * 
     * @param from the building the walk starts from
     * @param to the building the walk ends at
     * @return true if the walk was successfully removed, false otherwise
     */
    public boolean removeWalk(String from, String to){
        return graph.removeEdge(from,to);
    }

}
