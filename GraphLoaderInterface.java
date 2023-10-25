import java.util.ArrayList;
import java.io.IOException;
public interface GraphLoaderInterface{
	public ArrayList<String> loadDOTNodes(String filepath) throws IOException;
	public ArrayList<EdgeData<String,Double>> loadDOTEdges(String filepath) throws IOException;
}

