import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


class EditDistance {


    public int[][] table;

    // initialize the table
    public EditDistance(int x, int y) {
        table = new int[x + 1][y + 1];
    }


    public void findEditDistance(String str1, String str2, boolean toggle) {

        int str1len = str1.length();
        int str2len = str2.length();


        // Fill the table
        table[0][0] = 0;

        // Fill gap penalties of first row and first column
        for (int i = 1; i <= str2len; i++) {
            table[0][i] = table[0][i - 1] + 2;
        }
        for (int i = 1; i <= str1len; i++) {
            table[i][0] = table[i-1][0] + 2;
        }

        // for each point in the table, take the minimum cost of the three adjacent tables
        // cur is the current spot, jgap and igap are the spots above and to the right.
        // if cur is the same as the last spot, add 0, else add 1 to the value
        for (int i = 1; i <= str1len; i++) {
            for (int j = 1; j <= str2len; j++) {
                int cur;
                if (str1.charAt(i -1) == str2.charAt(j - 1)) {
                    cur = table[i-1][j-1];
                }
                else {
                    cur = table[i-1][j-1] + 1;
                }
                int igap = table[i-1][j];
                int jgap = table[i][j-1];

                table[i][j] = Math.min(cur, Math.min(igap + 2, jgap + 2));
            }
        }

        System.out.println("Edit distance = " + table[str1len][str2len]);


        // if toggle is set, print sequence
        if (toggle) {
            ArrayList<String> lines = new ArrayList<>();
            int line_idx = 0;
            int i = str1len;
            int j = str2len;
            int cur;
            int adj_i;
            int adj_j;
            int adj_diag;
            String cur_trace;

            // find the cost of each adjacent point.
            // take the minimum cost adjacent point and create a string from it.
            // add the string to the beginning of lines array
            while (i > 0 && j > 0) {
                cur = table[i][j];
                adj_i = table[i-1][j];
                adj_j = table[i][j-1];
                adj_diag = table[i-1][j-1];


                if (cur - 2 == adj_i) {
                    i -= 1;
                    cur_trace = str1.charAt(i) + " - 2";
                    lines.add(0, cur_trace);
                }
                else if (cur - 2 == adj_j) {
                    j -= 1;
                    cur_trace = "- " + str2.charAt(j) + " 2";
                    lines.add(0, cur_trace);
                }

                else if (cur == adj_diag) {
                    i -= 1;
                    j -= 1;
                    cur_trace = str1.charAt(i) + " " + str2.charAt(j) + " 0";
                    lines.add(0, cur_trace);
                }
                else if (cur - 1 == adj_diag ) {
                    i -= 1;
                    j -= 1;
                    cur_trace = str1.charAt(i) + " " + str2.charAt(j) + " 1";
                    lines.add(0, cur_trace);
                }
            }

            // print the elements of the array.
            for (int k = 0; k < lines.size(); k++) {
                System.out.println(lines.get(k));
            }

            // if there are leftover spots, print them with gaps.
            while (i > 0) {
                System.out.println(str1.charAt(i-1) + " - 2");
                i--;
            }
            
            while (j > 0) {
                System.out.println("- " + str2.charAt(j-1) + " 2");
                j--;
            }

        }

    }


    // within the main, to change file input file, change the string in line 122 to the file name
    // to toggle printing, change 'true' to 'false' in line 131, and vice versa.
    public static void main(String args[]) throws Exception{

            File f = new File("testEcoli3000.txt");
            Scanner sc = new Scanner(f);
            String st1 = sc.next();
            String st2 = sc.next();


            EditDistance ed = new EditDistance(st1.length(), st2.length());

            ed.findEditDistance(st1, st2, false);

    }


}