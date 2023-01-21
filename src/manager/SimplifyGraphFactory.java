package src.manager;

public interface SimplifyGraphFactory {

    /**
     * @param SimplifyGraphType t name of the concrete strategy to be instantiated
     * @return reference to instance of concrete strategy implementing the chosen strategy
     */
    public SimplifyGraphStrategy createSimplifyGraphStrategy(SimplifyGraphType t);

}
