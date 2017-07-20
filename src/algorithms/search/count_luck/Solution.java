package algorithms.search.count_luck;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int R = stdin.nextInt();
                final int C = stdin.nextInt();
                stdin.nextLine();

                final int map[][] = new int[R][C];

                int startR=-1, startC=-1;
                int endR=-1, endC=-1;

                for (int r=0; r<R; r++) {
                    final String str = stdin.nextLine();
                    for (int c=0; c<C; c++) {
                        final char ch = str.charAt(c);

                        switch(ch) {
                            case 'X':
                                map[r][c] = -1;
                                break;
                            case 'M':
                                startR=r;
                                startC=c;
                                map[r][c] = 1;
                                break;
                            case '*':
                                endR=r;
                                endC=c;
                                break;
                            case '.':
                                break;
                            default:
                                throw new RuntimeException("ops char: " + ch);
                        }
                    }
                }

                final int K = stdin.nextInt();

                if (startR == -1) {
                    throw new RuntimeException("no start found");
                }
                if (endR == -1) {
                    throw new RuntimeException("no end found");
                }

                final Queue<Integer> queue = new LinkedList<>();
                queue.add(startR*C+startC);

                while(!queue.isEmpty()) {
                    final Integer num = queue.remove();
                    final int r = num / C;
                    final int c = num % C;

                    if (r==endR && c==endC) {
                        break;
                    }

                    if (r+1<R) {
                        if (map[r+1][c] == 0) {
                            map[r+1][c] = map[r][c]+1;
                            queue.add((r+1)*C+c);
                        }
                    }
                    if(r-1>=0) {
                        if (map[r-1][c] == 0) {
                            map[r-1][c] = map[r][c]+1;
                            queue.add((r-1)*C+c);
                        }
                    }
                    if (c+1<C) {
                        if (map[r][c+1] == 0) {
                            map[r][c+1] = map[r][c]+1;
                            queue.add(r*C+c+1);
                        }
                    }
                    if(c-1>=0) {
                        if (map[r][c-1] == 0) {
                            map[r][c-1] = map[r][c]+1;
                            queue.add(r*C+c-1);
                        }
                    }
                }

                int r=endR, c=endC;
                int k=0;
                while (r != startR || c!=startC) {
                    int prevR=-1, prevC=-1;

                    if (r+1<R) {
                        if (map[r+1][c] == map[r][c]-1) {
                            prevR=r+1; prevC=c;
                        }
                    }
                    if(r-1>=0) {
                        if (map[r-1][c] == map[r][c]-1) {
                            prevR=r-1; prevC=c;
                        }
                    }
                    if (c+1<C) {
                        if (map[r][c+1] == map[r][c]-1) {
                            prevR=r; prevC=c+1;
                        }
                    }
                    if(c-1>=0) {
                        if (map[r][c-1] == map[r][c]-1) {
                            prevR=r; prevC=c-1;
                        }
                    }

                    if (prevR == -1) {
                        throw new RuntimeException("no path back");
                    }

                    boolean many = false;
                    if (prevR+1<R) {
                        if (map[prevR+1][prevC] == map[r][c] && prevR+1 != r) {
                            many = true;
                        }
                    }
                    if(prevR-1>=0) {
                        if (map[prevR-1][prevC] == map[r][c] && prevR-1 != r) {
                            many = true;
                        }
                    }
                    if (prevC+1<C) {
                        if (map[prevR][prevC+1] == map[r][c] && prevC+1 != c) {
                            many = true;
                        }
                    }
                    if(prevC-1>=0) {
                        if (map[prevR][prevC-1] == map[r][c] && prevC-1 != c) {
                            many = true;
                        }
                    }

                    if (many) {
                        k++;
                    }

                    r=prevR;
                    c=prevC;
                }

                if (k == K) {
                    System.out.println("Impressed");
                } else {
                    System.out.println("Oops!");
                }
            }
        }
    }
}
