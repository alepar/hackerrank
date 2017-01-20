package algorithms.graph_theory.jack_goes_to_rapture;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();
            final int E = stdin.nextInt();

            final long[] costs = new long[N];
            final List<Edge>[] adjacentList = new List[N];
            for (int i=0; i<N; i++) {
                costs[i] = Long.MAX_VALUE;
                adjacentList[i] = new ArrayList<>();
            }

            for (int i=0; i<E; i++) {
                final int l = stdin.nextInt()-1;
                final int r = stdin.nextInt()-1;
                final long cost = stdin.nextLong();
                adjacentList[l].add(new Edge(r, cost));
                adjacentList[r].add(new Edge(l, cost));
            }

            final PriorityQueue<Vertice> queue = new PriorityQueue<>();
            queue.add(new Vertice(0, 0L));

            while (!queue.isEmpty()) {
                final Vertice v = queue.poll();

                if (v.cost < costs[v.vertice]) {
                    costs[v.vertice] = v.cost;

                    if (v.vertice == N-1) {
                        System.out.println(v.cost);
                        return;
                    }

                    for (Edge edge : adjacentList[v.vertice]) {
                        queue.add(new Vertice(edge.dest, Math.max(v.cost, edge.cost)));
                    }
                }
            }

            System.out.println("NO PATH EXISTS");
        }
    }

    private static class Edge {

        private final int dest;
        private final long cost;

        public Edge(int dest, long cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }

    private static class Vertice implements Comparable<Vertice> {

        private final int vertice;
        private final long cost;

        public Vertice(int vertice, long cost) {
            this.vertice = vertice;
            this.cost = cost;
        }

        @Override
        public int compareTo(Vertice that) {
            return Long.compare(this.cost, that.cost);
        }
    }
}
