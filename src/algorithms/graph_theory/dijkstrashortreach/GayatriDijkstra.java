package algorithms.graph_theory.dijkstrashortreach;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Pattern;

public class GayatriDijkstra {

    public static class Node {
        int label;
        int distance;
        Map<Node, Edge> neighbours;

        public Node(int label) {
            this.label = label;
            distance = Integer.MAX_VALUE;
            neighbours = new IdentityHashMap<>();
        }

        public void addNeighbour(Edge e) {
            Edge o = neighbours.get(e.neighbourNode);
            if (o != null) {
                if (e.weight < o.weight) {
                    o.weight = e.weight;
                }
                return;
            }

            this.neighbours.put(e.neighbourNode, e);
        }
    }

    public static class Edge {
        Node neighbourNode;
        int weight;

        public Edge(Node neighbourNode, int weight) {
            this.neighbourNode = neighbourNode;
            this.weight = weight;
        }
    }

    public static class Candidate implements Comparable<Candidate>{
        Node n;
        int distance;

        public Candidate(Node n, int distance) {
            this.n = n;
            this.distance = distance;
        }

        @Override
        public int compareTo(Candidate c) {
            return Integer.compare(this.distance, c.distance);
        }

    }

    // Complete the shortestReach function below.
    static int[] shortestReach(int n, int[][] edges, int s) {
        int[] dist = new int[n+1];
        Node[] nodes = new Node[n+1];
        Queue<Candidate> pq = new PriorityQueue<>();

        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dst = edges[i][1];
            int wgt = edges[i][2];

            if (nodes[src] == null) {
                nodes[src] = new Node(src);
            }
            if (nodes[dst] == null) {
                nodes[dst] = new Node(dst);
            }

            nodes[src].addNeighbour(new Edge(nodes[dst], wgt));
            nodes[dst].addNeighbour(new Edge(nodes[src], wgt));
        }

        pq.add(new Candidate(nodes[s], 0));
        while(!pq.isEmpty()) {
            Candidate can = pq.poll();

            if (can.n.distance == Integer.MAX_VALUE) {
                can.n.distance = can.distance;
                dist[can.n.label] = can.n.distance;

                for (Edge edge: can.n.neighbours.values()) {
                    int alt = can.n.distance + edge.weight;
                    if (edge.neighbourNode.distance == Integer.MAX_VALUE) {
                        Candidate neighbourCan = new Candidate(edge.neighbourNode, alt);
                        pq.offer(neighbourCan);
                    }
                }
            }

        }

        return dist;
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

            final int[][] EDGES = new int[M][3];
            final int[] edgeline = new int[3];
            for(int m=0; m<M; m++) {
                readEdgeString(stdin.readLine(), edgeline);

                EDGES[m][0] = edgeline[0];
                EDGES[m][1] = edgeline[1];
                EDGES[m][2] = edgeline[2];
            }

            final int S = Integer.valueOf(stdin.readLine());

            final int[] result = shortestReach(N, EDGES, S);
            for (int i = 1; i < result.length; i++) {
                if (result[i] == 0) {
                    if (i != S) {
                        stdout.write(String.valueOf(-1));
                        if (i != result.length - 1) {
                            stdout.write(" ");
                        }
                    }
                } else {
                    stdout.write(String.valueOf(result[i]));
                    if (i != result.length - 1) {
                        stdout.write(" ");
                    }
                }
            }
            stdout.newLine();
        }

        stdout.flush();
    }
}