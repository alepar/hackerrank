package codesignal;

import java.util.*;

// https://www.interviewdb.io/question/codesignal?page=1&name=in-memory-database
//
// In-memory Database
//
// == Level 1 ==
// Basic operations to manipulate records, fields, and values within fields.
// Records are keyed by a unique string key, and contain field-value pairs (both strings).
//
// SET <key> <field> <value> — insert a field-value pair to the record associated with key.
//   If the field already exists, replace its value. If the record doesn't exist, create one.
//   Returns "".
//
// GET <key> <field> — return the value of field in the record associated with key.
//   If the record or field doesn't exist, return "".
//
// DELETE <key> <field> — remove the field from the record associated with key.
//   Returns "true" if successfully deleted, "false" if key or field don't exist.
//
// == Level 2 ==
// Displaying data based on filters.
//
// SCAN <key> — return a string of all fields of the record associated with key, formatted as
//   "<field1>(<value1>), <field2>(<value2>), ..." with fields sorted lexicographically.
//   If the record doesn't exist, return "".
//
// SCAN_BY_PREFIX <key> <prefix> — same as SCAN but only includes fields starting with prefix.
//
// == Level 3 ==
// TTL (Time-To-Live) for field-value pairs. Timestamps are guaranteed to strictly increase.
// Each test uses either timestamped or non-timestamped operations, not both.
//
// SET_AT <key> <field> <value> <timestamp> — same as SET but with timestamp.
//
// SET_AT_WITH_TTL <key> <field> <value> <timestamp> <ttl> — same as SET_AT but also sets
//   TTL starting at timestamp. The field-value pair exists during [timestamp, timestamp + ttl).
//
// DELETE_AT <key> <field> <timestamp> — same as DELETE but with timestamp.
//
// GET_AT <key> <field> <timestamp> — same as GET but with timestamp.
//
// SCAN_AT <key> <timestamp> — same as SCAN but with timestamp.
//
// SCAN_BY_PREFIX_AT <key> <prefix> <timestamp> — same as SCAN_BY_PREFIX but with timestamp.
//
// == Level 4 ==
// Backup and restore functionality. When restoring, TTL expiration times are recalculated:
// remaining_ttl = initial_ttl - (backup_timestamp - field_set_timestamp)
// new_expiration = restore_timestamp + remaining_ttl
//
// BACKUP <timestamp> — save the database state at the specified timestamp, including
//   remaining TTL for all records and fields. Returns a string representing the number of
//   non-empty non-expired records in the database.
//
// RESTORE <timestamp> <timestampToRestore> — restore the database from the latest backup
//   before or at timestampToRestore. Expiration times for restored records and fields should
//   be recalculated according to the timestamp of this operation. Returns "".
public class InMemoryDatabase {

    private Map<String, Record> records = new HashMap<>();
    private final NavigableMap<Integer, Map<String, Record>> backups = new TreeMap<>();

    public class Record {
        private final String name;
        private final SortedMap<String, FieldValue> fieldValues = new TreeMap<>();

        public Record(String name) {
            this.name = name;
        }
    }

    public class FieldValue {
        private final String name;
        private final String value;

        private int createdAt;
        private int ttl;

        public FieldValue(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public FieldValue(String name, String value, int createdAt, int ttl) {
            this.name = name;
            this.value = value;

            this.createdAt = createdAt;
            this.ttl = ttl;
        }

        public boolean isExpiredAt(int timestamp) {
            if (ttl <= 0) return false;
            return createdAt+ttl <= timestamp;
        }
    }

    // Level 1
    private String Set(String key, String field, String value) {
        final Record record = this.records.compute(key, (String k, Record r) -> {
            if (r != null) return r;
            return new Record(k);
        });
        // replace if exists, create otherwise
        record.fieldValues.put(field, new FieldValue(field, value));
        return "";
    }

    private String Get(String key, String field) {
        final Record record = this.records.get(key);
        if (record == null) return "";

        final FieldValue fieldValue = record.fieldValues.get(field);
        if (fieldValue == null) return "";

        return fieldValue.value;
    }

    private String Delete(String key, String field) {
        final Record record = this.records.get(key);
        if (record == null) return "false";


        final FieldValue fieldValue = record.fieldValues.remove(field);
        if (fieldValue == null) return "false";

        return "true";
    }

    // Level 2
    private String Scan(String key) {
        final Record record = this.records.get(key);
        if (record == null) return "";

        final StringBuilder sb = new StringBuilder();
        for (var fv: record.fieldValues.values()) {
            sb.append(fv.name).append('(').append(fv.value).append("), ");
        }

        if (sb.length()>=2) {
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    private String ScanByPrefix(String key, String prefix) {
        final Record record = this.records.get(key);
        if (record == null) return "";

        final StringBuilder sb = new StringBuilder();
        for (var fv: record.fieldValues.values()) {
            if (fv.name.startsWith(prefix)) {
                sb.append(fv.name).append('(').append(fv.value).append("), ");
            }
        }

        if (sb.length()>=2) {
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    // Level 3
    private String SetAt(String key, String field, String value, int timestamp) {
        final Record record = this.records.compute(key, (String k, Record r) -> {
            if (r != null) return r;
            return new Record(k);
        });
        // replace if exists, create otherwise
        record.fieldValues.put(field, new FieldValue(field, value, timestamp, -1));
        return "";
    }

    private String SetAtWithTtl(String key, String field, String value, int timestamp, int ttl) {
        final Record record = this.records.compute(key, (String k, Record r) -> {
            if (r != null) return r;
            return new Record(k);
        });
        // replace if exists, create otherwise
        record.fieldValues.put(field, new FieldValue(field, value, timestamp, ttl));
        return "";
    }

    private String DeleteAt(String key, String field, int timestamp) {
        final Record record = this.records.get(key);
        if (record == null) return "false";

        final FieldValue fieldValue = record.fieldValues.remove(field);
        if (fieldValue == null) return "false";

        if (fieldValue.isExpiredAt(timestamp)) return "false";

        return "true";
    }

    private String GetAt(String key, String field, int timestamp) {
        final Record record = this.records.get(key);
        if (record == null) return "";

        final FieldValue fieldValue = record.fieldValues.get(field);
        if (fieldValue == null) return "";

        if (fieldValue.isExpiredAt(timestamp)) return "";

        return fieldValue.value;
    }

    private String ScanAt(String key, int timestamp) {
        final Record record = this.records.get(key);
        if (record == null) return "";

        final StringBuilder sb = new StringBuilder();
        for (var fv: record.fieldValues.values()) {
            if (!fv.isExpiredAt(timestamp)) {
                sb.append(fv.name).append('(').append(fv.value).append("), ");
            }
        }

        if (sb.length()>=2) {
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    private String ScanByPrefixAt(String key, String prefix, int timestamp) {
        final Record record = this.records.get(key);
        if (record == null) return "";

        final StringBuilder sb = new StringBuilder();
        for (var fv: record.fieldValues.values()) {
            if (fv.name.startsWith(prefix) && !fv.isExpiredAt(timestamp)) {
                sb.append(fv.name).append('(').append(fv.value).append("), ");
            }
        }

        if (sb.length()>=2) {
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    // Level 4
    private String Backup(int timestamp) {
        final Map<String, Record> backup = new HashMap<>();
        int count = 0;

        for (var record: this.records.values()) {
            final Record newRecord = new Record(record.name);
            backup.put(record.name, newRecord);

            for (var fv: record.fieldValues.values()) {
                if (fv.isExpiredAt(timestamp)) continue;

                final String key = record.name;
                final String field = fv.name;
                final String value = fv.value;
                final int createdAt = fv.createdAt;
                final int ttl = fv.ttl;

                final int newTtl;
                if (ttl <= 0) newTtl=0;
                else newTtl=(ttl-timestamp+createdAt);

                newRecord.fieldValues.put(field, new FieldValue(field, value, 0, newTtl));
            }

            if (!newRecord.fieldValues.isEmpty()) count++;
        }

        this.backups.put(timestamp, backup);

        return String.valueOf(count);
    }

    private String Restore(int timestamp, int timestampToRestore) {
        final Map.Entry<Integer, Map<String, Record>> backupEntry = this.backups.floorEntry(timestampToRestore);
        this.records = backupEntry.getValue();

        for (var record: records.values()) {
            for (var fv: record.fieldValues.values()) {
                fv.createdAt = timestamp;
            }
        }

        return "";
    }

    public static void main(String[] args) {
        // level 1
        {
            final InMemoryDatabase db = new InMemoryDatabase();
            assertEquals(1, "", (int t) -> db.Set("A", "B", "E"));       // {"A": {"B": "E"}}
            assertEquals(2, "", (int t) -> db.Set("A", "C", "F"));       // {"A": {"B": "E", "C": "F"}}
            assertEquals(3, "E", (int t) -> db.Get("A", "B"));
            assertEquals(4, "", (int t) -> db.Get("A", "D"));            // field doesn't exist
            assertEquals(5, "true", (int t) -> db.Delete("A", "B"));     // {"A": {"C": "F"}}
            assertEquals(6, "false", (int t) -> db.Delete("A", "D"));    // field doesn't exist
        }

        // level 2
        {
            final InMemoryDatabase db = new InMemoryDatabase();
            assertEquals(1, "", (int t) -> db.Set("A", "BC", "E"));
            assertEquals(2, "", (int t) -> db.Set("A", "BD", "F"));
            assertEquals(3, "", (int t) -> db.Set("A", "C", "G"));
            assertEquals(4, "BC(E), BD(F)", (int t) -> db.ScanByPrefix("A", "B"));
            assertEquals(5, "BC(E), BD(F), C(G)", (int t) -> db.Scan("A"));
            assertEquals(6, "", (int t) -> db.ScanByPrefix("B", "B"));   // record "B" doesn't exist
        }

        // level 3 - example 1: TTL expiration
        {
            final InMemoryDatabase db = new InMemoryDatabase();
            assertEquals(1, "", (int t) -> db.SetAtWithTtl("A", "BC", "E", 1, 9));   // BC expires at 10
            assertEquals(2, "", (int t) -> db.SetAtWithTtl("A", "BC", "E", 5, 10));  // BC overwritten, now expires at 15
            assertEquals(3, "", (int t) -> db.SetAt("A", "BD", "F", 5));              // BD has no TTL
            assertEquals(4, "BC(E), BD(F)", (int t) -> db.ScanByPrefixAt("A", "B", 14));  // BC still alive at 14
            assertEquals(5, "BD(F)", (int t) -> db.ScanByPrefixAt("A", "B", 15));          // BC expired at 15
        }

        // level 3 - example 2: mixed TTL and no-TTL
        {
            final InMemoryDatabase db = new InMemoryDatabase();
            assertEquals(1, "", (int t) -> db.SetAt("A", "B", "C", 1));
            assertEquals(2, "", (int t) -> db.SetAtWithTtl("X", "Y", "Z", 2, 15));   // Y expires at 17
            assertEquals(3, "Z", (int t) -> db.GetAt("X", "Y", 3));
            assertEquals(4, "", (int t) -> db.SetAtWithTtl("A", "D", "E", 4, 10));   // D expires at 14
            assertEquals(5, "B(C), D(E)", (int t) -> db.ScanAt("A", 13));             // both alive
            assertEquals(6, "Y(Z)", (int t) -> db.ScanAt("X", 16));                   // Y alive at 16
            assertEquals(7, "", (int t) -> db.ScanAt("X", 17));                        // Y expired at 17
            assertEquals(8, "false", (int t) -> db.DeleteAt("X", "Y", 20));            // already expired
        }

        // level 4 - backup and restore with TTL recalculation
        {
            final InMemoryDatabase db = new InMemoryDatabase();
            assertEquals(1, "", (int t) -> db.SetAtWithTtl("A", "B", "C", 1, 10));    // B expires at 11
            assertEquals(2, "1", (int t) -> db.Backup(3));                              // save state; B has remaining_ttl=8
            assertEquals(3, "", (int t) -> db.SetAt("A", "D", "E", 4));                // add field D (no TTL)
            assertEquals(4, "1", (int t) -> db.Backup(5));                              // save state; B has remaining_ttl=6
            assertEquals(5, "true", (int t) -> db.DeleteAt("A", "B", 8));
            assertEquals(6, "1", (int t) -> db.Backup(9));                              // save state; only D remains
            // restore to latest backup at or before t=7, which is backup at t=5
            // B had remaining_ttl=6 at backup t=5, so new expiration = 10+6 = 16
            assertEquals(7, "", (int t) -> db.Restore(10, 7));
            assertEquals(8, "1", (int t) -> db.Backup(11));
            assertEquals(9, "B(C), D(E)", (int t) -> db.ScanAt("A", 15));              // B alive (expires 16)
            assertEquals(10, "D(E)", (int t) -> db.ScanAt("A", 16));                   // B expired at 16
        }

        System.out.println("all tests passed");
    }


    public interface Operation<T> {
        public T exec(int t);
    }

    public static <T> void assertEquals(int t, T exp, Operation<T> f) {
        T act = f.exec(t);
        if (exp == null && act != null || exp != null && act == null) {
            throw new RuntimeException(t + ": expected " + exp + " but got " + act);
        }
        if (exp != act && !act.equals(exp)) {
            throw new RuntimeException(t + ": expected " + exp + " but got " + act);
        }
    }

}
