package mathematics.fundamentals.sherlock_and_permutations;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static final long MODULO = 1_000_000_000L + 7;

    public static final long[] cache = new long[1000*1000];

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();
                final int M = stdin.nextInt();

                Arrays.fill(cache, 0, N*1000+M, -1L);
                System.out.println(calculate(N, M-1));
            }
        }
    }

    private static long calculate(int ones, int zeroes) {
        if (ones <= 0 || zeroes <= 0) {
            return 1;
        }

        final long cached = get(ones, zeroes);
        if (cached != -1L) {
            return cached;
        }

        final long calculated = (calculate(ones-1, zeroes) + calculate(ones,zeroes-1)) % MODULO;
//        System.out.println(ones + "," + zeroes + "=" + calculated);
        set(ones, zeroes, calculated);
        return calculated;
    }

    private static long get(int ones, int zeroes) {
        return cache[(ones-1)*1000+zeroes-1];
    }

    private static void set(int ones, int zeroes, long value) {
        cache[(ones-1)*1000+zeroes-1] = value;
    }

}
