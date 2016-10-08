package challenges.candies;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try (Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();

            final int ratings[] = new int[N];
            final int candies[] = new int[N];

            for (int n = 0; n < N; n++) {
                ratings[n] = stdin.nextInt();
            }

            for (int i=0; i<N;) {
                if (i>0 && ratings[i-1] < ratings[i]) {
                    candies[i] = candies[i-1]+1;
                } else {
                    candies[i] = 1;
                }

                int j=i;
                while (j<N-1 && ratings[j] > ratings[j+1]) {
                    j++;
                }

                if (j!=i) {
                    candies[j]=1;
                    for (int jj=j-1; jj>i; jj--) {
                        candies[jj] = candies[jj+1]+1;
                    }
                    if (candies[i+1] >= candies[i]) {
                        candies[i] = candies[i+1]+1;
                    }
                }
                i = j+1;
            }

            long totalCandies = 0;
            for (int candy : candies) {
                totalCandies += candy;
//                System.out.print(candy + " ");
            }
//            System.out.println();
            System.out.println(totalCandies);
        }
    }

}
