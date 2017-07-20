package gcj_2016_1a;
import java.util.*;

public class B {

    public static class Matrix {

        private final int N;

        private final int[] values;
        private final int[] applied;

        public Matrix(int N) {
            this.N = N;
            this.values = new int[N*N];
            this.applied = new int[N*N];
        }

        public boolean applyRow(int[] line, int idx) {
            for (int i=0; i<N; i++) {
                if (applied[idx*N + i] > 0) {
                    if (line[i] != values[idx*N + i]) {
                        return false;
                    }
                }
            }

            for (int i=0; i<N; i++) {
                applied[idx*N+i]++;
                values[idx*N+i] = line[i];
            }

            return true;
        }

        public void removeRow(int idx) {
            for (int i=0; i<N; i++) {
                applied[idx*N+i]--;
            }
        }

        public boolean applyCol(int[] line, int idx) {
            for (int i=0; i<N; i++) {
                if (applied[i*N + idx] > 0) {
                    if (line[i] != values[i*N + idx]) {
                        return false;
                    }
                }
            }

            for (int i=0; i<N; i++) {
                applied[i*N+idx]++;
                values[i*N+idx] = line[i];
            }

            return true;
        }

        public void removeCol(int idx) {
            for (int i=0; i<N; i++) {
                applied[i*N+idx]--;
            }
        }

        public int[] getRow(int idx) {
            final int[] arr = new int[N];
            for (int i=0; i<N; i++) {
                arr[i] = values[idx*N+i];
            }
            return arr;
        }

        public int[] getCol(int idx) {
            final int[] arr = new int[N];
            for (int i=0; i<N; i++) {
                arr[i] = values[i*N+idx];
            }
            return arr;
        }
    }

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            for (int t=1; t<=T; t++) {
                final int N = stdin.nextInt();

                final List<int[]> lists = new ArrayList<>();
                for (int l=0; l<2*N-1; l++) {
                    final int[] arr = new int[N];
                    for (int i=0; i<N; i++) {
                        arr[i] = stdin.nextInt();
                    }
                    lists.add(arr);
                }

                final Matrix matrix = new Matrix(N);
                final int missing = recurse(matrix, 0, N, new ArrayList<>(lists));

                final List<int[]> missingCandidates = new ArrayList<>();
                missingCandidates.add(matrix.getRow(missing-1));
                missingCandidates.add(matrix.getCol(missing-1));

                for (int[] missingCandidate : missingCandidates) {
                    boolean found = false;
                    final Iterator<int[]> it = lists.iterator();
                    while (it.hasNext()) {
                        int[] list = it.next();
                        if (Arrays.equals(list, missingCandidate)) {
                            found = true;
                            it.remove();
                            break;
                        }
                    }

                    if (!found) {
                        final StringBuilder sb = new StringBuilder();
                        for (int i : missingCandidate) {
                            sb.append(i).append(' ');
                        }
                        sb.setLength(sb.length()-1);
                        System.out.format("Case #%d: %s\n", t, sb.toString());
                        break;
                    }
                }
            }
        }
    }

    private static int recurse(Matrix matrix, int idx, int N, List<int[]> lists) {
        if (idx == N) {
            return -1;
        }

        int min = Integer.MAX_VALUE;
        List<int[]> candidates = new ArrayList<>();
        for (int[] arr: lists) {
            if (arr[idx] < min) { min = arr[idx]; candidates.clear(); candidates.add(arr); }
            else if (arr[idx] == min) { candidates.add(arr); }
        }

        lists.removeAll(candidates);

        if (candidates.size() == 1) {
            final int[] line = candidates.get(0);
            if (matrix.applyRow(line, idx)) {
                int ret = recurse(matrix, idx + 1, N, lists);
                if (ret != 0) return idx+1;
                matrix.removeRow(idx);
            }
            if (matrix.applyCol(line, idx)) {
                int ret = recurse(matrix, idx + 1, N, lists);
                if (ret != 0) return idx+1;
                matrix.removeCol(idx);
            }
        } else if (candidates.size() == 2) {
            int ret = 0;
            for (int i=0; i<2; i++) {
                final int[] rowLine = candidates.get(i);
                final int[] colLine = candidates.get(1-i);

                if (matrix.applyRow(rowLine, idx)) {
                    if(matrix.applyCol(colLine, idx)) {
                        ret = recurse(matrix, idx + 1, N, lists);
                        if (ret != 0) return ret;
                        matrix.removeCol(idx);
                    }
                    matrix.removeRow(idx);
                }
            }
        } else {
            throw new RuntimeException("algo error");
        }

        lists.addAll(candidates);

        return 0;
    }
}
