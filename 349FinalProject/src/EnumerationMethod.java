import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


class EnumerationMethod {


    // based on pseudocode algorithm from http://www.micsymposium.org/mics_2005/papers/paper102.pdf
    public static void bruteForce(int n, int cap, int[] idx, int[] vals, int[] wgts) {

        ArrayList<Integer> sol = new ArrayList<>();
        int finalval = 0;
        int totalwgt = 0;

        int[] arr = new int[n];
        int[] optArr = new int[n];

        // variable for 2 to the n; used in for loop
        int toTheN = (int)Math.pow(2,n);

        int curVal;
        int curWgt;
        int j;

        int optVal = 0;
        int optWgt = 0;

        // Generate every possible subset/arrangement in knapsack
        for (int i = 0; i < toTheN; i++) {

            // reset value and weight
            curVal = 0;
            curWgt = 0;

            // locate the next chosen item
            j = n - 1;
            while (arr[j] != 0 && j > 0) {
                arr[j] = 0;
                --j;
            }
            arr[j] = 1;

            // add value and weight of current arrangement
            for (int k = 0; k < n; k++) {
                if (arr[k] == 1) {
                    curVal += vals[k];
                    curWgt += wgts[k];
                }
            }

            // if current arrangement fulfills optimal check, set its value and weight
            if ((curWgt <= cap) && (curVal > optVal)) {
                optVal = curVal;
                optWgt = curWgt;

                for (int pos = 0; pos < n; pos++) {
                    optArr[pos] = arr[pos];
                }
            }
        }

        // add optimal array to solution array
        for (int k = 0; k < n; k++) {
            if (optArr[k] == 1) {
                sol.add(idx[k]);
            }
        }

        // assign final value and weight
        finalval = optVal;
        totalwgt = optWgt;



        System.out.print("Using Brute force the best feasible solution found: ");
        System.out.println("Value " + finalval + ", Weight " + totalwgt);


        for (Integer num: sol) {
            System.out.print(num);
            System.out.print(" ");
        }
    }








    // directions: replace string in line 89 with name of the text file in proper format
    public static void main(String args[]) throws Exception {

        long timeStart = System.currentTimeMillis();

        File f = new File("easy20.txt");
        Scanner sc = new Scanner(f);


        // parse for number items & total capacity
        int n = Integer.parseInt(sc.next());

        int[] idx = new int[n];
        int[] values = new int[n];
        int[] weights = new int[n];

        int idxidx = 0;
        int valuesidx = 0;
        int weightsidx = 0;

        // parsing for weight & value arrays
        for (int i = 0; i < n * 3; i++) {
            if (i % 3 == 0) {
                idx[idxidx++] = Integer.parseInt(sc.next());
            }
            else if (i % 3 == 1) {
                values[valuesidx++] = Integer.parseInt(sc.next());
            }
            else {
                weights[weightsidx++] = Integer.parseInt(sc.next());
            }
        }

        int cap = Integer.parseInt(sc.next());

        bruteForce(n, cap, idx, values, weights);

        long timeTotal = (System.currentTimeMillis() - timeStart);

        System.out.println("\ntime: " + timeTotal + "ms");

    }
}