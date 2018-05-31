package algorithms.graph_theory.dijkstrashortreach;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Perf {
    public static void main(String[] args) throws Exception {
        final String inputPath = Perf.class.getPackage().getName().replace('.', '/') + '/' + "input07.txt";

        try(
                InputStream is = Perf.class.getClassLoader().getResourceAsStream(inputPath);
                OutputStream os = new FileOutputStream("solution")) {
            final long start = System.nanoTime();
//            DijkstraShortReach.run(is, os);
            GayatriDijkstra.run(is, os);
            final long end = System.nanoTime();

            System.out.println((end-start)/1_000_000/1000.0 + "s");
        }
    }
}
