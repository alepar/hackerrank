package algorithms.graph_theory.synchronous_shopping;

import java.io.InputStream;

public class Perf {
    public static void main(String[] args) throws Exception {
        final String inputPath = Perf.class.getPackage().getName().replace('.', '/') + '/' + "input25.txt";

        try(InputStream is = Perf.class.getClassLoader().getResourceAsStream(inputPath)) {
            final long start = System.nanoTime();
            Solution.run(is);
            final long end = System.nanoTime();

            System.out.println((end-start)/1_000_000/1000.0 + "s");
        }
    }
}
