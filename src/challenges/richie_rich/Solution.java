package challenges.richie_rich;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try {
            try(Scanner stdin = new Scanner(System.in)) {
                final int N = stdin.nextInt();
                final int K = stdin.nextInt();

                final char[] str = stdin.next().toCharArray();

                int cost = 0;
                for (int i=0; i<N/2; i++) {
                    if (str[i] != str[N-i-1]) {
                        cost++;
                    }
                }

                if (cost > K) {
                    System.out.println(-1);
                    return;
                }

                for (int i=0; i<N/2; i++) {
                    if (cost < K) {
                        if (str[i]!='9' && str[N-i-1]!='9') {
                            if (str[i]==str[N-i-1]) {
                                if (cost+1 < K) {
                                    cost++;
                                } else {
                                    continue;
                                }
                            }

                            cost++;

                            str[i]=str[N-i-1]='9';
                            continue;
                        }
                    }

                    str[i] = str[N-i-1] = (char)Math.max(str[i], str[N-i-1]);
                }

                if (N%2==1 && cost<K && str[N/2]!='9') {
                    cost++;
                    str[N/2] = '9';
                }

                for(int i=0; i<N; i++) {
                    System.out.print(str[i]);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
