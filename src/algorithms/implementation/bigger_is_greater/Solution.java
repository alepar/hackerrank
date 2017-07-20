package algorithms.implementation.bigger_is_greater;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            stdin.nextLine();

            for (int t=0; t<T; t++) {
                final String w = stdin.nextLine();

                int i;
                final int[] chars = new int[255];
                chars[w.charAt(w.length()-1)]++;

                for (i=w.length()-2; i>=0; i--) {
                    chars[w.charAt(i)]++;
                    if (w.charAt(i) < w.charAt(i+1)) {
                        break;
                    }
                }

                if (i == -1) {
                    System.out.println("no answer");
                } else {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(w.substring(0, i));

                    for(i=w.charAt(i)+1; i<255; i++) {
                        if (chars[i] > 0) {
                            sb.append((char)i);
                            chars[i]--;
                            break;
                        }
                    }

                    for(i=0; i<255; i++) {
                        while(chars[i]>0) {
                            sb.append((char)i);
                            chars[i]--;
                        }
                    }

                    System.out.println(sb.toString());
                }
            }
        }
    }
}
