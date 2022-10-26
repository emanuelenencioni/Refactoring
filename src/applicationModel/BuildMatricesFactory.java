package src.applicationModel;


/**
 * 
 */
public class BuildMatricesFactory {

    /**
     * Default constructor
     */
    public BuildMatricesFactory() {
    }


    /**
     * @param BuilMatricesType t name of the concrete strategy to be instantiated
     * @return reference to instance of concrete strategy implementing the chosen strategy
     */
    public BuildCoMatStrategy createBuildCoMatStrategy(BuildMatricesType t) {

        switch (t) {
            case Average:
                return new Average();

            default:
                return null;
        }

    }

}