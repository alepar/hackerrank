package artem;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


// https://www.reddit.com/r/Hack2Hire/comments/1p2ae9e/openai_screening_interview_gpu_credits/
public class GpuCredits {

    private Map<Integer, Integer> subtracts = new HashMap<>();
    private SortedMap<Integer, Integer> grants = new TreeMap<>();

    public int getBalance(int timestamp) throws InsufficientCreditException {
        int accum = 0;
        for (var grantEntry: grants.entrySet()) {
            if (grantEntry.getKey() > timestamp) break;
            accum += grantEntry.getValue();
        }

        final int remaining = accum - subtracts.getOrDefault(timestamp, 0);
        if (remaining<0) throw new InsufficientCreditException();
        return remaining;
    }

    public void createGrant(String id, int amount, int timestamp, int expirationTimestamp) {
        grants.compute(timestamp, (Integer t, Integer cur) -> {
            if (cur == null) return amount;
            return cur+amount;
        });
        grants.compute(expirationTimestamp, (Integer t, Integer cur) -> {
            if (cur == null) return -amount;
            return cur-amount;
        });
    }

    public void subtract(int amount, int timestamp) {
        subtracts.compute(timestamp, (Integer t, Integer cur) -> {
           if (cur == null) {
               cur = 0;
           }
           return cur+amount;
        });
    }

    public static class InsufficientCreditException extends Exception {}

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }

    static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    static void assertThrows(Class<? extends Exception> expected, ThrowingRunnable runnable) {
        try {
            runnable.run();
            throw new AssertionError("Expected " + expected.getSimpleName() + " but nothing was thrown");
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            if (!expected.isInstance(e)) {
                throw new AssertionError("Expected " + expected.getSimpleName() + " but got " + e.getClass().getSimpleName());
            }
        }
    }

    // grant active on [timestamp, expirationTimestamp)
    // subtract applies at exact timestamp only
    // getBalance throws InsufficientCreditException if subtracts exceed available credits

    static void testCase1() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.subtract(1, 30);
        assertThrows(InsufficientCreditException.class, () -> gpc.getBalance(30));
        gpc.createGrant("a", 1, 10, 100);
        assertEquals(1, gpc.getBalance(10));
        assertEquals(1, gpc.getBalance(20));
        assertEquals(0, gpc.getBalance(30));
        System.out.println("Passed test case 1");
    }

    static void testCase2() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.subtract(1, 30);
        assertThrows(InsufficientCreditException.class, () -> gpc.getBalance(30));
        gpc.createGrant("a", 2, 10, 100);
        assertEquals(2, gpc.getBalance(10));
        assertEquals(2, gpc.getBalance(20));
        assertEquals(1, gpc.getBalance(30));
        assertEquals(0, gpc.getBalance(100));
        System.out.println("Passed test case 2");
    }

    static void testCase3() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 3, 10, 60);
        assertEquals(3, gpc.getBalance(10));
        gpc.createGrant("b", 2, 20, 40);
        gpc.subtract(1, 30);
        gpc.subtract(3, 50);
        assertEquals(3, gpc.getBalance(10));
        assertEquals(5, gpc.getBalance(20));
        assertEquals(4, gpc.getBalance(30));
        assertEquals(3, gpc.getBalance(40));
        assertEquals(0, gpc.getBalance(50));
        System.out.println("Passed test case 3");
    }

    // empty system returns 0
    static void testCase4() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        assertEquals(0, gpc.getBalance(0));
        assertEquals(0, gpc.getBalance(100));
        System.out.println("Passed test case 4");
    }

    // single grant boundary: [start, expiration) inclusive/exclusive
    static void testCase5() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 5, 10, 20);
        assertEquals(0, gpc.getBalance(9));   // before start
        assertEquals(5, gpc.getBalance(10));  // at start (inclusive)
        assertEquals(5, gpc.getBalance(15));  // mid
        assertEquals(5, gpc.getBalance(19));  // last active timestamp
        assertEquals(0, gpc.getBalance(20));  // at expiration (exclusive)
        assertEquals(0, gpc.getBalance(21));  // after expiration
        System.out.println("Passed test case 5");
    }

    // minimum duration grant: active at exactly one timestamp
    static void testCase6() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 3, 10, 11);
        assertEquals(0, gpc.getBalance(9));
        assertEquals(3, gpc.getBalance(10));
        assertEquals(0, gpc.getBalance(11));
        System.out.println("Passed test case 6");
    }

    // multiple subtracts at the same timestamp
    static void testCase7() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 10, 0, 100);
        gpc.subtract(3, 50);
        gpc.subtract(4, 50);
        assertEquals(3, gpc.getBalance(50));  // 10 - 3 - 4 = 3
        assertEquals(10, gpc.getBalance(49)); // unaffected
        System.out.println("Passed test case 7");
    }

    // subtract exactly equals available → returns 0, no exception
    static void testCase8() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 5, 10, 50);
        gpc.subtract(5, 30);
        assertEquals(0, gpc.getBalance(30));  // exactly 0, no exception
        assertEquals(5, gpc.getBalance(20));  // other timestamps unaffected
        System.out.println("Passed test case 8");
    }

    // subtract exceeds available by 1 → exception
    static void testCase9() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 5, 10, 50);
        gpc.subtract(6, 30);
        assertThrows(InsufficientCreditException.class, () -> gpc.getBalance(30));
        assertEquals(5, gpc.getBalance(20));  // unaffected
        System.out.println("Passed test case 9");
    }

    // non-overlapping grants with gap
    static void testCase10() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 3, 10, 20);
        gpc.createGrant("b", 5, 30, 40);
        assertEquals(0, gpc.getBalance(5));   // before any grant
        assertEquals(3, gpc.getBalance(10));  // in grant a
        assertEquals(0, gpc.getBalance(25));  // in the gap
        assertEquals(5, gpc.getBalance(30));  // in grant b
        assertEquals(0, gpc.getBalance(40));  // after grant b
        System.out.println("Passed test case 10");
    }

    // out-of-order: subtract first causes exception, then grant retroactively covers it
    static void testCase11() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.subtract(2, 50);
        assertThrows(InsufficientCreditException.class, () -> gpc.getBalance(50));
        gpc.createGrant("a", 5, 10, 100);
        assertEquals(3, gpc.getBalance(50));  // now 5 - 2 = 3
        System.out.println("Passed test case 11");
    }

    // out-of-order: query returns 0, then grant retroactively covers that time
    static void testCase12() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        assertEquals(0, gpc.getBalance(50));
        gpc.createGrant("a", 7, 10, 100);
        assertEquals(7, gpc.getBalance(50));  // now covered
        System.out.println("Passed test case 12");
    }

    // many overlapping grants — nested intervals
    static void testCase13() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 1, 0, 100);
        gpc.createGrant("b", 2, 10, 80);
        gpc.createGrant("c", 3, 20, 60);
        gpc.createGrant("d", 4, 30, 40);
        assertEquals(1, gpc.getBalance(5));   // a
        assertEquals(3, gpc.getBalance(15));  // a+b
        assertEquals(6, gpc.getBalance(25));  // a+b+c
        assertEquals(10, gpc.getBalance(35)); // a+b+c+d
        assertEquals(6, gpc.getBalance(45));  // a+b+c (d expired)
        assertEquals(3, gpc.getBalance(65));  // a+b (c expired)
        assertEquals(1, gpc.getBalance(85));  // a only (b expired)
        assertEquals(0, gpc.getBalance(100)); // all expired
        System.out.println("Passed test case 13");
    }

    // multiple grants + multiple subtracts, one causing exception
    static void testCase14() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 3, 10, 50);
        gpc.createGrant("b", 2, 20, 40);
        gpc.subtract(4, 25);  // a(3)+b(2)=5, -4 → 1
        gpc.subtract(2, 35);  // a(3)+b(2)=5, -2 → 3
        gpc.subtract(5, 45);  // a(3) only,   -5 → negative → exception
        assertEquals(3, gpc.getBalance(15));  // only a, no subtract
        assertEquals(1, gpc.getBalance(25));
        assertEquals(3, gpc.getBalance(35));
        assertThrows(InsufficientCreditException.class, () -> gpc.getBalance(45));
        System.out.println("Passed test case 14");
    }

    // many grants at same time range, subtract at different point
    static void testCase15() throws InsufficientCreditException {
        GpuCredits gpc = new GpuCredits();
        gpc.createGrant("a", 1, 10, 20);
        gpc.createGrant("b", 1, 10, 20);
        gpc.createGrant("c", 1, 10, 20);
        gpc.createGrant("d", 1, 10, 20);
        gpc.createGrant("e", 1, 10, 20);
        assertEquals(5, gpc.getBalance(10));
        gpc.subtract(3, 15);
        assertEquals(2, gpc.getBalance(15));
        assertEquals(5, gpc.getBalance(10)); // subtract at 15 doesn't affect 10
        System.out.println("Passed test case 15");
    }

    public static void main(String[] args) throws InsufficientCreditException {
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        testCase5();
        testCase6();
        testCase7();
        testCase8();
        testCase9();
        testCase10();
        testCase11();
        testCase12();
        testCase13();
        testCase14();
        testCase15();
    }
}
