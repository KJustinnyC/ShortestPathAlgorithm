import java.util.Scanner;

public class App{
    public static void main(String[] args){
        GraphLoaderInterface loader = new GraphLoaderDW();
        GraphDataStructureInterface<String,Double> graph = new GraphDataStructureAE<String,Double>();
        RouteFinderBackendInterface backend =new RouteFinderBackend(loader,graph);
        Scanner userInput = new Scanner(System.in);
        CampusAppFD frontend = new CampusAppFD(userInput,backend);
        frontend.runCommandLoop();
    }
}
