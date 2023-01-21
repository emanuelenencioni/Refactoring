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
    

    public InputManager(String input){

        buildMatricesFactory = new BuildMatricesFactory();
        simplifyGraphFactory = new SimplifyGraphFactory();
        lossFunctionFactory = new LossFunctionFactory();
        this.inputPath = input;
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

                UseCase useCase = new UseCase(ID, frequency, endPointList, buildCoMatStrategy);
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

            JSONObject graphSettingsObject = (JSONObject) parser.parse(new FileReader(inputPath + graphMangerSettings));

            // STRATEGY
            String strategyName = (String) graphSettingsObject.get("simpifyGraphStrategy");

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
            
            //TODO difficile impostare caso specifico per MST

            simplifyGraphStrategy = simplifyGraphFactory.createSimplifyGraphStrategy(strategyType);
            

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        return simplifyGraphStrategy;

    }

    public LossFunctionStrategy getLossFucntionStrategy(){

        JSONParser parser = new JSONParser();
        LossFunctionStrategy lossFunctionStrategy = null;
        
        try {

            JSONObject graphSettingsObject = (JSONObject) parser.parse(new FileReader(inputPath + graphMangerSettings));

            // STRATEGY
            String strategyName = (String) graphSettingsObject.get("lossFunctionStrategy");

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

    public Float getWeight(Type type){

        JSONParser parser = new JSONParser();
        Float weight = null;

        try {

            JSONObject graphSettingsObject = (JSONObject) parser.parse(new FileReader(inputPath + graphMangerSettings));

            // WEIGHT
            switch (type){

                case CC:
                    double weightCCDouble = (double) graphSettingsObject.get("weightCC");
                    weight = (float) weightCCDouble;
                    break;

                case CQ:
                    double weightCQDouble = (double) graphSettingsObject.get("weightCQ");
                    weight = (float) weightCQDouble;
                    break;

                case QC:
                    double weightQCDouble = (double) graphSettingsObject.get("weightQC");
                    weight = (float) weightQCDouble;
                    break;

                case QQ:
                    double weightQQDouble = (double) graphSettingsObject.get("weightQQ");
                    weight = (float) weightQQDouble;
                    break;
                default:
                    break;

            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Errore input file JSON");
        }

        if (weight == null){
            System.err.println("Error in parsing weight " + Type.CC.toString() + " from file JSON");
        }
        
        return weight;

    }
    

    private String inputPath;
    private final String entities = "Entities.json";
    private final String endPoints = "EndPoints.json";
    private final String useCases = "UseCases.json";
    private final String businessLogic = "BusinessLogic.json";
    private final String graphMangerSettings = "GraphManagerSettings.json";

    private BuildMatricesFactory buildMatricesFactory;
    private SimplifyGraphFactory simplifyGraphFactory;
    private LossFunctionFactory lossFunctionFactory;

}