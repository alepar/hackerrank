package prep2026.phase1.stack;

// https://leetcode.com/problems/min-stack/description/
public class MinStack {

    private Node top;
    private Node min;

    public class Node {
        final int value;
        final Node prev;
        final Node prevMin;

        public Node(int value, Node prev, Node prevMin) {
            this.value = value;
            this.prev = prev;
            this.prevMin = prevMin;
        }
    }

    public MinStack() {

    }

    public void push(int val) {
        final Node n = new Node(val, top, min);
        top = n;
        if (min == null || val < min.value) {
            min = n;
        }
    }

    public void pop() {
        final Node n = top;
        top = n.prev;
        min = n.prevMin;
    }

    public int top() {
        if (top == null) return 0;
        return top.value;
    }

    public int getMin() {
        if (min == null) return 0;
        return min.value;
    }

    public static void main(String[] args) {
        int pass = 0, fail = 0;

        // Test 1: LeetCode example - push, push, push, getMin, pop, top, getMin
        {
            MinStack s = new MinStack();
            s.push(-2);
            s.push(0);
            s.push(-3);
            boolean ok = s.getMin() == -3;
            s.pop();
            ok &= s.top() == 0;
            ok &= s.getMin() == -2;
            if (ok) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL"); }
        }

        // Test 2: Single element
        {
            MinStack s = new MinStack();
            s.push(42);
            boolean ok = s.top() == 42 && s.getMin() == 42;
            s.pop();
            if (ok) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL"); }
        }

        // Test 3: Min changes after pop
        {
            MinStack s = new MinStack();
            s.push(3);
            s.push(1);
            s.push(2);
            boolean ok = s.getMin() == 1;
            s.pop(); // remove 2
            ok &= s.getMin() == 1;
            s.pop(); // remove 1
            ok &= s.getMin() == 3;
            if (ok) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL"); }
        }

        // Test 4: Duplicate minimums
        {
            MinStack s = new MinStack();
            s.push(1);
            s.push(1);
            boolean ok = s.getMin() == 1;
            s.pop();
            ok &= s.getMin() == 1;
            if (ok) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL"); }
        }

        // Test 5: Descending order (each push is new min)
        {
            MinStack s = new MinStack();
            s.push(5);
            s.push(4);
            s.push(3);
            s.push(2);
            s.push(1);
            boolean ok = s.getMin() == 1;
            s.pop();
            ok &= s.getMin() == 2;
            s.pop();
            ok &= s.getMin() == 3;
            if (ok) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL"); }
        }

        // Test 6: Ascending order (min never changes)
        {
            MinStack s = new MinStack();
            s.push(1);
            s.push(2);
            s.push(3);
            boolean ok = s.getMin() == 1;
            s.pop();
            ok &= s.getMin() == 1;
            s.pop();
            ok &= s.getMin() == 1;
            if (ok) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL"); }
        }

        // Test 7: Negative numbers
        {
            MinStack s = new MinStack();
            s.push(-1);
            s.push(-2);
            s.push(-3);
            boolean ok = s.getMin() == -3 && s.top() == -3;
            s.pop();
            ok &= s.getMin() == -2;
            if (ok) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL"); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}
