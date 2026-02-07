package codesignal;

import java.util.*;

// https://www.interviewdb.io/question/codesignal?page=1&name=banking-system
public class Banking {

    private static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private final SortedMap<String, Account> accounts = new TreeMap<>();
    private final SortedSet<Account> topAccounts = new TreeSet<>((Account l, Account r) -> {
        if (l.outgoing != r.outgoing) {
            return -Integer.compare(l.outgoing, r.outgoing);
        }

        return l.accountId.compareTo(r.accountId);
    });

    private final Deque<FutureDeposit> futureDeposits = new ArrayDeque<>();
    private final Map<String, List<BalanceSnapshot>> balanceSnapshots = new TreeMap<>();

    private int ordinal;

    public class BalanceSnapshot {
        private final int t;
        private final Integer amount;

        public BalanceSnapshot(int t, Integer amount) {
            this.t = t;
            this.amount = amount;
        }
    }

    public class FutureDeposit {
        private final String paymentId;
        private String accountId;
        private final int amount;
        public final int timestamp;

        public FutureDeposit(String paymentId, String accountId, int cashbackAmount, int timestamp) {
            this.paymentId = paymentId;
            this.accountId = accountId;
            this.amount = cashbackAmount;
            this.timestamp = timestamp;
        }
    }
    public class Account {
        private final String accountId;
        private Integer balance = 0;
        private int outgoing;

        private final Map<String, String> paymentStatuses = new TreeMap<>();

        public Account(String accountId) {
            this.accountId = accountId;
        }
    }


    public static void main(String[] args) {
        // level 1
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "account1"));
            assertFalse(2, (int t) -> b.CreateAccount(t, "account1"));
            assertTrue(3, (int t) -> b.CreateAccount(t, "account2"));

            assertEquals(4, null, (int t) -> b.Deposit(t, "non-existing", 2700));
            assertEquals(5, 2700, (int t) -> b.Deposit(t, "account1", 2700));

            assertEquals(6, null, (int t) -> b.Transfer(t, "account1", "account2", 2701));
            assertEquals(7, 2500, (int t) -> b.Transfer(t, "account1", "account2", 200));
        }

        // level 2
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "account3"));
            assertTrue(2, (int t) -> b.CreateAccount(t, "account2"));
            assertTrue(3, (int t) -> b.CreateAccount(t, "account1"));

            assertEquals(4, 2000, (int t) -> b.Deposit(t, "account1", 2000));
            assertEquals(5, 3000, (int t) -> b.Deposit(t, "account2", 3000));
            assertEquals(6, 4000, (int t) -> b.Deposit(t, "account3", 4000));

            {
                final List<Account> tops = b.TopSpenders(7, 3);
                assertEquals(70, "account1", (int t) -> tops.get(0).accountId);
                assertEquals(71, "account2", (int t) -> tops.get(1).accountId);
                assertEquals(72, "account3", (int t) -> tops.get(2).accountId);
            }

            assertEquals(8, 3500, (int t) -> b.Transfer(t, "account3", "account2", 500));
            assertEquals(9, 2500, (int t) -> b.Transfer(t, "account3", "account1", 1000));
            assertEquals(10, 500, (int t) -> b.Transfer(t, "account1", "account2", 2500));

            {
                final List<Account> tops = b.TopSpenders(11, 3);
                assertEquals(1100, "account1", (int t) -> tops.get(0).accountId);
                assertEquals(1101, 2500, (int t) -> tops.get(0).outgoing);
                assertEquals(1110, "account3", (int t) -> tops.get(1).accountId);
                assertEquals(1111, 1500, (int t) -> tops.get(1).outgoing);
                assertEquals(1120, "account2", (int t) -> tops.get(2).accountId);
                assertEquals(1121, 0, (int t) -> tops.get(2).outgoing);
            }
        }

        // level 3
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "account1"));
            assertTrue(2, (int t) -> b.CreateAccount(t, "account2"));

            assertEquals(3, 2000, (int t) -> b.Deposit(t, "account1", 2000));

            assertEquals(4, "payment1", (int t) -> b.Pay(t, "account1", 1000));
            assertEquals(100, "payment2", (int t) -> b.Pay(t, "account1", 1000));

            assertEquals(101, null, (int t) -> b.GetPaymentStatus(t, "non-existing", "payment1"));
            assertEquals(102, null, (int t) -> b.GetPaymentStatus(t, "account2", "payment1"));
            assertEquals(103, "IN_PROGRESS", (int t) -> b.GetPaymentStatus(t, "account1", "payment1"));

            {
                final List<Account> tops = b.TopSpenders(104, 2);
                assertEquals(10400, "account1", (int t) -> tops.get(0).accountId);
                assertEquals(10401, 2000, (int t) -> tops.get(0).outgoing);
                assertEquals(10410, "account2", (int t) -> tops.get(1).accountId);
                assertEquals(10411, 0, (int t) -> tops.get(1).outgoing);
            }

            assertEquals(3 + DAY_IN_MILLIS, 100, (int t) -> b.Deposit(t, "account1", 100));
            assertEquals(4 + DAY_IN_MILLIS, "CASHBACK_RECEIVED", (int t) -> b.GetPaymentStatus(t, "account1", "payment1"));

            assertEquals(5 + DAY_IN_MILLIS, 220, (int t) -> b.Deposit(t, "account1", 100));
            assertEquals(99 + DAY_IN_MILLIS, 320, (int t) -> b.Deposit(t, "account1", 100));
            assertEquals(100 + DAY_IN_MILLIS, 440, (int t) -> b.Deposit(t, "account1", 100));
        }

        // level 4.1
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "account1"));
            assertTrue(2, (int t) -> b.CreateAccount(t, "account2"));

            assertEquals(3, 2000, (int t) -> b.Deposit(t, "account1", 2000));
            assertEquals(4, 2000, (int t) -> b.Deposit(t, "account2", 2000));

            assertEquals(5, "payment1", (int t) -> b.Pay(t, "account2", 300));
            assertEquals(6, 1500, (int t) -> b.Transfer(t, "account1", "account2", 500));

            assertEquals(7, false, (int t) -> b.Merge(t, "account1", "non-exiting"));
            assertEquals(8, false, (int t) -> b.Merge(t, "account1", "account1"));
            assertEquals(9, true, (int t) -> b.Merge(t, "account1", "account2"));

            assertEquals(10, 3800, (int t) -> b.Deposit(t, "account1", 100));
            assertEquals(11, null, (int t) -> b.Deposit(t, "account2", 100));

            assertEquals(12, null, (int t) -> b.GetPaymentStatus(t, "account2", "payment1"));
            assertEquals(13, "IN_PROGRESS", (int t) -> b.GetPaymentStatus(t, "account1", "payment1"));

            assertEquals(14, null, (int t) -> b.GetBalance(t, "account2", 1));
            assertEquals(15, null, (int t) -> b.GetBalance(t, "account2", 9));
            assertEquals(16, 3800, (int t) -> b.GetBalance(t, "account1", 11));

            assertEquals(5+DAY_IN_MILLIS, 3906, (int t) -> b.Deposit(t, "account1", 100));
        }
        // Bug test: TreeSet corruption — outgoing mutated before remove()
        // In Transfer/Pay/Merge, outgoing is changed BEFORE topAccounts.remove(),
        // so the red-black tree search goes the wrong way and fails to remove
        // the element, leaving a duplicate. TopSpenders then returns it twice.
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "aa"));
            assertTrue(2, (int t) -> b.CreateAccount(t, "bb"));
            assertTrue(3, (int t) -> b.CreateAccount(t, "cc"));

            assertEquals(4, 500, (int t) -> b.Deposit(t, "cc", 500));
            // cc sits in the right subtree; transferring bumps its outgoing,
            // but remove() searches left (higher outgoing → earlier) and misses it.
            assertEquals(5, 300, (int t) -> b.Transfer(t, "cc", "aa", 200));

            // Add a 4th account after corruption
            assertTrue(6, (int t) -> b.CreateAccount(t, "dd"));

            {
                // Expected order: cc(200), aa(0), bb(0), dd(0)
                // Actual (with bug): cc(200), aa(0), bb(0), cc(200) — duplicate cc pushes dd out
                final List<Account> tops = b.TopSpenders(7, 4);
                assertEquals(70, "cc", (int t) -> tops.get(0).accountId);
                assertEquals(71, 200, (int t) -> tops.get(0).outgoing);
                assertEquals(72, "aa", (int t) -> tops.get(1).accountId);
                assertEquals(73, 0, (int t) -> tops.get(1).outgoing);
                assertEquals(74, "bb", (int t) -> tops.get(2).accountId);
                assertEquals(75, 0, (int t) -> tops.get(2).outgoing);
                assertEquals(76, "dd", (int t) -> tops.get(3).accountId);
                assertEquals(77, 0, (int t) -> tops.get(3).outgoing);
            }
        }

        // Bug test: Payment ordinal is per-account, but spec says global
        // "payment[ordinal number of withdraws from all accounts]"
        // Code uses ++account.ordinal (per-account), so two different accounts
        // both produce "payment1" instead of "payment1" then "payment2".
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "account1"));
            assertTrue(2, (int t) -> b.CreateAccount(t, "account2"));

            assertEquals(3, 1000, (int t) -> b.Deposit(t, "account1", 1000));
            assertEquals(4, 1000, (int t) -> b.Deposit(t, "account2", 1000));

            assertEquals(5, "payment1", (int t) -> b.Pay(t, "account1", 100));
            // Should be "payment2" (global ordinal), but code returns "payment1"
            assertEquals(6, "payment2", (int t) -> b.Pay(t, "account2", 100));
        }

        // level 4.2
        {
            final Banking b = new Banking();
            assertTrue(1, (int t) -> b.CreateAccount(t, "account1"));

            assertEquals(2, 1000, (int t) -> b.Deposit(t, "account1", 1000));
            assertEquals(3, "payment1", (int t) -> b.Pay(t, "account1", 300));

            assertEquals(4, 700, (int t) -> b.GetBalance(t, "account1", 3));
            assertEquals(5+DAY_IN_MILLIS, 700, (int t) -> b.GetBalance(t, "account1", 2+DAY_IN_MILLIS));
            assertEquals(6+DAY_IN_MILLIS, 706, (int t) -> b.GetBalance(t, "account1", 3+DAY_IN_MILLIS));
        }
    }

    private Integer GetBalance(int t, String accountId, int tAt) {
        this.processFutureDeposits(t);

        final List<BalanceSnapshot> snapshots = this.balanceSnapshots.get(accountId);
        if (snapshots == null) {
            return null;
        }

        BalanceSnapshot lastSnap = null;
        for (BalanceSnapshot snap: snapshots) {
            if (snap.t > tAt) break;
            lastSnap = snap;
        }

        if (lastSnap == null) {
            return null;
        }

        return lastSnap.amount;
    }

    private boolean Merge(int t, String intoId, String fromId) {
        this.processFutureDeposits(t);

        final Account into = this.accounts.get(intoId);
        final Account from = this.accounts.get(fromId);
        if (into == null || from == null || Objects.equals(intoId, fromId)) {
            return false;
        }

        this.topAccounts.remove(from);
        this.topAccounts.remove(into);

        into.balance += from.balance;
        into.outgoing += from.outgoing;
        into.paymentStatuses.putAll(from.paymentStatuses);

        from.balance = null;
        this.accounts.remove(fromId);
        this.topAccounts.add(into);

        for (FutureDeposit futureDeposit: this.futureDeposits) {
            if (futureDeposit.accountId.equals(fromId)) {
                futureDeposit.accountId = intoId;
            }
        }

        addSnapshot(t, from);
        addSnapshot(t, into);

        return true;
    }

    private void addSnapshot(int t, Account account) {
        final List<BalanceSnapshot> snapshots = this.balanceSnapshots.computeIfAbsent(account.accountId, k -> new ArrayList<>());
        snapshots.add(new BalanceSnapshot(t, account.balance));

    }

    private String GetPaymentStatus(int t, String accountId, String paymentId) {
        this.processFutureDeposits(t);

        final Account account = this.accounts.get(accountId);
        if (account == null) {
            return null;
        }

        return account.paymentStatuses.get(paymentId);
    }

    private String Pay(int t, String accountId, int amount) {
        this.processFutureDeposits(t);

        final Account account = this.accounts.get(accountId);
        if (account == null || account.balance < amount) {
            return null;
        }

        this.topAccounts.remove(account);
        account.balance -= amount;
        account.outgoing += amount;
        this.topAccounts.add(account);

        final String paymentId = String.format("payment%d", ++this.ordinal);
        final int cashbackAmount = amount / 50;

        this.futureDeposits.add(new FutureDeposit(paymentId, account.accountId, cashbackAmount, t+ DAY_IN_MILLIS));
        account.paymentStatuses.put(paymentId, "IN_PROGRESS");

        addSnapshot(t, account);

        return paymentId;
    }

    private List<Account> TopSpenders(int t, int k) {
        this.processFutureDeposits(t);

        final List<Account> result = new ArrayList<>(k);
        for (Account a: this.topAccounts) {
            result.add(a);
            if (--k==0) break;
        }
        return result;
    }

    private Integer Transfer(int t, String srcId, String dstId, int amount) {
        this.processFutureDeposits(t);

        final Account src = this.accounts.get(srcId);
        if (src == null) return null;

        final Account dst = this.accounts.get(dstId);
        if (dst == null) return null;

        if (src.balance < amount) return null;

        src.balance -= amount;
        this.topAccounts.remove(src);
        src.outgoing += amount;
        this.topAccounts.add(src);

        dst.balance += amount;

        addSnapshot(t, src);
        addSnapshot(t, dst);

        return src.balance;
    }

    private Integer Deposit(int t, String id, int amount) {
        this.processFutureDeposits(t);

        final Account account = this.accounts.get(id);
        if (account == null) {
            return null;
        }
        account.balance += amount;

        addSnapshot(t, account);

        return account.balance;
    }

    private boolean CreateAccount(int t, String id) {
        this.processFutureDeposits(t);

        if (this.accounts.containsKey(id))
            return false;

        final Account acc = new Account(id);
        this.accounts.put(id, acc);
        this.topAccounts.add(acc);

        addSnapshot(t, acc);

        return true;
    }

    private void processFutureDeposits(int t) {
        while (!this.futureDeposits.isEmpty()) {
            final FutureDeposit deposit = this.futureDeposits.getFirst();
            if (deposit == null || deposit.timestamp > t) break;

            this.futureDeposits.removeFirst();
            final Account account = this.accounts.get(deposit.accountId);
            account.balance += deposit.amount;
            account.paymentStatuses.put(deposit.paymentId, "CASHBACK_RECEIVED");

            addSnapshot(deposit.timestamp, account);
        }
    }

    public interface Operation<T> {
        public T exec(int t);
    }

    public static void assertTrue(int t, Operation<Boolean> f) {
        if (!f.exec(t)) {
            throw new RuntimeException(String.valueOf(t));
        }
    }

    public static void assertFalse(int t, Operation<Boolean> f) {
        if (f.exec(t)) {
            throw new RuntimeException(String.valueOf(t));
        }
    }

    public static <T> void assertEquals(int t, T exp, Operation<T> f) {
        T act = f.exec(t);
        if (exp == null && act != null || exp != null && act == null) {
            throw new RuntimeException(String.valueOf(t));
        }
        if (exp != act && !act.equals(exp)) {
            throw new RuntimeException(String.valueOf(t));
        }
    }

}
