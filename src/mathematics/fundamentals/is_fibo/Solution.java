package mathematics.fundamentals.is_fibo;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        final Set<Long> fibs = new HashSet<>();

        fillFibsUpTo(fibs, 10_000_000_000L);

        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for(int i=0; i<T; i++) {
                final long l = stdin.nextLong();

                if (fibs.contains(l)) {
                    System.out.println("IsFibo");
                } else {
                    System.out.println("IsNotFibo");
                }
            }
        }
    }

    private static void fillFibsUpTo(Set<Long> fibs, long upTo) {
        long a = 1L, b=1L;

        while (b <= upTo) {
            fibs.add(b);

            long t = a+b;
            a = b;
            b = t;
        }
    }
}
