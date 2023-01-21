package src.manager;

import java.util.Scanner;

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

        switch (t) 
        {
            case MSTClustering:
                // System.out.println("You chose MSTClustering to Simplify Graph, insert values for n and s greater than 0.");

                // //INSERT N
                // Scanner scan1 = new Scanner(System.in);
                // boolean inputOk = false;
            
                // int n = 0;
                // while (!inputOk) {
                //     System.out.println("Insert n");
                //     if (!scan1.hasNextInt()) {
                //         System.out.println("Input is not valid: not a number");
                //         scan1.next();
                //     }
                //     else {
                //         n = scan1.nextInt();
                //         if (n <= 0){
                //             System.out.println("Input is not valid: number must be greater than 0");
                //         }
                //         else {
                //             inputOk = true;
                //         }
                //     }
                // }
                // scan1.close();


                // // INSERT S
                // System.out.println("Insert s: ");
                // Scanner scan2 = new Scanner(System.in);

                
                // inputOk = false;
            
                // int s = 0;
                // while (!inputOk) {
                //     System.out.println("Enter a number greater than 0");
                //     if (!scan2.hasNextInt()) {
                //         System.out.println("Input is not valid: not a number");
                //         scan2.next();
                //     }
                //     else {
                //         s = scan2.nextInt();
                //         if (s <= 0){
                //             System.out.println("Input is not valid: number must be greater than 0");
                //         }
                //         else{
                //             inputOk = true;
                //         }
                //     }
                // }
                // scan2.close();
                // FIXME non funziona

                // return new MSTClustering(n, s);
                return new MSTClustering();

            default:
                return null;
        }

    }

}
