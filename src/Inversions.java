import java.util.Arrays;

public class Inversions {


    // arr is array, l and r are left and right indices
    public static int invCounter(int[] arr, int l, int r) {

        // Keep track of inversion count at particular node of recursion tree
        int count = 0;

        if (l < r) {
            int m = (l + r) / 2; // get middle index

            // Total inversions = left count + right count + merge count

            // DIVIDE STEP: divide array by left and right

            // Recursive call for left sub-array
            // Add # inversions from left sub-array
            count += invCounter(arr, l, m);

            // Recursive call for right sub-array
            // Add # inversions from right sub-array
            count += invCounter(arr, m + 1, r);

            // COMBINE STEP: merge the left and right and count their inversions
            // Merge subarrays and their counts
            count += mergeAndCount(arr, l, m ,r);
        }

        return count;


    }


    // arr is array, l, r and m are left, right and middle indices
    public static int mergeAndCount(int[] arr, int l, int m, int r) {

        // CONQUER STEP: Find inversions by merging left/right subarrays
        // Create the left sub-array
        int[] left = Arrays.copyOfRange(arr, l, m + 1);

        // Create the right sub-array
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l, swaps = 0;

        // Count inversions for left and right subarrays
        while (i < left.length && j < right.length) {

            if (left[i] <= right[j]) { // if right greater, append left
                arr[k++] = left[i++];
            }

            else { // if right greater, append right and add swaps
                arr[k++] = right[j++];
                swaps += (m + 1) - (l + i);
            }
        }

        // Fill new array from the rest of the left sub-array
        while (i < left.length) {
            arr[k++] = left[i++];
        }

        // Fill new array from the rest of the right sub-array
        while (j < right.length) {
            arr[k++] = right[j++];
        }

        // return number of swaps
        return swaps;

    }




}
