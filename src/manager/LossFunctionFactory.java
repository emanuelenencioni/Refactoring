package src.manager;


/**
 * 
 */
public class LossFunctionFactory {

    /**
     * Default constructor
     */
    public LossFunctionFactory() {
    }


    /**
     * @param LossFunctionType t name of the concrete strategy to be instantiated
     * @return reference to instance of concrete strategy implementing the chosen strategy
     */
    public LossFunctionStrategy createLossFunctiontrategy(LossFunctionType t) {

        switch (t) {
            case CutSum:
                return new ReverseHuber();

            case LossStrategy2:
                return new MSE();

            default:
                return null;}

    }

}