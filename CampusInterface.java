
public interface CampusInterface {
  public void runCommandLoop();

  public void loadData(String filename);

  public void addBuilding(String building);

  public void removeBuilding(String building);

  public void addWalk(String fromBuilding, String toBuilding, Double distance);

  public void removeWalk(String fromBuilding, String toBuilding);

  public void searchShortestPath(String fromBuilding, String toBuilding);
  
  public void searchShortestPathLength(String fromBuilding, String toBuilding);

}
