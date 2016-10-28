package algorithms.graph_theory.even_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static int cut = 0;
    private static List<Integer>[] adjacency;

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int V = stdin.nextInt();
            final int E = stdin.nextInt();

            adjacency = new List[V];
            for (int i=0; i<V; i++) {
                adjacency[i] = new ArrayList<>();
            }

            for (int e=0; e<E; e++) {
                final int vs = stdin.nextInt()-1;
                final int vd = stdin.nextInt()-1;
                adjacency[Math.min(vs, vd)].add(Math.max(vs, vd));
            }

            if (V%2 != 0) {
                System.out.println(0);
            } else {
                recurse(0);
                System.out.println(cut-1);
            }
        }
    }

    private static int recurse(int parent) {
        int count = 1;
        for (int child : adjacency[parent]) {
            count += recurse(child);
        }

        if (count%2 == 0) {
            cut++;
            return 0;
        } else {
            return count;
        }
    }

}
