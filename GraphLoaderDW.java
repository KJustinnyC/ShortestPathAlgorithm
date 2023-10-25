import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GraphLoaderDW implements GraphLoaderInterface {

    public ArrayList<String> loadDOTNodes(String filepath) throws IOException{
        HashMap<String,Boolean> seen = new HashMap<String,Boolean>();
        ArrayList<EdgeData<String,Double>> edges = loadDOTEdges(filepath);
        ArrayList<String> result = new ArrayList<String>();
        for(EdgeData<String,Double> edge : edges){
            if(!seen.containsKey(edge.from)){
                result.add(edge.from);
                seen.put(edge.from,true);
            }
            if(!seen.containsKey(edge.to)){
                result.add(edge.to);
                seen.put(edge.to,true);
            }
        }
        return result;

    }

	public ArrayList<EdgeData<String,Double>> loadDOTEdges(String filepath) throws IOException{
        ArrayList<EdgeData<String,Double>> result = new ArrayList<>();
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
                EdgeData<String,Double> data = new EdgeData<String,Double>(firstNode,secondNode,Double.valueOf(numWeight));
                result.add(data);
            }
        }
        return result;
    }
}
