package algorithms.dynamic_programming.cuttree;

import java.util.*;

public class CutTree2 {

    private static int N;
    private static int K;

    private static Node[] nodes;

    private static long result = 1;

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            N = scanner.nextInt();
            K = scanner.nextInt();

            nodes = new Node[N];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node(i+1);
            }

            // read adjacency
            for (int i=1; i<N; i++) {
                final int a = scanner.nextInt()-1;
                final int b = scanner.nextInt()-1;

                nodes[a].linked.add(nodes[b]);
                nodes[b].linked.add(nodes[a]);
            }

            walk(nodes[0], null);

            System.out.println(result);
        }
    }

    private static void walk(Node node, Node from) {
        for (Node child : node.linked) {
            if (child != from) {
                walk(child, node);
            }
        }

        node.hist.put(node.linked.size(), 1);
        for (Node child : node.linked) {
            if (child != from) {
                node.hist.add(child.hist);
            }
        }

        result += node.hist.passing();
    }

    private static class Node {
        private final int id;
        private final List<Node> linked = new ArrayList<>();

        private final Hist hist = new Hist();

        private Node(int id) {
            this.id = id;
        }
    }

    private static class Hist {
        private final Map<Integer, Long> map = new HashMap<>();

        public void put(int neighbours, long count) {
            map.put(neighbours, count);
        }

        public void add(Hist hist) {
            final List<Map.Entry<Integer, Long>> newEntries = new ArrayList<>();
            for (Map.Entry<Integer, Long> left : this.map.entrySet()) {
                for (Map.Entry<Integer, Long> right : hist.map.entrySet()) {
                    newEntries.add(new AbstractMap.SimpleEntry<>(left.getKey()+right.getKey()-2, left.getValue()*right.getValue()));
                }
            }

            for (Map.Entry<Integer, Long> newEntry : newEntries) {
                Long cur = this.map.get(newEntry.getKey());
                if (cur == null) {
                    cur = 0L;
                }
                cur += newEntry.getValue();
                this.map.put(newEntry.getKey(), cur);
            }
        }

        public long passing() {
            long r = 0;
            for (Map.Entry<Integer, Long> entry : map.entrySet()) {
                if (entry.getKey() <= K) {
                    r += entry.getValue();
                }
            }
            return r;
        }
    }
}
