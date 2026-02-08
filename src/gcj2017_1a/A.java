package gcj2017_1a;

import java.util.Scanner;

public class A {

    private static class Matrix {

        private final int R;
        private final int C;

        private final char[] arr;

        public Matrix(int r, int c) {
            this.R = r;
            this.C = c;
            this.arr = new char[r*c];
        }

        public void set(int r, int c, char ch) {
            arr[r*C+c] = ch;
        }

        public char get(int r, int c) {
            return arr[r*C+c];
        }

        public void fill(int leftr, int leftc, int rightr, int rightc, char ch) {
            for (int r=leftr; r<=rightr; r++) {
                for (int c=leftc; c<=rightc; c++) {
                    set(r, c, ch);
                }
            }
        }
    }

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int R = stdin.nextInt();
                final int C = stdin.nextInt();
                stdin.nextLine();

                final Matrix m = new Matrix(R, C);

                for (int r=0; r<R; r++) {
                    final String line = stdin.nextLine();
                    for (int c=0; c<C; c++) {
                        final char ch = line.charAt(c);
                        m.set(r, c, ch);
                    }
                }

                char lastChar = '?';
                int startC=0, startR=0;
                for (int c=0; c<C; c++) {
                    for (int r=0; r<R; r++) {
                        if (m.get(r,c) != '?') {
                            lastChar = m.get(r,c);
                            m.fill(startR, startC, r, c, lastChar);
                            startR = r+1;
                        }
                    }

                    if (lastChar != '?') {
                        m.fill(startR, startC, R-1, c, lastChar);
                        startC = c+1;
                        startR = 0;
                        lastChar = '?';
                    }
                }

                if (startR == 0) {
                    for (int r=0; r<R; r++) {
                        m.fill(r, startC, r, C-1, m.get(r, startC-1));
                    }
                }

                System.out.format("Case #%d:\n", t+1);
                for (int r=0; r<R; r++) {
                    for (int c=0; c<C; c++) {
                        System.out.print(m.get(r, c));
                    }
                    System.out.println();
                }

            }
        }
    }

}
