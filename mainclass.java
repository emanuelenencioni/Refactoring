import java.util.ArrayList;

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
  public static void some(Integer a) {
    a = a + 1;
  }

  public static void main(String Args[]) {

    InputManager im = new InputManager();

    ArrayList<Entity> entityList = im.getEntityList();

    DomainModel domainModel = new DomainModel(entityList);

    ArrayList<EndPoint> endPointList = im.getEndPointList(entityList);

    ArrayList<UseCase> useCaseList = im.getUseCaseList(endPointList);

    BusinessLogic businessLogic = im.getBusinessLogic(useCaseList);

    System.out.println(businessLogic.getID() + " " + businessLogic.getFrequency());

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
    
    Float lossValue = graphManager.simplifyAndComputeLoss();
    
    Graph graph = graphManager.getSimplifiedGraph();

    graph.visualizeGraph();

    System.out.println("The loss value for the simplification made is: " + lossValue);

    // JSON GENERATOR
    // ArrayList<Entity> enList = new ArrayList<>();
    // for (int i = 0; i < 4; i++) {
    //   enList.add(new Entity("en" + i));
    // }

    // ArrayList<Coupling> cpList = new ArrayList<>();


    // for (Entity r : enList) {
    //   for (Entity c : enList) {
    //     if (!r.equals(c)) {

    //       switch (r.getName()) {
    //         case "en0":
    //           cpList.add(new Coupling(r, c, Type.CC, 1.f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.CC + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.3,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.CQ, 1.f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.CQ + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.3,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.QC, 1.f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.QC + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.3,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.QQ, 1.f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.QQ + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.3,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           break;

    //         case "en1":
    //           cpList.add(new Coupling(r, c, Type.CC, 0.5f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.CC + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.8,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.CQ, 0.5f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.CQ + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.8,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.QC, 0.5f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.QC + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.8,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.QQ, 0.5f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.QQ + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.8,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           break;

    //         case "en2":
    //           cpList.add(new Coupling(r, c, Type.CC, 0.25f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.CC + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.4,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.CQ, 0.25f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.CQ + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.4,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.QC, 0.25f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.QC + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.4,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("},");
    //           cpList.add(new Coupling(r, c, Type.QQ, 0.25f));
    //           System.out.println("{");
    //           System.out.println(" \"type\" : \"" + Type.QQ + "\",");
    //           System.out.println(" \"coOccurrence\" : 0.4,");
    //           System.out.println(" \"entity1\" : \"" + r.getName() + "\",");
    //           System.out.println(" \"entity2\" : \"" + c.getName() + "\"");
    //           System.out.println("}");
    //           break;

    //         default:
    //           break;
    //       }

    //     }

    //   }

    // }

    // InputManager im = new InputManager();

    // ArrayList<Entity> result = im.getEntities();

    // for (Entity e : result) {
    //   System.out.println(e.getName());
    // }

    // ArrayList<Coupling> couplingsResult = im.getCouplings(result);

    // if (couplingsResult == null) {
    //   System.err.println("Error in coupling");
    // }

    // Entity x = new Entity("prova");
    // DomainModel dm = new DomainModel();
    // dm.addEntity(x);
    // x = new Entity("bo");
    // System.out.println(x.getName());
    // Integer z = 10;
    // some(z);
    // System.out.println(z);

  }

}