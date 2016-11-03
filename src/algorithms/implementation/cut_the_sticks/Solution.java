package algorithms.implementation.cut_the_sticks;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try (Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();
            final int[] arr = new int[N];
            for (int i=0; i<N; i++) {
                arr[i] = stdin.nextInt();
            }
            Arrays.sort(arr);
            System.out.println(N);
            int prev = arr[0];
            for (int i = 0; i < N; i++) {
                if (arr[i] == prev) continue;

                System.out.println(N-i);
                prev = arr[i];
            }
        }
    }

}
