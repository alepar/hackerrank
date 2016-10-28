package algorithms.sorting.insertion_sort;

import java.util.*;

public class InsertionSort2 {

    public static final int CACHE_LINE = 32;

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();

                final int first = stdin.nextInt();
                final Node root = new Node(first);

                long shifts = 0;
                for (int c=1; c<N; c++) {
                    final int cur = stdin.nextInt();

                    shifts += sumOfGreater(root, cur);
                    add(root, cur);
                }

                System.out.println(shifts);
            }
        }
    }

    private static void add(Node node, int value) {
        if (node.children.isEmpty()) {
            if (node.min != value) {
                final Node oldnode = new Node(node.min);
                oldnode.count = node.count;

                final Node newnode = new Node(value);

                node.children.clear();
                if (value > node.max) {
                    node.max = value;
                    node.children.add(oldnode);
                    node.children.add(newnode);
                }
                else {
                    node.min = value;
                    node.children.add(newnode);
                    node.children.add(oldnode);
                }
            }
            node.count++;
            return;
        }

        for (Node child : node.children) {
            if (child.min <= value && value <= child.max) {
                add(child, value);
                node.count++;
                return;
            }
        }

        if (node.min != value || value != node.max) {
            if (value < node.min) {
                node.min = value;
                node.children.add(0, new Node(value));
            }
            else if (value > node.max) {
                node.max = value;
                node.children.add(node.children.size(), new Node(value));
            }

            else {
                for (int i=0; i<node.children.size(); i++) {
                    if (node.children.get(i).min > value) {
                        node.children.add(i, new Node(value));
                        break;
                    }
                }
            }
        }
        node.count++;

        if (node.children.size() > CACHE_LINE) {
            final Node left = new Node();
            final Node right = new Node();

            for (int i=0; i<node.children.size(); i++) {
                if (i*2<CACHE_LINE) {
                    left.addChild(node.children.get(i));
                } else {
                    right.addChild(node.children.get(i));
                }
            }

            node.children.clear();
            node.children.add(left);
            node.children.add(right);
        }
    }

    private static long sumOfGreater(Node node, int value) {
        if (node.max <= value) {
            return 0;
        }

        else if (node.min > value) {
            return node.count;
        }

        else {
            long sum = 0;
            for (Node child : node.children) {
                if (child.min > value) {
                    sum += child.count;
                } else if (child.max > value) {
                    sum += sumOfGreater(child, value);
                }
            }
            return sum;
        }
    }

    private static class Node {

        private final List<Node> children = new ArrayList<>();

        private int min;
        private int max;
        private int count;

        public Node(int value) {
            min = max = value;
            count = 1;
        }

        public Node() {
            min = Integer.MAX_VALUE;
            max = -1;
        }

        public void addChild(Node child) {
            if (child.min < this.min) {
                this.min = child.min;
            }

            if (child.max > this.max) {
                this.max = child.max;
            }

            this.count += child.count;
            this.children.add(child);
        }

        @Override
        public String toString() {
            return "{" + min + ", " + max +"} #" + count;
        }
    }
}
