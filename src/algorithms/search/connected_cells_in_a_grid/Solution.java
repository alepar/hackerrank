package algorithms.search.connected_cells_in_a_grid;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int R = stdin.nextInt();
            final int C = stdin.nextInt();

            final int[][] m = new int[R][C];

            for (int r=0; r<R; r++) {
                for (int c=0; c<C; c++) {
                    m[r][c] = stdin.nextInt();
                }
            }

            int count = 0;
            for (int r=0; r<R; r++) {
                for (int c=0; c<C; c++) {
                    if (m[r][c] == 1) {
                        mark(m, r, c, R, C, --count);
                    }
                }
            }

            if (count == 0) {
                System.out.println(0);
            } else {
                final int counts[] = new int[-count];
                int max = 0;
                for (int r=0; r<R; r++) {
                    for (int c=0; c<C; c++) {
                        if (m[r][c] < 0) {
                            final int idx = -m[r][c]-1;
                            counts[idx]++;
                            if (max < counts[idx]) {
                                max = counts[idx];
                            }
                        }
                    }
                }

                System.out.println(max);
            }

        }
    }

    private static void mark(int[][] m, int r, int c, int R, int C, int label) {
        m[r][c] = label;
        for (int dr=-1; dr<=1; dr++) {
            for (int dc=-1; dc<=1; dc++) {
                final int r2=r+dr, c2=c+dc;
                if (r2>=0 && r2<R && c2>=0 && c2<C) {
                    if (m[r2][c2] == 1) {
                        mark(m, r2, c2, R, C, label);
                    }
                }
            }
        }
    }
}