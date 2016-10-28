package mathematics.fundamentals.reverse_game;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();
                final int K = stdin.nextInt();

                final int maxball = N - 1;
                final int pos1 = (maxball - K) * 2;
                final int pos2 = 1 + K * 2;
                System.out.println(Math.min(pos1, pos2));
            }
        }
    }

}
