import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppFDTest {
  RouteFinderBackendInterface _backend;
  GraphDataStructureInterface _graph;
  GraphLoaderInterface _loader;
  GraphDataStructureInterface _instance;
  /*
   * Creates a fresh redblacktree before each test
   */
  @BeforeEach
  public void createInstance(){
      _instance = new GraphDataStructureAE<String,Double>();
      _graph = new GraphDataStructureBD();
      _loader = new GraphLoaderBD();
      _backend = new RouteFinderBackend(_loader,_graph);
  }

  // This test checks whether or not the load building method works
  @Test
  public void test1() {
    TextUITester tester = new TextUITester("L\ndata/small.txt\nI\nBuildinga\n \nQ");
    Scanner scn = new Scanner(System.in);
    RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
    CampusInterface frontend = new CampusAppFD(scn, backend);
    frontend.runCommandLoop();
    String out = tester.checkOutput();
    boolean check = out.contains("Building");
    assertEquals(true, check);
  }

  // This test checks whether or not the add walk works,
  @Test
  public void test2() {
    TextUITester tester = new TextUITester("L\ndata/small.txt\nS\nBuildinga\nBuilding2\n \nQ");
    Scanner scn = new Scanner(System.in);
    RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
    CampusInterface frontend = new CampusAppFD(scn, backend);
    frontend.runCommandLoop();
    String out = tester.checkOutput();
    boolean check = out.contains("Building");
    assertEquals(true, check);
  }

  // This test checks whether or not the remove walk works,
  @Test
  public void test3() {
    TextUITester tester = new TextUITester("L\ndata/small.txt\nS\nBuildinga\nBuilding2\n \nQ");
    Scanner scn = new Scanner(System.in);
    RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
    CampusInterface frontend = new CampusAppFD(scn, backend);
    frontend.runCommandLoop();
    String out = tester.checkOutput();
    boolean check = out.contains("Building");
    assertEquals(true, check);
  }

  // This test checks whether or not the remove building works,
  @Test
  public void test4() {
    TextUITester tester = new TextUITester("L\ndata/small.txt\nB\nBuildinga\n \nQ");
    Scanner scn = new Scanner(System.in);
    RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
    CampusInterface frontend = new CampusAppFD(scn, backend);
    frontend.runCommandLoop();
    String out = tester.checkOutput();
    boolean check = out.contains("Building");
    assertEquals(true, check);
  }

  // This test checks whether or not the get shortest path works or not
  @Test
  public void test5() {
    TextUITester tester = new TextUITester("L\ndata/small.txt\nS\nBuildinga\nBuilding2\n \nQ");
    Scanner scn = new Scanner(System.in);
    RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
    CampusInterface frontend = new CampusAppFD(scn, backend);
    frontend.runCommandLoop();
    String out = tester.checkOutput();
    boolean check = out.contains("Building");
    assertEquals(true, check);
  }
  
  // This test checks whether or not the get shortest path length works or not
  @Test
  public void test6() {
    TextUITester tester = new TextUITester("L\ndata/small.txt\nB\nBuildinga\n \nQ");
    Scanner scn = new Scanner(System.in);
    RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
    CampusInterface frontend = new CampusAppFD(scn, backend);
    frontend.runCommandLoop();
    String out = tester.checkOutput();
    boolean check = out.contains("Building");
    assertEquals(true, check);
  }

//Testing DataWranglers functionality
  @Test
  public void CodeReviewOfDataWrangler(){
    GraphNode node1 = new GraphNode("pred");
    GraphNode node2 = new GraphNode("succ");
    GraphEdge edge = new GraphEdge(node1, node2, 0);
    assertEquals(node1, edge.getPred());
    assertEquals(node2, edge.getSucc());
    assertEquals(0, edge.getWeight());
  }
  
// Testing Algorithm Engineer's method of creating a graph
       @Test
        public void CodeReviewOfAlgorithmEngineer() {
      // Initialize graph
         GraphDataStructureAE graph = new GraphDataStructureAE();

         // Create graph
         graph.insertNode("A");
         graph.insertNode("B");
         graph.insertNode("C");
         graph.insertNode("D");
         graph.insertNode("E");
         graph.insertNode("F");
         graph.insertNode("H");
         graph.insertNode("I");
         graph.insertNode("J");
         graph.insertEdge("A", "B", 4);
         graph.insertEdge("A", "C", 8);
         graph.insertEdge("B", "C", 3);
         graph.insertEdge("B", "D", 8);
         graph.insertEdge("C", "E", 7);
         graph.insertEdge("C", "F", 1);
         graph.insertEdge("D", "E", 5);
         graph.insertEdge("D", "I", 4);
         graph.insertEdge("D", "H", 7);
         graph.insertEdge("E", "F", 6);
         graph.insertEdge("F", "I", 2);
         graph.insertEdge("H", "I", 5);
         graph.insertEdge("H", "J", 9);
         graph.insertEdge("I", "J", 10);

         // Test Dijkstra Algorithm Results
         List<Object> result = new LinkedList<>();
         double costResult = 0;
         result = graph.shortestPathData("A", "E");
         costResult = graph.shortestPathCost("A", "E");
         assertEquals(costResult, 14);
         assertEquals(result.get(0), "A");
         assertEquals(result.get(1), "B");
         assertEquals(result.get(2), "C");
         assertEquals(result.get(3), "E");

         // Test Dijkstra Algorithm Results
         result = graph.shortestPathData("A", "I");
         costResult = graph.shortestPathCost("A", "I");
         assertEquals(costResult, 10);
         assertEquals(result.size(), 5);
         assertEquals(result.get(0), "A");
         assertEquals(result.get(1), "B");
         assertEquals(result.get(2), "C");
         assertEquals(result.get(3), "F");
         assertEquals(result.get(4), "I");
        }
       
       
       //Integration Test that uses DW and BD
       @Test
        public void IntegrationTest1() {
         TextUITester tester = new TextUITester("L\nexample.dot\nI\nBuildinga\n \nQ");
         Scanner scn = new Scanner(System.in);
         RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
         CampusInterface frontend = new CampusAppFD(scn, backend);
         frontend.runCommandLoop();
         String out = tester.checkOutput();
         boolean check = out.contains("Building");
         assertEquals(true, check);
       }
     //Second Integration Test that uses DW and BD
       @Test
        public void IntegrationTest2() {
         TextUITester tester = new TextUITester("L\nexample.dot\nI\nSOmethingLes\n \nQ");
         Scanner scn = new Scanner(System.in);
         RouteFinderBackendInterface backend = new RouteFinderBackend(_loader, _instance);
         CampusInterface frontend = new CampusAppFD(scn, backend);
         frontend.runCommandLoop();
         String out = tester.checkOutput();
         boolean check = out.contains("Building");
         assertEquals(true, check);
       }

}

