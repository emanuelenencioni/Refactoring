package src.applicationModel;

import java.util.ArrayList;
// import java.util.Scanner;
// import java.util.regex.Pattern;
// import java.io.File;
// import java.io.FileNotFoundException;



import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import src.manager.LossFunctionFactory;
import src.manager.LossFunctionStrategy;
import src.manager.LossFunctionType;
import src.manager.SimplifyGraphFactory;
import src.manager.SimplifyGraphStrategy;
import src.manager.SimplifyGraphType;


public class InputManager {
    

    public InputManager(){

        buildMatricesFactory = new BuildMatricesFactory();
        simplifyGraphFactory = new SimplifyGraphFactory();

    }

    
    public ArrayList<Entity> getEntityList(){

        JSONParser parser = new JSONParser();
        ArrayList<Entity> entityList = new ArrayList<Entity>();
    
        try {

            JSONArray inputEntityList = (JSONArray) parser.parse(new FileReader(inputPath + entities));

            for (Object e : inputEntityList){

                String entityName = (String) e;
                Entity entity = new Entity(entityName);
                entityList.add(entity);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return entityList;

    }
    

    public ArrayList<EndPoint> getEndPointList(ArrayList<Entity> entityList){

        ArrayList<EndPoint> endPointList = new ArrayList<EndPoint>();

        JSONParser parser = new JSONParser();
    
        try {

            JSONArray inputEndPoints = (JSONArray) parser.parse(new FileReader(inputPath + endPoints));

            for (Object e : inputEndPoints){
                
                JSONObject epObject = (JSONObject) e;

                // ID
                String ID = (String) epObject.get("ID");

                // FREQUENCY
                double fd = (double) epObject.get("frequency");
                float frequency = (float) fd;

                // COUPLING LIST
                JSONArray cl = (JSONArray) epObject.get("coupList");
                ArrayList<Coupling> coupList = new ArrayList<Coupling>();


                for (Object c : cl){

                    JSONObject coupObject = (JSONObject) c;

                    // ENTITY1 & ENTITY2
                    String entityName1 = (String) coupObject.get("entity1");
                    String entityName2 = (String) coupObject.get("entity2");

                    int idEntity1 = -1;
                    int idEntity2 = -1;

                    Entity entity1 = null;
                    Entity entity2 = null;

                    for (int i = 0; i < entityList.size(); i++){

                        if (entityList.get(i).getName().equals(entityName1)){
                            idEntity1 = i;
                        }
                        else{
                            if (entityList.get(i).getName().equals(entityName2)){
                                idEntity2 = i;
                            }
                        }
                    }

                    if (idEntity1 != -1 && idEntity2 != -1){
                        entity1 = entityList.get(idEntity1);
                        entity2 = entityList.get(idEntity2);
                    } 
                    else{
                        System.err.println("Error in parsing of Coupling between " + entityName1 + " and " + entityName2 + ": incorrect Entity name");
                    }

                    // CO-OCCURRENCE
                    double cd = (double) coupObject.get("coOccurrence");
                    float coOccurrence = (float) cd;

                    // TYPE
                    Type type = null;
                    String t = (String) coupObject.get("type");
                    
                    switch(t){
                        case "CC":
                            type = Type.CC;
                            break;
                        case "CQ":
                            type = Type.CQ;
                            break;
                        case "QC":
                            type = Type.QC;
                            break;
                        case "QQ":
                            type = Type.QQ;
                            break;
                        default:
                            System.err.println("Error in parsing of Type for Coupling between " + entityName1 + " and " + entityName2 + t);
                            break;
                    }

                    Coupling coupling = new Coupling(entity1, entity2, type, coOccurrence);
                    coupList.add(coupling);
                }

                EndPoint endPoint = new EndPoint(coupList, ID, frequency);
                endPointList.add(endPoint);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return endPointList;

    }

    public ArrayList<UseCase> getUseCaseList(ArrayList<EndPoint> fullEndPointList){

        ArrayList<UseCase> useCaseList = new ArrayList<UseCase>();

        JSONParser parser = new JSONParser();
    
        try {

            JSONArray a = (JSONArray) parser.parse(new FileReader(inputPath + useCases));

            for (Object o : a){
                
                JSONObject ucObject = (JSONObject) o;

                // ID
                String ID = (String) ucObject.get("ID");

                // FREQUENCY
                double fd = (double) ucObject.get("frequency");
                float frequency = (float) fd;

                // BUILD CO-MAT STRATEGY
                String bmStrategy = (String) ucObject.get("buildMatStrategy");
                BuildMatricesType bmType = BuildMatricesType.valueOf(bmStrategy);
                
                boolean correctBMType = false;
                for (BuildMatricesType t : BuildMatricesType.values()){
                    if (bmType == t){
                        correctBMType = true;
                    }
                }
                
                if (!correctBMType){
                    System.err.println("incorrect buildCoMatStrategy for UseCase: " + ID);
                }
                    
                BuildCoMatStrategy buildCoMatStrategy = buildMatricesFactory.createBuildCoMatStrategy(bmType);


                // ENDPOINT LIST
                JSONArray el = (JSONArray) ucObject.get("endPointList");
                ArrayList<EndPoint> endPointList = new ArrayList<EndPoint>();


                for (Object e : el){

                    String endPointID = (String) e;

                    Boolean correctEndPointName = false;

                    for(EndPoint ep : fullEndPointList){

                        
                        if (ep.getID().equals(endPointID)){
                            endPointList.add(ep);
                            correctEndPointName = true;
                        }
                    }

                    if (!correctEndPointName){
                        System.err.println("Error in parsing UseCase with ID:" + ID + ". Invalid EndPoint list");
                    }

                }

                UseCase useCase = new UseCase(ID, frequency, endPointList);
                useCase.setStrategy(buildCoMatStrategy);
                useCaseList.add(useCase);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return useCaseList;

    }

    public BusinessLogic getBusinessLogic(ArrayList<UseCase> fullUseCaseList){

        JSONParser parser = new JSONParser();
        BusinessLogic bl = null;

        try {

            JSONObject blObject = (JSONObject) parser.parse(new FileReader(inputPath + businessLogic));
            
            // ID
            String ID = (String) blObject.get("ID");

            // BUILD CO-MAT STRATEGY
            String bmStrategy = (String) blObject.get("buildMatStrategy");
            BuildMatricesType bmType = BuildMatricesType.valueOf(bmStrategy);
            
            boolean correctBMType = false;
            for (BuildMatricesType t : BuildMatricesType.values()){
                if (bmType == t){
                    correctBMType = true;
                }
            }
            
            if (!correctBMType){
                System.err.println("incorrect buildCoMatStrategy for BusinessLogic: " + ID);
            }
            
            BuildCoMatStrategy buildCoMatStrategy = buildMatricesFactory.createBuildCoMatStrategy(bmType);

            bl = new BusinessLogic(ID, fullUseCaseList, buildCoMatStrategy);
        

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return bl;


    }

    public SimplifyGraphStrategy getSimplifyGraphStrategy(){

        JSONParser parser = new JSONParser();
        SimplifyGraphStrategy simplifyGraphStrategy = null;
        
        try {

            JSONObject inputSimplifyStrategy = (JSONObject) parser.parse(new FileReader(inputPath + graphMangerSettings));

            // STRATEGY
            String strategyName = (String) inputSimplifyStrategy.get("simpifyGraphStrategy");

            SimplifyGraphType strategyType = SimplifyGraphType.valueOf(strategyName);
            
            boolean correctSGType = false;
            for (SimplifyGraphType t : SimplifyGraphType.values()){
                if (strategyType == t){
                    correctSGType = true;
                }
            }
            
            if (!correctSGType){
                System.err.println("incorrect SimplifyGraphStrategy");
            }
            
            simplifyGraphStrategy = simplifyGraphFactory.createSimplifyGraphStrategy(strategyType);
            

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return simplifyGraphStrategy;

    }

    public LossFunctionStrategy getLossFucntioStrategy(){

        JSONParser parser = new JSONParser();
        LossFunctionStrategy lossFunctionStrategy = null;
        
        try {

            JSONObject inputLossStrategy = (JSONObject) parser.parse(new FileReader(inputPath + graphMangerSettings));

            // STRATEGY
            String strategyName = (String) inputLossStrategy.get("lossFunctionStrategy");

            LossFunctionType strategyType = LossFunctionType.valueOf(strategyName);
            
            boolean correctLFType = false;
            for (LossFunctionType t : LossFunctionType.values()){
                if (strategyType == t){
                    correctLFType = true;
                }
            }
            
            if (!correctLFType){
                System.err.println("incorrect LossFunctionStrategy");
            }
            
            lossFunctionStrategy = lossFunctionFactory.createLossFunctiontrategy(strategyType);
            

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return lossFunctionStrategy;

    }

    private String inputPath = "./input/";
    private String entities = "Entities.json";
    private String endPoints = "EndPoints.json";
    private String useCases = "UseCases.json";
    private String businessLogic = "BusinessLogic.json";
    private String graphMangerSettings = "GraphManagerSettings.json";

    BuildMatricesFactory buildMatricesFactory;
    SimplifyGraphFactory simplifyGraphFactory;
    LossFunctionFactory lossFunctionFactory;
 


    // private String couplings = "Couplings.txt";
    // private String entities = "Entities.txt";
    // private String useCases = "UseCases.txt";
    

}



// public ArrayList<Coupling> getCouplings(ArrayList<Entity> entities){

//     ArrayList<Coupling> inputCouplingList = new ArrayList<>();
//     try {
//         File couplingsFile = new File(this.inputPath + couplings);
//         Scanner couplingScanner = new Scanner(couplingsFile);
//         couplingScanner.useDelimiter(Pattern.compile("\\s*" + "/" + "\\s*"));

//         couplingScanner.nextLine();
//         while (couplingScanner.hasNextLine()) {
            

//             Scanner lineScanner = new Scanner(couplingScanner.nextLine());
//             lineScanner.useDelimiter(Pattern.compile("\\s*" + "/" + "\\s*"));


//             String entityName1 = lineScanner.next();
//             String entityName2 = lineScanner.next();
//             Entity entity1 = null;
//             Entity entity2 = null;
//             for (Entity e : entities){
//                 if (e.getName().equals(entityName1))
//                     entity1 = e;

//                 if (e.getName().equals(entityName2))
//                     entity2 = e;
//             }
//             if (entity1 == null){
//                 System.err.println("Entity name " + entityName1 + " is incorrect.");
//                 lineScanner.close();
//                 return null;
//             }
//             if (entity2 == null){
//                 System.err.println("Entity name " + entityName2 + " is incorrect.");
//                 lineScanner.close();
//                 return null;
//             }

//             Type type;
//             String t = lineScanner.next();
//             switch(t){
//                 case "CC":
//                     type = Type.CC;
//                     break;
//                 case "CQ":
//                     type = Type.CQ;
//                     break;
//                 case "QC":
//                     type = Type.QC;
//                     break;
//                 case "QQ":
//                     type = Type.QQ;
//                     break;
//                 default:
//                     System.err.println("Error in definition of Type for Coupling between " + entityName1 + " and " + entityName2 + t);
//                     return null;
//             }

//             float coOccurrence = Float.parseFloat(lineScanner.next());
//             if(coOccurrence > 1.f){
//                 System.err.println("Error in definition of co-occurrency value between " + entityName1 + " and " + entityName2 + " co-occurrency must be less than 1.");
//                 lineScanner.close();
//                 return null;
//             }
//             if(coOccurrence < 0.f){
//                 System.err.println("Error in definition of co-occurrency value between " + entityName1 + " and " + entityName2 + " co-occurrency must be more than 0.");
//                 lineScanner.close();
//                 return null;
//             }

//             inputCouplingList.add(new Coupling(entity1, entity2, type, coOccurrence));
//             System.out.println(entityName1 + entityName2 + type + coOccurrence);
//             }
//             couplingScanner.close();
            
//         } catch (FileNotFoundException e) {
//             System.out.println("An error occurred.");
//             e.printStackTrace();
//         }

//         return inputCouplingList;

// }



// public ArrayList<Entity> getEntities(){

    //     ArrayList<Entity> inputEntityList = new ArrayList<>();
    //     try {
    //         File entitiesFile = new File(this.inputPath + entities);
    //         Scanner entityScanner = new Scanner(entitiesFile);
    //         entityScanner.useDelimiter(Pattern.compile("\\n"));

    //         entityScanner.nextLine();
    //         while (entityScanner.hasNext()) {
    //           String entityName = entityScanner.next();
    //           inputEntityList.add(new Entity(entityName));
    //           System.out.println(entityName);
    //         }
    //         entityScanner.close();
    //       } catch (FileNotFoundException e) {
    //         System.out.println("An error occurred.");
    //         e.printStackTrace();
    //       }

    //     return inputEntityList;

    // }
