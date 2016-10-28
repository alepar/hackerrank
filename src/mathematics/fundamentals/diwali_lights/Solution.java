package mathematics.fundamentals.diwali_lights;

import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        final BigInteger two = new BigInteger("2");

        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();

                final BigInteger answer = two.pow(N);
                String answerString = answer.toString();
                answerString = answerString.substring(Math.max(0, answerString.length()-5), answerString.length());

                int answerInteger = Integer.valueOf(answerString);
                if (answerInteger == 0) {
                    answerInteger = 99999;
                } else {
                    answerInteger-=1;
                }
                System.out.println(answerInteger);
            }
        }
    }
}
