package algorithms.graph_theory.journey_to_the_moon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int N = stdin.nextInt();
            final int I = stdin.nextInt();

            final UnionFind uf = new UnionFind(N);

            for (int i=0; i<I; i++) {
                final int left = stdin.nextInt();
                final int right = stdin.nextInt();

                uf.union(left, right);
            }

            final List<Integer> setPowers = uf.getSetPowers();

            long powerSum = 0;
            for (int i=0; i<setPowers.size(); i++) {
                powerSum += setPowers.get(i);
            }

            long variants = 0;
            for (int i=0; i<setPowers.size()-1; i++) {
                powerSum -= setPowers.get(i);
                variants += setPowers.get(i) * powerSum;
            }

            System.out.println(variants);
        }
    }

    private static class UnionFind {

        private final int[] parents;
        private final int[] counts;

        public UnionFind(int size) {
            parents = new int[size];
            counts = new int[size];

            for (int i=0; i<size; i++) {
                counts[i] = 1;
                parents[i] = -1;
            }
        }


        public void union(int left, int right) {
            int leftParent = findParent(left);
            int rightParent = findParent(right);

            if (leftParent != rightParent) {
                parents[leftParent] = rightParent;
                counts[rightParent] += counts[leftParent];
            }
        }

        private int findParent(int elem) {
            while (parents[elem] != -1) {
                elem = parents[elem];
            }
            return elem;
        }

        public List<Integer> getSetPowers() {
            final List<Integer> powers = new ArrayList<>();
            for (int i=0; i<parents.length; i++) {
                if (parents[i] == -1) {
                    powers.add(counts[i]);
                }
            }
            return powers;
        }
    }
}
