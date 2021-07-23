import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Comparator;


class GreedySearch {


    public static class Item {

        public int key;
        public int value;
        public int weight;
        public double ratio;

        public Item(int key, int value, int weight)  {
            this.key = key;
            this.value = value;
            this.weight = weight;
            this.ratio = ((double) (this.value) / (double) (this.weight));
        }

        public double getRatio() {
            return this.ratio;
        }

    }

    public static class ItemRatioComparator implements Comparator<Item> {
        public int compare(Item i1, Item i2) {
            if (i1.getRatio() < i2.getRatio()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static class ItemKeyComparator implements Comparator<Item> {
        public int compare(Item i1, Item i2) {
            if (i1.key < i2.key) {
                return -1;
            }
            else if (i1.key > i2.key) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    public static void greedySol(int n, int cap, int idx[], int[] vals, int[] wgts) {


        ArrayList<Item> sorted = new ArrayList<>();
        ArrayList<Item> sol = new ArrayList<>();
        int finalval = 0;
        int totalwgt = 0;

        for (int i = 0; i < n; i++) {
            sorted.add(new Item(idx[i], vals[i], wgts[i]));
        }

        // make sorted list by value / weight
        Collections.sort(sorted, new ItemRatioComparator());

        // add sorted items until weight will be exceeded
        int nextWgt = 0;
        int cur = 0;
        Item curItem;
        while ((nextWgt < cap) && (cur < sorted.size()) && (cur < n)) {
            curItem = sorted.get(cur);
            totalwgt += curItem.weight;
            finalval += curItem.value;
            sol.add(curItem);
            cur++;
            nextWgt = totalwgt + sorted.get(cur).weight;
        }

        // sort the solution by key
        Collections.sort(sol, new ItemKeyComparator());


        System.out.print("Greedy solution (not necessarily optmial): ");
        System.out.println("Value " + finalval + ", Weight " + totalwgt);



        for (Item item: sol) {
            System.out.print(item.key);
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

        greedySol(n, cap, idx, values, weights);

        long timeTotal = (System.currentTimeMillis() - timeStart);

        System.out.println("\ntime: " + timeTotal + "ms");

    }
}