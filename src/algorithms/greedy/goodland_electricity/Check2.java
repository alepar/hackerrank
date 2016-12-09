package algorithms.greedy.goodland_electricity;

import java.util.Scanner;

public class Check2 {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();
            final int K = stdin.nextInt();

            final boolean[] hasTower = new boolean[N];
            for (int i=0; i<N; i++) {
                if(stdin.nextInt() > 0) {
                    hasTower[i] = true;
                }
            }

            int lastTower = -K;
            for (int i=0; i<N; i++) {
                if(hasTower[i]) {
                    final int dist = i-lastTower;
                    if (dist > 2*K-1) {
                        System.out.println("bad between\t" + lastTower + "\t" + i);
                    }
                    lastTower=i;
                }
            }

        }
    }
}
