package algorithms.sorting.insertion_sort;

import java.util.Scanner;

public class InsertionSort {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();
                final int[] array = new int[N];
                for (int i=0; i<N; i++) {
                    array[i] = stdin.nextInt();
                }

                long shifts = 0;
                for (int c=1; c<N; c++) {
                    final int cur = array[c];
                    final int pos = binSearch(array, 0, c-1, cur);
                    if (pos != c) {
                        shifts += c-pos;
                        for (int i=c; i>pos; i--) {
                            array[i] = array[i-1];
                        }
                        array[pos] = cur;
                    }
                }
                System.out.println(shifts);
            }
        }
    }

    private static int binSearch(int[] array, int left, int right, int value) {
        if (array[right] <= value) {
            return right+1;
        }
        if (array[left] > value) {
            return left;
        }

        if (right-left == 1) {
            return right;
        }

        final int mid = (left+right)/2;
        if (array[mid] <= value) {
            return binSearch(array, mid, right, value);
        } else {
            return binSearch(array, left, mid, value);
        }
    }
}
