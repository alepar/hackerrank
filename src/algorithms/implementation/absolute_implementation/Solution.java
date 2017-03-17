package algorithms.implementation.absolute_implementation;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            int T = stdin.nextInt();
            for(int t = 0; t < T; t++){
                int N = stdin.nextInt();
                int K = stdin.nextInt();

                if (K == 0) {
                    for (int i=1; i<=N; i++) {
                        System.out.print(i);
                        System.out.print(" ");
                    }
                    System.out.println();
                } else if (N % (2*K) != 0) {
                    System.out.println(-1);
                } else {
                    for(int i=0; i<N/2/K; i++) {
                        int start = i*2*K+K+1;
                        for(int ii=0; ii<K; ii++) {
                            System.out.print(start++);
                            System.out.print(" ");
                        }
                        start = i*2*K+1;
                        for(int ii=0; ii<K; ii++) {
                            System.out.print(start++);
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}
