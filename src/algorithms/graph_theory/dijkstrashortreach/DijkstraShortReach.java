package algorithms.graph_theory.dijkstrashortreach;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DijkstraShortReach {

    public static void main(String[] args) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(System.getenv("OUTPUT_PATH"))) {
            run(System.in, fos);
        }
    }

    public static void run(InputStream is, OutputStream os) throws Exception {
        final BufferedReader stdin = new BufferedReader(new InputStreamReader(is));
        final BufferedWriter stdout = new BufferedWriter(new OutputStreamWriter(os));

        final Pattern splitby = Pattern.compile(" ");
        String[] line;

        final int T = Integer.valueOf(stdin.readLine());
        for (int t=0; t<T; t++) {
            line = splitby.split(stdin.readLine());
            final int N = Integer.valueOf(line[0]);
            final int M = Integer.valueOf(line[1]);

            final Node[] graph = new Node[N];
            for (int n=0; n<N; n++) {
                graph[n] = new Node();
            }

            final int[] edgeline = new int[3];
            for (int m=0; m<M; m++) {
                readEdgeString(stdin.readLine(), edgeline);

                final Node x = graph[edgeline[0]-1];
                final Node y = graph[edgeline[1]-1];
                final int w = edgeline[2];

                x.addEdge(y, w);
                y.addEdge(x, w);
            }

            final Node start = graph[Integer.valueOf(stdin.readLine())-1];

            final PriorityQueue<Candidate> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
            queue.add(new Candidate(start, 0));

            Candidate can;
            while((can = queue.poll()) != null) {
                if (can.node.distance == -1) {
                    can.node.distance = can.distance;

                    for (Edge edge : can.node.edges.values()) {
                        final Node dest = edge.dest;
                        final int newDist = can.node.distance + edge.distance;
                        if (dest.distance == -1) {
                            queue.add(new Candidate(dest, newDist));
                        }
                    }
                }
            }

            for (int i=0; i<graph.length; i++) {
                final Node node = graph[i];
                final int distance = node.distance;
                if (distance != 0) {
                    stdout.write(String.valueOf(distance));
                    if (i != graph.length-1) {
                        stdout.write(" ");
                    }
                }
            }
            stdout.newLine();
        }

        stdout.flush();
    }

    private static void readEdgeString(String s, int[] out) {
        int wi=0, cur=0;;
        for(int i=0; i<s.length(); i++) {
            final char c = s.charAt(i);

            if(c == ' ') {
                out[wi++] = cur;
                cur=0;
            } else {
                cur = cur*10 + c-'0';
            }
        }
        out[wi] = cur;
    }

    private static class Node {
        private final Map<Node, Edge> edges = new IdentityHashMap<>();
        private int distance = -1;

        public void addEdge(Node dest, int weight) {
            final Edge edge = edges.get(dest);
            if (edge != null) {
                if (edge.distance > weight) edge.distance = weight;
            }
            else {
                edges.put(dest, new Edge(dest, weight));
            }
        }
    }

    private static class Edge {
        private final Node dest;
        private int distance;

        private Edge(Node dest, int distance) {
            this.dest = dest;
            this.distance = distance;
        }
    }

    private static class Candidate {
        private final Node node;
        private final int distance;

        private Candidate(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}