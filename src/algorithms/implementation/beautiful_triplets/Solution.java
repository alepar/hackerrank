package algorithms.implementation.beautiful_triplets;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();
            final int D = stdin.nextInt();

            final int[] arr = new int[N];
            for (int i=0; i<N; i++) {
                arr[i] = stdin.nextInt();
            }

            if (N < 3) {
                System.out.println(0);
                return;
            }

            int count=0;
            int il=0, im=1, ir=2;

            for (il=0; il<N-2; il++) {

                for (; im<N-1; im++) {
                    if (arr[il]+D <= arr[im]) {
                        break;
                    }
                }

                for (; ir<N; ir++) {
                    if (arr[im]+D <= arr[ir]) {
                        break;
                    }
                }

                if ((ir<N) && (im<N-1) && (il<N-2) && (arr[il]+D == arr[im]) && (arr[im]+D == arr[ir])) {
                    count++;
                }
            }

            System.out.println(count);
        }
    }

}
