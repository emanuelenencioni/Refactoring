import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import src.applicationModel.BusinessLogic;
import src.applicationModel.DomainModel;
import src.applicationModel.EndPoint;
import src.applicationModel.Entity;
import src.applicationModel.InputManager;
import src.applicationModel.Type;
import src.applicationModel.UseCase;
import src.manager.GraphManager;
import src.weightedGraph.Graph;

/**
 * 
 */
public class mainclass {

  /**
   * Default constructor
   */

  public static void main(String Args[]) {

    final String inputPath = "./input/";

    File folder = new File(inputPath);
    File[] listOfFiles = folder.listFiles();
    ArrayList<String> listOfDirectories = new ArrayList<>();


    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isDirectory()) {
        listOfDirectories.add(listOfFiles[i].getName() + "/");
      }
    }

    System.out.println("Choose an input:");
    for (int i = 0; i < listOfDirectories.size(); i++){
      System.out.println( i + ": " + listOfDirectories.get(i));
    }

    Scanner scan = new Scanner(System.in);
    boolean inputOk = false;

    int inputChoice = 0;
    while (!inputOk) {
      System.out.println("Enter a number between 0 and " + (listOfDirectories.size()-1) + ": ");
      if (!scan.hasNextInt()) {
        System.out.println("Input is not valid: not a number");
        scan.next();
      }
      else {
        inputChoice = scan.nextInt();
        if (inputChoice < 0 || inputChoice > (listOfDirectories.size()-1)){
          System.out.println("Input is not valid: number must be between 0 and " + (listOfDirectories.size()-1));
        }
        else{
          inputOk = true;
        }
      }
    }


    InputManager im = new InputManager(inputPath + listOfDirectories.get(inputChoice), scan);

    ArrayList<Entity> entityList = im.getEntityList();

    DomainModel domainModel = new DomainModel(entityList);

    ArrayList<EndPoint> endPointList = im.getEndPointList(entityList);

    ArrayList<UseCase> useCaseList = im.getUseCaseList(endPointList);

    BusinessLogic businessLogic = im.getBusinessLogic(useCaseList);

    GraphManager graphManager = new GraphManager(
                                                businessLogic,
                                                im.getWeight(Type.CC),
                                                im.getWeight(Type.CQ),
                                                im.getWeight(Type.QC),
                                                im.getWeight(Type.QQ),
                                                im.getSimplifyGraphStrategy(),
                                                im.getLossFucntionStrategy(),
                                                domainModel
                                                );

    Graph initialGraph = graphManager.getGraph();

    initialGraph.visualizeGraph();
    

    // SimplifyAndComputeLoss

    Float lossValue = graphManager.simplifyAndComputeLoss();

    if(lossValue == null){
      System.out.println("simplifyGraphStrategy or graph not set");
    }
    
    Graph finalGraph = graphManager.getSimplifiedGraph();

    finalGraph.visualizeGraph();

    System.out.println("The loss value for the simplification made is: " + lossValue);


    // MyBestSolution
    
    lossValue = graphManager.myBestSolution();

    Graph myBest = graphManager.getSimplifiedGraph();

    myBest.visualizeGraph();
    
    System.out.println("The loss value for the simplification made is: " + lossValue);


    // FindBestSolution
    
    lossValue = graphManager.findBestSolution();

    Graph findBest = graphManager.getSimplifiedGraph();

    findBest.visualizeGraph();
    
    System.out.println("The loss value for the simplification made is: " + lossValue);

  }

}