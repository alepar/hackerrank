package algorithms.recursion.power_sum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    // Complete the powerSum function below.
    static int powerSum(int X, int N) {
        final List<Integer> arr = new ArrayList<>();
        int i=1, pow=1;

        do {
            arr.add(pow);
            i++;
            pow = pow(i, N);
        } while(pow <= X);

        return recurse(arr, arr.size(), X);
    }

    private static int recurse(List<Integer> arr, int i, int x) {
        if (x == 0) return 1;
        if (x<0 || i==0) return 0;

        return recurse(arr, i-1, x) + recurse(arr, i-1, x-arr.get(i-1));
    }

    private static int pow(int a, int n) {
        int r = 1;
        while (n-- > 0) {
            r *= a;
        }
        return r;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int X = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int N = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int result = powerSum(X, N);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}