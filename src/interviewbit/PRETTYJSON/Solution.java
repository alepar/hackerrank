package interviewbit.PRETTYJSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public ArrayList<String> prettyJSON(String json) {
        final StringBuilder sb = new StringBuilder();

        for(char c: json.toCharArray()) {
            if (Character.isWhitespace(c)) continue;

            if (c == '{' || c == '[') {
                sb.append('\n').append(c).append('\n');
            } else if (c == '}' || c == ']') {
                sb.append('\n').append(c);
            } else if (c == ',') {
                sb.append(c).append('\n');
            } else {
                sb.append(c);
            }
        }

        return reident(new ArrayList<>(Arrays.asList(sb.toString().trim().split("\n+"))));
    }

    public ArrayList<String> reident(ArrayList<String> lines) {
        final ArrayList<String> idented = new ArrayList<>(lines.size());
        int level = 0;

        for (String s : lines) {
            if ("{".equals(s) || "[".equals(s)) {
                idented.add(ident(s, level++));
            } else if (s.startsWith("}") || s.startsWith("]")) {
                idented.add(ident(s, --level));
            } else {
                idented.add(ident(s, level));
            }
        }

        return idented;
    }

    private static String ident(String s, int level) {
        final char[] tabs = new char[level];
        Arrays.fill(tabs, '\t');
        return new String(tabs) + s;
    }

    public static void main(String[] args) {
        test("{A:\"B\",C:{D:\"E\",F:{G:\"H\",I:\"J\"}},C:{D:\"E\",F:{G:\"H\",I:\"J\"}}}");
        test("[\"foo\",{\"bar\":[\"baz\",null,1.0,2]}]");
    }

    private static void test(String json) {
        final List<String> strings = new Solution().prettyJSON(json);
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println("\n.\n");
    }
}