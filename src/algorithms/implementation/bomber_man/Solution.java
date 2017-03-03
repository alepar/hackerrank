package algorithms.implementation.bomber_man;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int R = stdin.nextInt();
            final int C = stdin.nextInt();
            final long N = stdin.nextLong();
            stdin.nextLine();

            int field[][] = new int[R][];
            for (int r=0; r<R; r++) {
                field[r] = new int[C];

                final String line = stdin.nextLine();
                for (int c=0; c<C; c++) {
                    if (line.charAt(c) != '.') {
                        field[r][c] = 3;
                    }
                }
            }

            for (long i = 0; i <= N; i++) {
                for (int r = 0; r < R; r++) {
                    for (int c = 0; c < C; c++) {

                        final int cell = field[r][c];
                        if (i % 2 == 0) {
                            if (i > 0) {
                                if (cell == 0) {
                                    field[r][c] = 3;
                                } else {
                                    field[r][c]--;
                                }
                            }
                        } else {
                            if (cell > 0) {
                                if (cell == 1) {
                                    for (int j = 0; j < 4; j++) {
                                        final int d = (j % 2) * 2 - 1;
                                        final int nr = r + (j/2)*d;
                                        final int nc = c + (1-j/2)*d;

                                        if (nr >= 0 && nr < R && nc >= 0 && nc < C) {
                                            if (field[nr][nc] > 1) {
                                                field[nr][nc] = 0;
                                            }
                                        }
                                    }
                                }
                                field[r][c]--;
                            }
                        }

                    }
                }

            }

            printField(R, C, field);
            System.out.println();
        }
    }

    private static void printField(int r, int c, int[][] field) {
        for (int ir = 0; ir < r; ir++) {
            for (int ic = 0; ic < c; ic++) {
                if(field[ir][ic]>0) {
                    System.out.print('O');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }
}
