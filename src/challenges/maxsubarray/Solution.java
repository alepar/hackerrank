package challenges.maxsubarray;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for(int t=0; t<T; t++) {
                final int N = stdin.nextInt();

                boolean hasPositive = false;
                int maxNonPositive = Integer.MIN_VALUE;
                int posSum = 0;
                int subMax = 0;
                int subCur = 0;
                for (int i=0; i<N; i++) {
                    final int ai = stdin.nextInt();

                    if (ai > 0) {
                        posSum += ai;
                        hasPositive = true;
                    } else {
                        if (ai > maxNonPositive) {
                            maxNonPositive = ai;
                        }
                    }

                    subCur += ai;

                    if (subCur < 0) {
                        subCur = 0;
                    }
                    if (subCur > subMax) {
                        subMax = subCur;
                    }
                }

                if (hasPositive) {
                    System.out.print(subMax);
                    System.out.print(" ");
                    System.out.println(posSum);
                } else {
                    System.out.print(maxNonPositive);
                    System.out.print(" ");
                    System.out.println(maxNonPositive);
                }
            }
        }
    }
}
