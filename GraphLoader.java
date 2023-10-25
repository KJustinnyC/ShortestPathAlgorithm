import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GraphLoader implements GraphLoaderInterface {

    /**
     * Loads the Nodes in a DOT file
     * Calls loadDOTEdges first before extracting the nodes and filtering for dupes
     */
    public ArrayList<String> loadDOTNodes(String filepath) throws IOException{
        ArrayList<String> result = new ArrayList<String>();
        HashMap<Integer, String> hash = new HashMap<Integer, String>(100);
        ArrayList<EdgeData<String,Double>> edges = loadDOTEdges(filepath);
        for(EdgeData<String,Double> edge: edges){
            String firstNode = edge.from;
            String secondNode = edge.to;
            if(hash.get(firstNode.hashCode()) == null){
                hash.put(firstNode.hashCode(), firstNode);
                result.add(firstNode);
            }
            if(hash.get(secondNode.hashCode()) == null){
                hash.put(secondNode.hashCode(), secondNode);
                result.add(secondNode);
            }
        }

        return result;
    }

	public ArrayList<EdgeData<String,Double>> loadDOTEdges(String filepath) throws IOException{
        ArrayList<EdgeData<String,Double>> result = new ArrayList<EdgeData<String,Double>>();
        HashMap<String, GraphEdgeInterface> hash = new HashMap<String, GraphEdgeInterface>(100);
        Scanner dotFile = new Scanner(new File(filepath));

        while(dotFile.hasNext()){
            String line = dotFile.nextLine();
            if(line.contains("--")){
                //Processing first node in line
                int index = line.indexOf("--", 0);
                String firstNode = line.substring(1, index);
                firstNode = firstNode.strip();
                //System.out.println("First Node: " + firstNode);
                
                //processing second node in line
                int stopIndex = line.indexOf("[", 0);
                String secondNode = "";
                if(stopIndex == -1){
                    secondNode = line.substring(index+3);
                }else{
                    secondNode = line.substring(index+3, stopIndex);
                }
                secondNode = secondNode.strip();
                //System.out.println("Second Node: " + secondNode);

                //processing weight of edge
                int weightIndex = line.indexOf("distance");
                int weightEndIndex = line.indexOf("]");
                int numWeight;
                String weight = line.substring(weightIndex+9, weightEndIndex);
                //System.out.println("Weight: " + weight);
                try{
                    numWeight = Integer.parseInt(weight);
                }catch(NumberFormatException e){
                    System.out.println("Error occurred when processing weights. DOT file may be incorrectly formatted or corrupted. Aborting.");
                    return null;
                }
                
                //Constructs final edge object and adds it to the list. If there is already an edge with the same pred and succ nodes, update the weight
                GraphNode firstGraphNode = new GraphNode(firstNode);
                GraphNode secondGraphNode = new GraphNode(secondNode);
                GraphEdge edge = new GraphEdge(firstGraphNode, secondGraphNode, numWeight);
                String hashSearch = firstNode.concat(secondNode);
                if(hash.get(hashSearch) == null){
                    hash.put(hashSearch, edge);
                }else{
                    GraphEdgeInterface temp = hash.get(hashSearch);
                    temp.setWeight(edge.weight);
                }
            }
        }

        for(GraphEdgeInterface edge: hash.values()){
            result.add(new EdgeData<String,Double>(edge.getPred().getName(), edge.getSucc().getName(), (double)edge.getWeight()));
        }
        return result;
    }
}
