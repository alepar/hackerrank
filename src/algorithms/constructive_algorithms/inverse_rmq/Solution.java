package algorithms.constructive_algorithms.inverse_rmq;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.NoSuchObjectException;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        try {
            try(Scanner stdin = new Scanner(System.in)) {
                final int N = stdin.nextInt();
                final int a[] = new int[2*N-1];

                final Map<Integer, Integer> counts = new HashMap<>();
                for (int i=0; i<2*N-1; i++) {
                    a[i] = stdin.nextInt();
                    Integer count = counts.get(a[i]);
                    if (count == null) {
                        count = 0;
                    }
                    counts.put(a[i], count+1);
                }

                final int levels = log2(N)+1;
                final NavigableSet<Integer>[] numbersByLevel = new NavigableSet[levels];
                for (int l=0; l<levels; l++) {
                    numbersByLevel[l] = new TreeSet<>();
                }

                for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
                    if (e.getValue() > levels) {
                        throw new NoSuchObjectException("");
                    }
                    numbersByLevel[levels-e.getValue()].add(e.getKey());
                }

                if (numbersByLevel[0].size() != 1) {
                    throw new NoSuchObjectException("");
                }
                for (int l=1; l<levels; l++) {
                    if (numbersByLevel[l].size() != 1<<(l-1)) {
                        throw new NoSuchObjectException("");
                    }
                }

                final int o[] = new int[2*N-1];
                o[0] = numbersByLevel[0].first();

                for (int i=1; i<o.length; i+=2) {
                    final int level = level(i);

                    o[i] = o[parent(i)];

                    final Integer next = numbersByLevel[level].ceiling(o[i]);
                    numbersByLevel[level].remove(next);
                    o[i+1] = next;
                }
                System.out.println("YES");
                try(BufferedWriter w = new BufferedWriter(new OutputStreamWriter(System.out))) {
                    for (int i = 0; i < o.length; i++) {
                        w.write(Integer.toString(o[i]));
                        w.write(" ");
                    }
                    w.flush();
                }
            }
        } catch (NoSuchObjectException e) {
            System.out.println("NO");
        }
    }

    private static int level(int i) {
        return Integer.numberOfTrailingZeros(Integer.highestOneBit(i+1));
    }

    private static int parent(int i) {
        return (i-1)/2;
    }

    private static int log2(int N) {
        return Integer.numberOfTrailingZeros(N);
    }

}