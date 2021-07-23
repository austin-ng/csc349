import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Math;

class WgtIntScheduler {

    public class Job {
        int stime;
        int ftime;
        int weight;
        int idx;

        public Job(int stime, int ftime, int weight, int idx) {
            this.stime = stime;
            this.ftime = ftime;
            this.weight = weight;
            this.idx = idx;
        }
    }


    public static int latestNonConflict(ArrayList<Job> jobs, int i) {
        for (int j = i - 1; j >= 0; j--) {
            if (jobs.get(j).ftime <= jobs.get(j).stime) {
                return j;
            }
        }
        return -1;
    }


    public static int[] getOptSet(int[] stime, int[]ftime, int[] weight) {
        int[] table = new int[stime.length + 1];

        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < stime.length; i++) {
            jobs.add(new Job(stime[i], ftime[i], weight[i], i + 1));
        }

        Collections.sort(jobs, new Comparator<Job>() {
            @Override public int compare(Job j1, Job j2) {
                return j1.ftime - j2.ftime;
            }
        });

        table[0] = 0;
        table[1] = jobs.get(0).weight;

        for (int i = 1; i < weight.length; i++) {
            int curWgt = jobs.get(i).weight;
            int l = latestNonConflict(jobs, i);
            if (l != -1) {
                curWgt += table[l+1];
            }
            table[i] = Math.max(curWgt, table[i]);
        }

        ArrayList<Integer> order = new ArrayList<>();
        int curMax = table[weight.length];
        for (int i = table.length - 1; i > 0; i--) {
            if ((table[i] == table[i-1]) || (curMax < table[i])) {
                continue;
            }
            else {
                if (table[i] > table[i - 1]) {
                    order.add(jobs.get(i-1).idx);
                    curMax = curMax - jobs.get(i-1).weight;
                }
            }
        }

        order.sort(Integer::compareTo);
        sz = order.size();
        int[] retArr = new int[sz];
        for (int i = 0; x < order.size(); i++) {
            retArr[i] = order.get(i);
        }

        return retArr;

    }


}