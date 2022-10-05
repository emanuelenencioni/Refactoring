package src.applicationModel;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;


public class InputManager {
    

    public InputManager(){



    }

    public ArrayList<Entity> getEntities(){

        ArrayList<Entity> inputEntityList = new ArrayList<>();
        try {
            File entitiesFile = new File(this.inputPath + entities);
            Scanner entityScanner = new Scanner(entitiesFile);
            entityScanner.useDelimiter(Pattern.compile("\\n"));

            entityScanner.nextLine();
            while (entityScanner.hasNext()) {
              String entityName = entityScanner.next();
              inputEntityList.add(new Entity(entityName));
              System.out.println(entityName);
            }
            entityScanner.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return inputEntityList;

    }

    public ArrayList<Coupling> getCouplings(ArrayList<Entity> entities){

        ArrayList<Coupling> inputCouplingList = new ArrayList<>();
        try {
            File couplingsFile = new File(this.inputPath + couplings);
            Scanner couplingScanner = new Scanner(couplingsFile);
            couplingScanner.useDelimiter(Pattern.compile("\\s*" + "/" + "\\s*"));

            couplingScanner.nextLine();
            while (couplingScanner.hasNextLine()) {
                

                Scanner lineScanner = new Scanner(couplingScanner.nextLine());
                lineScanner.useDelimiter(Pattern.compile("\\s*" + "/" + "\\s*"));


                String entityName1 = lineScanner.next();
                String entityName2 = lineScanner.next();
                Entity entity1 = null;
                Entity entity2 = null;
                for (Entity e : entities){
                    if (e.getName().equals(entityName1))
                        entity1 = e;

                    if (e.getName().equals(entityName2))
                        entity2 = e;
                }
                if (entity1 == null){
                    System.err.println("Entity name " + entityName1 + " is incorrect.");
                    lineScanner.close();
                    return null;
                }
                if (entity2 == null){
                    System.err.println("Entity name " + entityName2 + " is incorrect.");
                    lineScanner.close();
                    return null;
                }

                Type type;
                String t = lineScanner.next();
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
                        System.err.println("Error in definition of Type for Coupling between " + entityName1 + " and " + entityName2 + t);
                        return null;
                }

                float coOccurrence = Float.parseFloat(lineScanner.next());
                if(coOccurrence > 1.f){
                    System.err.println("Error in definition of co-occurrency value between " + entityName1 + " and " + entityName2 + " co-occurrency must be less than 1.");
                    lineScanner.close();
                    return null;
                }
                if(coOccurrence < 0.f){
                    System.err.println("Error in definition of co-occurrency value between " + entityName1 + " and " + entityName2 + " co-occurrency must be more than 0.");
                    lineScanner.close();
                    return null;
                }

                inputCouplingList.add(new Coupling(entity1, entity2, type, coOccurrence));
                System.out.println(entityName1 + entityName2 + type + coOccurrence);
                }
                couplingScanner.close();
                
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            return inputCouplingList;

    }

    private String inputPath = "./input/";
    private String couplings = "Couplings.txt";
    private String entities = "Entities.txt";
    private String useCases = "UseCases.txt";
    

}
