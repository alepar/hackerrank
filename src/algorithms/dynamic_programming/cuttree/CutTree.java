package algorithms.dynamic_programming.cuttree;

import java.util.*;

public class CutTree {

    private static int N;
    private static int K;

    private static long result = 1;

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            N = scanner.nextInt();
            K = scanner.nextInt();

            // read
            final long[] adjacency = new long[N];
            for (int i=1; i<N; i++) {
                final int a = scanner.nextInt()-1;
                final int b = scanner.nextInt()-1;

                adjacency[a] = adjacency[a] | (1L << b);
                adjacency[b] = adjacency[b] | (1L << a);
            }

            final List<Subtree> subtrees = new ArrayList<>();
            final Set<Long> visited = new HashSet<>();
            for(int n=0; n<N; n++) {
                final int limit = subtrees.size();

                // single node subtree
                subtrees.add(new Subtree(
                        1L << n,
                        adjacency[n]
                ));
                visited.add(1L << n);

                for (int i=limit; i<subtrees.size(); i++) {
                    final Subtree subtreeRight = subtrees.get(i);
                    for (int j=0; j<limit; j++) {
                        final Subtree subtreeLeft = subtrees.get(j);

                        if (
                                !bit_intersect(subtreeLeft.subtreeNodes, subtreeRight.subtreeNodes) // subtrees do not intersect
                                && bit_intersect(subtreeLeft.subtreeNodes, subtreeRight.adjacentNodes) // and have adjacent nodes
                        ) {
                            final long unionTree = bit_union(subtreeRight.subtreeNodes, subtreeLeft.subtreeNodes);
                            if (!visited.contains(unionTree)) {
                                subtrees.add(new Subtree(
                                        unionTree, bit_union(subtreeRight.adjacentNodes, subtreeLeft.adjacentNodes)
                                ));
                                visited.add(unionTree);
                            }
                        }

                    }
                }
            }

            System.out.println(result);
        }
    }

    private static long bit_subtract(long a, long b) {
        return a & ~b;
    }

    private static long bit_union(long a, long b) {
        return a | b;
    }

    private static boolean bit_intersect(long a, long b) {
        return (b & a) > 0;
    }

    private static class Subtree {
        private final long subtreeNodes;
        private final long adjacentNodes;

        private Subtree(long subtreeNodes, long adjacentNodes) {
            this.subtreeNodes = subtreeNodes;
            this.adjacentNodes = adjacentNodes;

            if (Long.bitCount(bit_subtract(adjacentNodes, subtreeNodes)) <= K) {
                result++;
            }
        }

        private static String toString(long s) {
            final StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int i=0; i<N; i++) {
                if (bit_intersect(s, 1L << i)) {
                    sb.append(i+1).append(", ");
                }
            }
            if (sb.length()>2) {
                sb.setLength(sb.length()-2);
            }
            sb.append("}");
            return sb.toString();
        }

        @Override
        public String toString() {
            return toString(subtreeNodes) + ", " + toString(adjacentNodes);
        }
    }
}
