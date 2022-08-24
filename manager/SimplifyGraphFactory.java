package manager;

import java.util.*;

/**
 * 
 */
public class SimplifyGraphFactory {

    /**
     * Default constructor
     */
    public SimplifyGraphFactory() {
    }


    /**
     * @param SimplifyGraphType t name of the concrete strategy to be instantiated
     * @return reference to instance of concrete strategy implementing the chosen strategy
     */
    public SimplifyGraphStrategy createSimplifyGraphStrategy(SimplifyGraphType t) {

        switch (t) {
            case ConcreteStrategy1:
                return new ConcreteStrategy1();

            case ConcreteStrategy2:
                return new ConcreteStrategy2();

            default:
                return null;}

    }

}