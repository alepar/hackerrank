package prep2026.phase1.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

// https://leetcode.com/problems/evaluate-reverse-polish-notation/description/
public class EvaluateReversePolishNotation {
    class Solution {
        public int evalRPN(String[] tokens) {
            final Deque<Integer> stack = new ArrayDeque<>();

            final Pattern opPat = Pattern.compile("[-+*/]");
            for (String tkn : tokens) {
                if (opPat.matcher(tkn).matches()) {
                    final Integer r = stack.pop();
                    final Integer l = stack.pop();

                    final int res = switch (tkn) {
                        case "+" -> l + r;
                        case "-" -> l - r;
                        case "*" -> l * r;
                        case "/" -> l / r;
                        default -> throw new RuntimeException("uknown op: " + tkn);
                    };

                    stack.push(res);
                } else {
                    stack.push(Integer.valueOf(tkn));
                }
            }

            return stack.pop();
        }
    }

    public static void main(String[] args) {
        EvaluateReversePolishNotation outer = new EvaluateReversePolishNotation();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - ["2","1","+","3","*"] -> 9
        {
            int result = sol.evalRPN(new String[]{"2","1","+","3","*"});
            if (result == 9) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - ["4","13","5","/","+"] -> 6
        {
            int result = sol.evalRPN(new String[]{"4","13","5","/","+"});
            if (result == 6) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - ["10","6","9","3","+","-11","*","/","*","17","+","5","+"] -> 22
        {
            int result = sol.evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"});
            if (result == 22) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Single number
        {
            int result = sol.evalRPN(new String[]{"42"});
            if (result == 42) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Division truncates toward zero - 6 / -3 = -2 (not -3)
        {
            int result = sol.evalRPN(new String[]{"6","3","-","2","/"});
            if (result == 1) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Negative result - ["1","2","-"] -> -1
        {
            int result = sol.evalRPN(new String[]{"1","2","-"});
            if (result == -1) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Truncation toward zero with negatives - 7 / -2 = -3 (not -4)
        {
            int result = sol.evalRPN(new String[]{"7","-2","/"});
            if (result == -3) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}
