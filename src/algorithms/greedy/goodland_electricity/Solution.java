package algorithms.greedy.goodland_electricity;

import java.util.Scanner;

public class Solution {
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

            int toPowerOn = 0;
            int lastCovered = -1;
            while(lastCovered < N-1) {
                int i;
                final int right = Math.min(lastCovered + K, N - 1);
                final int left = Math.max(-1, lastCovered - K + 1);

                boolean found = false;
                for (i = right; i > left; i--) {
                    if (hasTower[i]) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("-1");
                    break;
                }

                lastCovered = i + K - 1;
                toPowerOn++;
            }

            if (lastCovered >= N-1) {
                System.out.println(toPowerOn);
            }
        }
    }
}
