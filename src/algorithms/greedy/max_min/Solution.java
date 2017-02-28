package algorithms.greedy.max_min;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();
            final int K = stdin.nextInt();

            final int[] arr = new int[N];
            for (int i=0; i<N; i++) {
                arr[i] = stdin.nextInt();
            }

            Arrays.sort(arr);

            if (N==K) {
                System.out.println(arr[N-1]-arr[0]);
            } else {
                int unfairness = arr[K-1]-arr[0];
                for (int i = 1; i < N - K + 1; i++) {
                    final int curUnfairness = arr[K-1+i]-arr[i];
                    if (curUnfairness < unfairness) {
                        unfairness = curUnfairness;
                    }
                }
                System.out.println(unfairness);
            }
        }
    }
}
