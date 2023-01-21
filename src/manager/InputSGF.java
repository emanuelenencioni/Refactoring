package src.manager;

public class InputSGF implements SimplifyGraphFactory{


    /**
     * Default constructor
     */
    public InputSGF() {
    }


    /**
     * @param SimplifyGraphType t name of the concrete strategy to be instantiated
     * @return reference to instance of concrete strategy implementing the chosen strategy
     */
    public SimplifyGraphStrategy createSimplifyGraphStrategy(SimplifyGraphType t) {

        switch (t) {
            case MSTClustering:
                return new MSTClustering();

            default:
                return null;}

    }

}
