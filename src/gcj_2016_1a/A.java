package gcj_2016_1a;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();
            stdin.nextLine();
            for (int t=0; t<T; t++) {
                final String line = stdin.nextLine();
                final Deque<Character> chars = new LinkedList<>();

                chars.add(line.charAt(0));
                for (int i=1; i<line.length(); i++) {
                    if (chars.peekFirst() <= line.charAt(i)) {
                        chars.addFirst(line.charAt(i));
                    } else {
                        chars.addLast(line.charAt(i));
                    }
                }

                final StringBuilder sb = new StringBuilder();
                for (int i=0; i<chars.size(); i++) {
                    sb.append(chars.removeFirst());
                }

                System.out.format("Case #%d: %s\n", t, sb.toString());
            }
        }
    }
}
