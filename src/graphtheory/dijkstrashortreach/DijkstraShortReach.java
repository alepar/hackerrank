package graphtheory.dijkstrashortreach;

import java.util.*;

public class DijkstraShortReach {

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt(); // nodes
                final int M = stdin.nextInt(); // edges

                final Node[] graph = new Node[N];
                for (int n=0; n<N; n++) {
                    graph[n] = new Node();
                }

                for (int m=0; m<M; m++) {
                    final Node x = graph[stdin.nextInt()-1];
                    final Node y = graph[stdin.nextInt()-1];
                    final int w = stdin.nextInt();

                    x.addEdge(y, w);
                    y.addEdge(x, w);
                }

                final Node start = graph[stdin.nextInt()-1];
                start.distance = 0;

                final PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));
                queue.add(start);

                Node src;
                while((src = queue.poll()) != null) {
                    for (Edge edge : src.edges) {
                        final Node dest = edge.dest;
                        final int newDist = src.distance + edge.distance;
                        if (dest.distance == -1 || dest.distance > newDist) {
                            dest.distance = newDist;
                            queue.remove(dest);
                            queue.add(dest);
                        }
                    }
                }

                final StringBuilder sb = new StringBuilder();
                for (Node node : graph) {
                    final int distance = node.distance;
                    if (distance != 0) {
                        sb.append(distance).append(" ");
                    }
                }
                sb.setLength(sb.length()-1);
                System.out.println(sb.toString());
            }
        }
    }

    private static class Node {
        private final List<Edge> edges = new ArrayList<>();
        private int distance = -1;

        public void addEdge(Node dest, int weight) {
            edges.add(new Edge(dest, weight));
        }
    }

    private static class Edge {
        private final Node dest;
        private final int distance;

        private Edge(Node dest, int distance) {
            this.dest = dest;
            this.distance = distance;
        }
    }
}
