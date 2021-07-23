import java.io.File;
import java.util.*;


class BranchAndBound {


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

    public static class Node {

        public int level;
        public int val;
        public int wgt;
        public double nodekey;
        public ArrayList<Integer> pathToHere;

        public Node(int level, int val, int wgt, Item item, int cap, ArrayList<Integer> pathToHere) {
            this.level = level;
            this.val = val;
            this.wgt = wgt;
            this.nodekey = val + (cap - wgt) * (item.getRatio());
            this.pathToHere = pathToHere;
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

    public static class NodeKeyComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            if (n1.nodekey < n2.nodekey) {
                return 1;
            } else {
                return -1;
            }
        }
    }


    // based on pseudocode algorithm from http://www.micsymposium.org/mics_2005/papers/paper102.pdf
    public static void branchBound(int n, int cap, int idx[], int[] vals, int[] wgts) {


        ArrayList<Item> sorted = new ArrayList<>();
        ArrayList<Item> sol = new ArrayList<>();
        int finalval = 0;
        int totalwgt = 0;

        for (int i = 0; i < n; i++) {
            sorted.add(new Item(idx[i], vals[i], wgts[i]));
        }

        // make sorted list by value / weight
        Collections.sort(sorted, new ItemRatioComparator());


        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeKeyComparator());
        Node curNode;

        Item rootItem = sorted.get(0);
        Node rootNode = new Node(0, 0, 0, rootItem, cap, new ArrayList<>());
        pq.add(rootNode);


        Item nextItem;
        ArrayList<Integer> pathTrue = new ArrayList<>();
        ArrayList<Integer> pathLeft;
        ArrayList<Integer> pathRight;
        boolean wgtExceeded;

        long startTime = System.currentTimeMillis();

        while (pq.size() > 0) {


            curNode = pq.poll();

            if (curNode.nodekey > finalval) {
                nextItem = sorted.get(curNode.level);
                wgtExceeded = false;

                if (curNode.level + 1 >= sorted.size()) {
                    break;
                }

                // Set left node to include next item
                pathLeft = new ArrayList<>(curNode.pathToHere);
                pathLeft.add(0);
                Node leftChild = new Node(curNode.level + 1,
                                            nextItem.value + curNode.val,
                                            curNode.wgt + nextItem.weight,
                                            sorted.get(curNode.level + 1),
                                            cap, pathLeft);

                // set wgtExceeded for left child path taken
                if (curNode.wgt + nextItem.weight > cap) {
                    wgtExceeded = true;
                }

                // if the left child's value is greater than max value, update best solution
                if ((leftChild.val > finalval) && (!wgtExceeded)) {
                    finalval = leftChild.val;
                    totalwgt = leftChild.wgt;
                    pathTrue = leftChild.pathToHere;
                }

                // if left child bound better than max value, enqueue left
                if ((leftChild.nodekey > finalval) && (!wgtExceeded)) {
                    pq.add(leftChild);
                }

                // set right child of cur node to not include the next item
                pathRight = new ArrayList<>(curNode.pathToHere);
                pathRight.add(1);
                Node rightChild = new Node(curNode.level + 1, curNode.val, curNode.wgt,
                        sorted.get(curNode.level + 1), cap, pathRight);

                // if right child bound better than max value, enqueue right
                if (rightChild.nodekey > finalval) {
                    pq.add(rightChild);
                }
            }
            if (System.currentTimeMillis() - startTime >= 60 * 1000) {
                break;
            }
        }


        System.out.print("Using Branch and Bound the best feasible solution found: ");
        System.out.println("Value " + finalval + ", Weight " + totalwgt);

        for (int i = 0; i < pathTrue.size(); i++) {
            if (pathTrue.get(i) == 0) {
                sol.add(sorted.get(i));
            }
        }

        Collections.sort(sol, new ItemKeyComparator());

        for (Item item: sol) {
            System.out.print(item.key);
            System.out.print(" ");
        }
    }








    // directions: replace string in line 89 with name of the text file in proper format
    public static void main(String args[]) throws Exception {

        long timeStart = System.currentTimeMillis();
        File f = new File("hard40.txt");
        Scanner sc = new Scanner(f);


        // parse for number items & total capacity
        int n = Integer.parseInt(sc.next());

        int idx[] = new int[n];
        int values[] = new int[n];
        int weights[] = new int[n];

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

        branchBound(n, cap, idx, values, weights);

        long timeTotal = (System.currentTimeMillis() - timeStart);

        System.out.println("\ntime: " + timeTotal + "ms");

    }
}