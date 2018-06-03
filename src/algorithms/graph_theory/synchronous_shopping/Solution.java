package algorithms.graph_theory.synchronous_shopping;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        run(System.in);
    }

    public static void run(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            final int N = scanner.nextInt();
            final int M = scanner.nextInt();
            final int K = scanner.nextInt();
            final Node[] nodes = new Node[N];
            for (int n=0; n<N; n++) {
                nodes[n] = new Node(1<<K);
            }

            for (int n=0; n<N; n++) {
                final int T = scanner.nextInt();
                for (int t=0; t<T; t++) {
                    nodes[n].addFish(scanner.nextInt());
                }
            }

            for (int m=0; m<M; m++) {
                final int src = scanner.nextInt()-1;
                final int dst = scanner.nextInt()-1;
                final int wgt = scanner.nextInt();

                nodes[src].edges.add(new Edge(dst, wgt));
                nodes[dst].edges.add(new Edge(src, wgt));
            }



            final Queue<Key> queue = new PriorityQueue<>(Comparator.comparingInt(c -> c.distance));
            queue.add(new Key(N-1, nodes[N-1].fishes, 0));

            Key c;
            while((c = queue.poll()) != null) {
                final int n = c.node;
                final int fishes = c.fishes;

                if (nodes[n].cache[c.fishes] != null) continue;

                nodes[n].cache[c.fishes] = c.distance;

                for (Edge e: nodes[n].edges) {
                    final int dist_n = c.distance + e.weight;
                    final int n_n = e.dst;
                    final int fishes_n = fishes | nodes[n_n].fishes;

                    if (nodes[n_n].cache[fishes_n] == null) {
                        queue.add(new Key(n_n, fishes_n, dist_n));
                    }
                }
            }



            final int all_fishes = (1<<K)-1;
            final Integer[] arr = nodes[0].cache;
            int min = Integer.MAX_VALUE;

            for (int i = 0; i<arr.length; i++) {
                if (arr[i] == null) continue;

                if (i == all_fishes) {
                    final int min_c = arr[i];
                    if (min_c < min) min = min_c;
                }
                else {
                    for (int j = i + 1; j < arr.length; j++) {
                        if (arr[j] == null) continue;

                        if ((i | j) == all_fishes) {
                            final int min_c = Math.max(arr[i], arr[j]);
                            if (min_c < min) min = min_c;
                        }
                    }
                }
            }

            System.out.println(min);
        }
    }

    public static class Key {
        private final int node;
        private final int fishes;
        private final int distance;

        public Key(int node, int fishes, int distance) {
            this.node = node;
            this.fishes = fishes;
            this.distance = distance;
        }
    }

    public static final class Node {
        private final List<Edge> edges = new ArrayList<>();
        private final Integer[] cache;

        private int fishes;

        public Node(int s) {
            cache = new Integer[s];
        }

        public void addFish(int k) {
            fishes |= 1 << (k-1);
        }

    }

    public static final class Edge {
        private final int dst;
        private final int weight;

        public Edge(int dst, int weight) {
            this.dst = dst;
            this.weight = weight;
        }
    }
}