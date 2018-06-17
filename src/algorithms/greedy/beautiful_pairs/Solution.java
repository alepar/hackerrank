package algorithms.greedy.beautiful_pairs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Solution {

    // Complete the beautifulPairs function below.
    static int beautifulPairs(int[] A, int[] B) {
        if (A.length != B.length) throw new IllegalArgumentException();

        final Map<Integer, Integer> as = new HashMap<>();
        for (int i: A) {
            as.put(i, as.getOrDefault(i, 0)+1);
        }
        final Map<Integer, Integer> bs = new HashMap<>();
        for (int i: B) {
            bs.put(i, bs.getOrDefault(i, 0)+1);
        }

        final Set<Integer> andSet = new HashSet<>(as.keySet());
        andSet.retainAll(bs.keySet());

        int result = 0;
        for (int i: andSet) {
            result += Math.min(as.get(i), bs.get(i));
        }

        if (result < A.length) {
            result++;
        } else {
            result--;
        }
        return result;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] A = new int[n];

        String[] AItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int AItem = Integer.parseInt(AItems[i]);
            A[i] = AItem;
        }

        int[] B = new int[n];

        String[] BItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int BItem = Integer.parseInt(BItems[i]);
            B[i] = BItem;
        }

        int result = beautifulPairs(A, B);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
