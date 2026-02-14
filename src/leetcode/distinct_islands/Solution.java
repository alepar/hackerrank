package leetcode.distinct_islands;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int ROWS = stdin.nextInt();
            final int COLS = stdin.nextInt();
            stdin.nextLine();

            if (ROWS < 1 || COLS < 1) throw new IllegalArgumentException();

            final int grid[][] = new int[ROWS][COLS];
            for (int r=0; r<ROWS; r++) {
                final String line = stdin.nextLine();
                for (int c=0; c<COLS; c++) {
                    grid[r][c] = line.charAt(c)-'0';
                }
            }

            System.out.println(countDistinct(grid));
        }
    }

    public static int countDistinct(int[][] grid) {
        int mark = -1;
        for (int r=0; r<grid.length; r++) {
            for (int c=0; c<grid[r].length; c++) {
                if (grid[r][c] == 1) {
                    wave(grid, r, c, mark--);
                }
            }
        }

        final Set<String> distinct = new HashSet<>();
        for (int m=-1; m>mark; m--) {
            distinct.add(islandToCanonicString(grid, m));
        }

        return distinct.size();
    }

    private static String islandToCanonicString(int[][] grid, int m) {
        int minR = grid.length;
        int minC = grid[0].length;

        for (int r=0; r<grid.length; r++) {
            for (int c=0; c<grid[r].length; c++) {
                if (grid[r][c] == m) {
                    if (r < minR) minR=r;
                    if (c < minC) minC=c;
                }
            }
        }

        final int[][] canGrid = new int[grid.length][grid[0].length];
        for (int r=0; r<grid.length; r++) {
            for (int c=0; c<grid[r].length; c++) {
                if (grid[r][c] == m) {
                    canGrid[r-minR][c-minC] = 1;
                }
            }
        }

        final StringBuilder sb = new StringBuilder();
        for (int r=0; r<canGrid.length; r++) {
            for (int c=0; c<canGrid[r].length; c++) {
                sb.append((char)canGrid[r][c]);
            }
        }

        return sb.toString();
    }

    private static void wave(int[][] grid, int r, int c, int mark) {
        final Queue<Coord> queue = new ArrayDeque<>();
        queue.add(new Coord(r, c));

        while (!queue.isEmpty()) {
            final Coord coord = queue.remove();

            for (Coord delta: NEIGHBOURS) {
                final Coord newCoord = new Coord(coord.r+delta.r, coord.c+delta.c);
                if (newCoord.r >=0 && newCoord.r<grid.length && newCoord.c>=0 && newCoord.c<grid[r].length && grid[newCoord.r][newCoord.c] == 1) {
                    grid[newCoord.r][newCoord.c] = mark;
                    queue.add(newCoord);
                }
            }
        }
    }

    private static Set<Coord> NEIGHBOURS = new HashSet<Coord>() {{
        add(new Coord(-1, 0));
        add(new Coord(1, 0));
        add(new Coord(0, -1));
        add(new Coord(0, 1));
    }};

    private static class Coord {
        private final int r;
        private final int c;

        private Coord(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
