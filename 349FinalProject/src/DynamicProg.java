import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class DynamicProg {


    // based on pseudocode from http://www.micsymposium.org/mics_2005/papers/paper102.pdf
    public static void dynProg(int n, int cap, int[] idx, int[] vals, int[] wgts) {

        ArrayList<Integer> sol = new ArrayList<>();
        int finalval = 0;
        int totalwgt = 0;


        // create value table
        int[][] table = new int[n + 1][cap + 1];


        // fill table
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= cap; j++) {
                // initialize first row/col with zeros
                if (i == 0 || j == 0) {
                    table[i][j] = 0;
                }
                // fill with optimal substructures
                else if (j < wgts[i-1]) {
                    table[i][j] = table[i-1][j];
                } else {
                    table[i][j] = Math.max(table[i-1][j], vals[i-1] + table[i-1][j - wgts[i-1]]);
                }
            }
        }

        int k = n;
        int l = cap;
        int curval;
        int adj_k;

        //traceback: find the last used weight and compare to current.
        while (k > 0 && l > 0) {
            curval = table[k][l];
            adj_k = table[k-1][l];
            if (curval == adj_k) {
                k--;
            } else { // If they are not the same, the item was added.
                sol.add(k);
                l -= wgts[k-1];
                k--;
            }
        }

        // get total weight and total value using solution array.
        for (Integer wgtidx: sol) {
            totalwgt += wgts[wgtidx -1];
        }
        for (Integer validx: sol) {
            finalval += vals[validx-1];
        }

        System.out.print("Dynamic Programming solution: ");
        System.out.println("Value " + finalval + ", Weight " + totalwgt);



        // reverse solution array and print.
        Collections.reverse(sol);

        for (Integer num: sol) {
            System.out.print(num);
            System.out.print(" ");
        }
    }









    // directions: replace string in line 89 with name of the text file in proper format
    public static void main(String args[]) throws Exception {

        long timeStart = System.currentTimeMillis();

        File f = new File("easy200.txt");
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

        dynProg(n, cap, idx, values, weights);

        long timeTotal = (System.currentTimeMillis() - timeStart);

        System.out.println("\ntime: " + timeTotal + "ms");

    }
}