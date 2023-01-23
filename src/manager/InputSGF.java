package src.manager;

import java.util.Scanner;

public class InputSGF implements SimplifyGraphFactory{


    /**
     * 
     * @param scanner the scanner for taking the input from terminal
     */
    public InputSGF(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * @param SimplifyGraphType t name of the concrete strategy to be instantiated
     * @return reference to instance of concrete strategy implementing the chosen strategy
     */
    public SimplifyGraphStrategy createSimplifyGraphStrategy(SimplifyGraphType t) {

        switch (t) 
        {
            case MSTClustering:
            System.out.println("You chose MSTClustering to Simplify Graph, insert values for n and s greater than 0.");

            
            //INSERT N
            boolean inputOk = false;
            int n = 0;
            while (!inputOk) {
                System.out.println("Insert n (number of partitions):");
                if(!this.scanner.hasNextInt()){
                    System.out.println("Input is not valid: not a number");
                    this.scanner.next();
                }else {
                    n = this.scanner.nextInt();
                    if (n <= 0) {
                        System.out.println("Input is not valid: number must be greater than 0");
                    } else {
                        inputOk = true;
                    }
                }
            }
            
            // INSERT S
            inputOk = false;
            int s = 0;
            while (!inputOk) {
                System.out.println("Insert s (max entities per partition):");
                if(!this.scanner.hasNextInt()){
                    System.out.println("Input is not valid: not a number");
                    this.scanner.next();
                }else {
                    s = this.scanner.nextInt();
                    if (s <= 0) {
                        System.out.println("Input is not valid: number must be greater than 0");
                    } else {
                        inputOk = true;
                    }
                }
            }
            this.scanner.close();

                return new MSTClustering(n, s);

            default:
                return null;
        }

    }
    private Scanner scanner;

}
