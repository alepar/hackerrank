package algorithms.search.sherlock_and_array;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();
                final int[] a = new int[N];
                long delta = 0;

                for (int n=0; n<N; n++) {
                    a[n] = stdin.nextInt();
                    delta += a[n];
                }

                delta -= a[0];
                boolean found = false;
                if (delta == 0) {
                    found = true;
                } else {
                    for (int i=1; i<N; i++) {
                        delta -= a[i]+a[i-1];

                        if (delta == 0) {
                            found = true;
                            break;
                        }
                    }
                }

                if (found) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }
}