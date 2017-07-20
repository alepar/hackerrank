package interviewbit;

public class POWER2 {

    /**
     *
     * Given a positive integer which fits in a 32 bit signed integer,
     * find if it can be expressed as A^P where P > 1 and A > 0. A and P both should be integers.
     */

    public boolean isPower(int a) {
        if (a == 1) return true;
        Integer gcd = null;

        final int end = a/2;
        for (int d = 2; d <= end; d++) {
            if (a == 1) break;

            int counter;
            for (counter = 0; a % d == 0; a /= d, counter++) ;

            if (counter > 0) {
                if (gcd == null) {
                    gcd = counter;
                } else {
                    gcd = gcd(gcd, counter);
                }

                if (gcd == 1) return false;
            }
        }

        return gcd != null && gcd > 1;
    }

    public int gcd(int a, int b) {
        int x = Math.max(a, b);
        int y = Math.min(a, b);

        while (y != 0) {
            a = y;
            b = x % y;

            x = a;
            y = b;
        }

        return x;
    }

    public static void main(String[] args) {
        System.out.println(new POWER2().isPower(43659));
    }
}
