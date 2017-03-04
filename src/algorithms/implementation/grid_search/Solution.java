package algorithms.implementation.grid_search;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) throws IOException {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            test: for (int t=0; t<T; t++) {
                final int R = stdin.nextInt();
                final int C = stdin.nextInt();
                stdin.nextLine();

                final String[] matrix = new String[R];
                for (int r=0; r<R; r++) {
                    matrix[r] = stdin.nextLine();
                }

                final int SR = stdin.nextInt();
                final int SC = stdin.nextInt();
                stdin.nextLine();
                final Pattern[] submatrix = new Pattern[SR];
                for (int r=0; r<SR; r++) {
                    submatrix[r] = Pattern.compile(stdin.nextLine());
                }

                for (int r=0; r<R-SR+1; r++) {
                    final List<Integer> indexes = find(submatrix[0], matrix[r]);

                    for (int rr=1; rr<SR; rr++) {
                        indexes.retainAll(find(submatrix[rr], matrix[r+rr]));
                    }

                    if (!indexes.isEmpty()) {
                        System.out.println("YES");
                        continue test;
                    }
                }

                System.out.println("NO");

            }

        }
    }

    private static List<Integer> find(Pattern pattern, String line) {
        int start = 0;
        final List<Integer> list = new ArrayList<>();

        final Matcher matcher = pattern.matcher(line);
        while(matcher.find(start)) {
            start = matcher.start();
            list.add(start);
            start++;
        }
        return list;
    }

    private static class StringPair {
        private final Pattern pattern;
        private final String line;

        private StringPair(Pattern pattern, String line) {
            this.pattern = pattern;
            this.line = line;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StringPair that = (StringPair) o;

            return pattern.pattern().equals(that.pattern.pattern()) && line.equals(that.line);
        }

        @Override
        public int hashCode() {
            int result = pattern.pattern().hashCode();
            result = 31 * result + line.hashCode();
            return result;
        }
    }
}
