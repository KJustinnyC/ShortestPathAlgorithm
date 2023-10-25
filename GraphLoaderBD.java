import java.util.ArrayList;
import java.io.IOException;

public class GraphLoaderBD implements GraphLoaderInterface{
	public ArrayList<String> loadDOTNodes(String filepath) throws IOException{
        if(filepath.equals("BAD")){
            throw new IOException("MOCK FILE NOT FOUND EXCEPTION");
        }
        ArrayList<String> result = new ArrayList<String>();
        result.add("a");
        result.add("b");
        result.add("c");
        result.add("d");
        result.add("e");
        return result;
    }
	public ArrayList<EdgeData<String,Double>> loadDOTEdges(String filepath) throws IOException{
        if(filepath.equals("BAD")){
            throw new IOException("MOCK FILE NOT FOUND EXCEPTION");
        }
        ArrayList<EdgeData<String,Double>> result = new ArrayList<>();
        result.add(new EdgeData("a","b",1.0));
        result.add(new EdgeData("b","c",2.0));
        result.add(new EdgeData("d","d",3.0));
        result.add(new EdgeData("d","e",4.0));
        return result;
    }
}
