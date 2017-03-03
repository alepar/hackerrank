package algorithms.implementation.bomber_man;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int R = stdin.nextInt();
            final int C = stdin.nextInt();
            final long N = stdin.nextLong();
            stdin.nextLine();

            int field[] = new int[R*C];
            for (int r=0; r<R; r++) {
                final String line = stdin.nextLine();
                for (int c=0; c<C; c++) {
                    if (line.charAt(c) != '.') {
                        field[r*C+c] = 3;
                    }
                }
            }

            final List<int[]> fields = new ArrayList<>();
            int startCycle = -1;

            long i = 0;
            iter: for (; i <= N; i++) {
                for (int r = 0; r < R; r++) {
                    for (int c = 0; c < C; c++) {

                        final int cell = field[r*C+c];
                        if (i % 2 == 0) {
                            if (i > 0) {
                                if (cell == 0) {
                                    field[r*C+c] = 3;
                                } else {
                                    field[r*C+c]--;
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
                                            if (field[nr*C+nc] > 1) {
                                                field[nr*C+nc] = 0;
                                            }
                                        }
                                    }
                                }
                                field[r*C+c]--;
                            }
                        }

                    }
                }

                fields.add(Arrays.copyOf(field, field.length));
                for (int d=(int)(i%2); d<i; d+=2) {
                    if (Arrays.equals(fields.get(d), field)) {
                        startCycle = d;
                        break iter;
                    }
                }
            }

            if (i != N) {
                i = (N-startCycle) % (i-startCycle) + startCycle;
            }

            printField(R, C, fields.get((int)i));
            System.out.println();
        }
    }

    private static void printField(int r, int c, int[] field) {
        for (int ir = 0; ir < r; ir++) {
            for (int ic = 0; ic < c; ic++) {
                if(field[ir*c+ic]>0) {
                    System.out.print('O');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }
}
