import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements the frontend for the CampusAppFD
 * 
 * @author junny
 *
 */
public class CampusAppFD implements CampusInterface {
  private Scanner userInput;
  private RouteFinderBackendInterface backend;

  /**
   * Initialize frontend to make use of a provided Scanner and RouteBackendFD.
   * 
   * @param userInput can be used to read input from use, or to read from files for testing
   * @param backend   placeholder by me, working implementation by Backend Developer
   */
  public CampusAppFD(Scanner userInput, RouteFinderBackendInterface backend) {
    this.userInput = userInput;
    this.backend = backend;
  }

  /**
   * Helper method to display a 79 column wide row of dashes: a horizontal rule.
   */
  private void hr() {
    System.out
        .println("-------------------------------------------------------------------------------");
  }

  /**
   * This loop repeated prompts the user for commands and displays appropriate feedback for each.
   * This continues until the user enters 'q' to quit.
   */
  public void runCommandLoop() {
    hr(); // display welcome message
    System.out.println("Welcome to the Campus Database!");
    hr();

    char command = '\0';
    while (command != 'Q') { // main loop continues until user chooses to quit
      command = this.mainMenuPrompt();
      switch (command) {
        case 'L': // System.out.println(" [L] - Load songs from file");
          System.out.print("Enter the name of the File to load: ");
          String filename = userInput.nextLine().trim();
          loadData(filename);
          break;
        case 'I': // System.out.println(" [I] - Input Building ");
          System.out.print("Enter the name of the building to add: ");
          String addBuilding = userInput.nextLine().trim();
          addBuilding(addBuilding);
          break;
        case 'B': // System.out.println(" [B] - Remove Building ");
          System.out.print("Enter the name of the building to remove: ");
          String removeBuilding = userInput.nextLine().trim();
          removeBuilding(removeBuilding);
          break;
        case 'W': // System.out.println(" [W] - Input Walk ");
          System.out.print("Enter the name of the building of departure: ");
          String fromBuilding = userInput.nextLine().trim();
          System.out.print("Enter the name of the building of arrival: ");
          String toBuilding = userInput.nextLine().trim();
          double dist = -1;
          while(dist <= 0.0){
              String distance = "";
              System.out.print("Enter the distance it is from the two buildings: ");
              distance = userInput.nextLine().trim();
              try {
                dist = Double.parseDouble(distance);
              } catch (Exception e) {
              }
          }

          addWalk(fromBuilding, toBuilding,dist);
          break;
        case 'R': // System.out.println("[R] - Remove Walk ");
          System.out.print("Enter the name of the building of departure: ");
          String fBuilding = userInput.nextLine().trim();
          System.out.print("Enter the name of the building of arrival: ");
          String tBuilding = userInput.nextLine().trim();
          removeWalk(fBuilding, tBuilding);
          break;
        case 'S': // System.out.println(" [S] - Search for shortest path between two buildings ");
          System.out.print("Enter the name of the building of departure: ");
          String foBuilding = userInput.nextLine().trim();
          System.out.print("Enter the name of the building of arrival: ");
          String aBuilding = userInput.nextLine().trim();
          searchShortestPath(foBuilding, aBuilding);
          break;
        case 'D': // System.out.println(" [[D] - Search for shortest path length between two buildings ");
          System.out.print("Enter the name of the building of departure: ");
          String forBuilding = userInput.nextLine().trim();
          System.out.print("Enter the name of the building of arrival: ");
          String arrBuilding = userInput.nextLine().trim();
          searchShortestPathLength(forBuilding, arrBuilding);
          break;
        case 'Q': // System.out.println(" [Q] - Quit app ");
          break;
        default:
          System.out.println(
              "Didn't recognize that command.  Please type one of the letters presented within []s to identify the command you would like to choose.");
          break;
      }
    }

    hr(); // thank user before ending this application
    System.out.println("Thank you for using the Campus Search App.");
    hr();
  }

  /**
   * Prints the command options to System.out and reads user's choice through userInput scanner.
   */
  public char mainMenuPrompt() {
    // display menu of choices
    System.out.println("Choose a command from the list below:");
    System.out.println("    [L] - Load Campus From File");
    System.out.println("    [I] - Add Building");
    System.out.println("    [B] - Remove Building");
    System.out.println("    [W] - Input Walk");
    System.out.println("    [R] - Remove Walk");
    System.out.println("    [S] - Search for shortest path between two buildings");
    System.out.println("    [D] - Search for shortest path length between two buildings");
    System.out.println("    [Q] - Quit app");

    // read in user's choice, and trim away any leading or trailing whitespace
    System.out.print("Choose command: ");
    String input = userInput.nextLine().trim();
    if (input.length() == 0) // if user's choice is blank, return null character
      return '\0';
    // otherwise, return an uppercase version of the first character in input
    return Character.toUpperCase(input.charAt(0));
  }

  /**
   * Prompt user to enter filename, and display error message when loading fails.
   */

  public void loadData(String filename) {
    try {
      backend.loadFile(filename);
    } catch (IOException e) {
      System.out.println("Error: Could not find or load file: " + filename);
    }

  }

  public void addBuilding(String building) {
    backend.addBuilding(building);
  }

  public void removeBuilding(String building) {
    backend.removeBuilding(building);
  }

  public void addWalk(String fromBuilding, String toBuilding, Double distance) {
    backend.addWalk(fromBuilding, toBuilding, distance);
  }

  public void removeWalk(String fromBuilding, String toBuilding) {
    backend.removeWalk(fromBuilding, toBuilding);
  }

  public void searchShortestPath(String fromBuilding, String toBuilding) {
      try{
          List<String> path = backend.getShortestPath(fromBuilding, toBuilding);
          System.out.println("The path from " + fromBuilding + " to " + toBuilding + " is: ");
          for(int i = 0; i < path.size(); i++){
              System.out.print( path.get(i));
              if(i != path.size()-1) System.out.print(", ");
          }
          System.out.println();
      }
      catch(Exception e){
          System.out.println("There is no path from " + fromBuilding + " to " + toBuilding);
      }
  }

  public void searchShortestPathLength(String fromBuilding, String toBuilding) {
      try{
          double cost = backend.getShortestPathLength(fromBuilding, toBuilding);
          System.out.println("The shortest path from " + fromBuilding + " to " + toBuilding + " is " + cost + " miles long");
      }
      catch(Exception e){
          System.out.println("There is no path from " + fromBuilding + " to " + toBuilding);
      }

  }

}
