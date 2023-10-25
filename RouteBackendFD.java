import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class RouteBackendFD implements RouteBackendInterface{

  @Override
  public String getInfo() {
    return "info";
  }

  @Override
  public void loadFile(String nodeFile, String edgeFile) throws FileNotFoundException {
    if(nodeFile.equals("errorTrigger")){
      throw new FileNotFoundException("Error Triggered");
    }
    if(edgeFile.equals("errorTrigger")){
      throw new FileNotFoundException("Error Triggered");
    }
  }

  @Override
  public double getShortestPathLength(String start, String end) {
    return 10.0;
  }

  @Override
  public List<String> getShortestPath(String start, String end) {
    ArrayList<String> temp = new ArrayList<String>();
    temp.add(start);
    temp.add(end);
    return temp;
  }

  @Override
  public boolean addBuilding(String building) {
    ArrayList<String> temp = new ArrayList<String>();
    temp.add(building);
    return true;
  }

  @Override
  public boolean addWalk(String from, String to, double time) {
    ArrayList<String> temp = new ArrayList<String>();
    temp.add(from);
    temp.add(to);
    return true;
  }

  @Override
  public boolean removeBuilding(String building) {
    return true;
  }

  @Override
  public boolean removeWalk(String from, String to) {
    return true;
  }

}
