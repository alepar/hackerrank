package algorithms.dynamic_programming.red_john_is_back;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try (Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();

                cache = new HashMap<>();
                final long ways = calculateWays(N);
                era = new byte[(int)(ways/2)+1];
                final long primes = eratosphen(ways);
                System.out.println(primes);
            }
        }
    }

    private static byte[] era;
    private static long eratosphen(long n) {
        if (n<2) return 0;
        int count = 1;
        for (long i=3; i<=n; i++) {
            if (era[idx(i)] == 1) continue;
            count++;
            for (long t=i;t<=n;t+=i) {
                if (t%2 != 0) {
                    era[idx(t)] = 1;
                }
            }
        }
        return count;
    }
    private static int idx(long n) {
        return (int)((n-1)/2-1);
    }

    private static Map<Integer, Long> cache;
    private static long calculateWays(int n) {
        Long res = cache.get(n);
        if (res != null) {
            return res;
        }

        if (n<=3) {
            return 1;
        }

        res = calculateWays(n-1) + calculateWays(n-4);
        cache.put(n, res);
        return res;
    }

}
