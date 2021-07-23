import java.util.*;
import java.io.*;

public class TopSorterTest1 {

    public static void main(String[] args) {

        ArrayList<Integer> list = TopSorter.topSortGenerator("topSortTest1.txt");
        for (int v: list) {
            System.out.println(v);
        }


    }
}
