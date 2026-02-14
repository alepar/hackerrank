package codesignal;

import java.util.*;

// https://www.interviewdb.io/question/codesignal?page=1&name=cloud-storage-system---all-levels
//
// Cloud Storage System
//
// == Level 1 ==
// The cloud storage system should support file manipulation.
//
// ADD_FILE <name> <size> — should add a new file `name` to the storage. `size` is the amount
//   of memory required in bytes. The current operation fails if a file with the same `name`
//   already exists. Returns "true" if the file was added successfully or "false" otherwise.
//
// GET_FILE_SIZE <name> — should return a string representing the size of the file `name` if
//   it exists, or an empty string otherwise.
//
// DELETE_FILE <name> — should delete the file `name`. Returns a string representing the
//   deleted file size if the deletion was successful or an empty string if the file does not exist.
//
// == Level 2 ==
// Implement an operation for retrieving some statistics about files with a specific prefix.
//
// GET_N_LARGEST <prefix> <n> — should return the string representing the names of the top `n`
//   largest files with names starting with `prefix` in the following format:
//   "<name1>(<size1>), ..., <nameN>(<sizeN>)".
//   Returned files should be sorted by size in descending order, or in case of a tie, sorted in
//   lexicographical order of the names. If there are no such files, return an empty string. If
//   the number of such files is less than `n`, all of them should be returned in the specified format.
//
// == Level 3 ==
// Implement support for queries from different users. All users share a common filesystem in
// the cloud storage system, but each user is assigned a storage capacity limit.
//
// ADD_USER <userId> <capacity> — should add a new user in the system, with `capacity` as their
//   storage limit in bytes. The total size of all files owned by `userId` cannot exceed `capacity`.
//   The operation fails if a user with `userId` already exists. Returns "true" if a user with
//   `userId` is successfully created, or "false" otherwise.
//
// ADD_FILE_BY <userId> <name> <size> — should behave in the same way as the ADD_FILE from
//   Level 1, but the added file should be owned by the user with `userId`. A new file cannot be
//   added to the storage if doing so will exceed the user's `capacity` limit. Returns a string
//   representing the remaining capacity of the user if the file is added successfully, or an
//   empty string otherwise.
//   Note that all queries calling the ADD_FILE operation implemented during Level 1 are run by
//   the user with userId = "admin", who has unlimited storage capacity.
//
// MERGE_USER <userId1> <userId2> — should merge the account of `userId2` with the `userId1`.
//   Ownership of all of `userId2`'s files is transferred to `userId1`, and any remaining storage
//   capacity is also added to `userId1`'s limit. `userId2` is deleted if the merge is successful.
//   Returns a string representing the remaining capacity of `userId1` after merging, or an empty
//   string if one of the users does not exist or `userId1` is equal to `userId2`. It is guaranteed
//   that neither `userId1` nor `userId2` equals "admin".
//
// == Level 4 ==
// Implement support to allow users to back up their files.
//
// BACKUP_USER <userId> — should back up the current state of all files owned by `userId` — i.e.,
//   file names and sizes. The backup is stored on a separate storage system and is not affected by
//   any new file manipulation queries. Overwrites any backups for the same user if previous backups
//   exist. Returns a string representing the number of backed-up files, or an empty string if
//   `userId` does not exist.
//
// RESTORE_USER <userId> — should restore the state of `userId`'s files to the latest backup. If
//   there was no backup, all of `userId`'s files are deleted. If a file can't be restored because
//   another user added another file with the same name, it is ignored. Returns a string representing
//   the number of files that are successfully restored or an empty string if `userId` does not exist.
//   Note that MERGE_USER does not affect userId1's backup, and userId2 is deleted along with its backup.
//   Note that the RESTORE_USER operation does not affect the user's capacity.
public class CloudStorageSystem {

    private final User admin;

    private final Map<String, CloudFile> files = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();

    public CloudStorageSystem() {
        this.admin = new User("admin", -1);
        users.put(this.admin.userName, this.admin);
    }


    public class CloudFile {
        private final String fileName;
        private final int size;

        private User owner;

        public CloudFile(String fileName, int size, User owner) {
            this.fileName = fileName;
            this.size = size;
            this.owner = owner;
        }
    }

    public class User {
        private final String userName;
        private int limit;
        private int used;
        private Map<String, Integer> backup;

        private final Map<String, CloudFile> files = new HashMap<>();

        public User(String userName, int limit) {
            this.userName = userName;
            this.limit = limit;
        }
    }

    // Level 1
    private String AddFile(String name, int size) {
        final String remaining = AddFileBy(this.admin.userName, name, size);
        if ("".equals(remaining)) return "false";
        return "true";
    }

    private String GetFileSize(String name) {
        final CloudFile file = this.files.get(name);
        if (file == null) return "";
        return String.valueOf(file.size);
    }

    private String DeleteFile(String name) {
        final CloudFile file = this.files.get(name);
        if (file == null) return "";

        this.files.remove(file.fileName);
        final User owner = file.owner;
        owner.files.remove(file.fileName);
        owner.used -= file.size;

        return String.valueOf(file.size);
    }

    // Level 2
    private String GetNLargest(String prefix, int n) {
        final SortedSet<CloudFile> top = new TreeSet<>((CloudFile l, CloudFile r) -> {
            if (l.size != r.size) {
                return -Integer.compare(l.size, r.size);
            }
            return l.fileName.compareTo(r.fileName);
        });

        for (var file : this.files.values()) {
            if (file.fileName.startsWith(prefix)) {
                top.add(file);
            }
        }

        final StringBuilder sb = new StringBuilder();
        int i=0;
        for (var file: top) {
            if (i++>=n) break;
            sb.append(file.fileName).append('(').append(String.valueOf(file.size)).append(')').append(", ");
        }
        if (sb.length()>2) {
            sb.setLength(sb.length()-2);
        }
        return sb.toString();
    }

    // Level 3
    private String AddUser(String userId, int capacity) {
        User user = this.users.get(userId);
        if (user != null) return "false";

        user = new User(userId, capacity);
        this.users.put(userId, user);
        return "true";
    }

    private String AddFileBy(String userId, String name, int size) {
        if (this.files.containsKey(name)) return "";

        final User owner = this.users.get(userId);
        if (owner == null) return "";

        if (!this.admin.userName.equals(userId)) {
            if (owner.limit - owner.used < size) return "";
        }

        final CloudFile file = new CloudFile(name, size, owner);
        this.files.put(name, file);
        owner.files.put(name, file);
        owner.used += file.size;
        return String.valueOf(owner.limit-owner.used);
    }

    private String MergeUser(String intoId, String fromId) {
        if (intoId.equals(fromId)) return "";

        final User from = this.users.get(fromId);
        final User into = this.users.get(intoId);
        if (from == null || into == null) return "";

        // ownership of files
        for (var file: from.files.values()) {
            file.owner = into;
        }
        into.files.putAll(from.files);
        // limit updated
        into.limit += from.limit;
        into.used += from.used;

        this.users.remove(from.userName);

        return String.valueOf(into.limit - into.used);
    }

    // Level 4
    private String BackupUser(String userId) {
        final User user = this.users.get(userId);
        if (user == null) return "";

        final Map<String, Integer> backup = new TreeMap<>();
        for (var file: user.files.values()) {
            backup.put(file.fileName, file.size);
        }
        user.backup = backup;

        return String.valueOf(backup.size());
    }

    private String RestoreUser(String userId) {
        final User user = this.users.get(userId);
        if (user == null) return "";

        final List<String> toDelete = new ArrayList<>();
        for (var file : user.files.values()) {
            toDelete.add(file.fileName);
        }
        for (var fileName: toDelete) {
            this.DeleteFile(fileName);
        }

        int restored = 0;
        if (user.backup != null) {
            for (var backupEntry: user.backup.entrySet()) {
                final String res = this.AddFileBy(user.userName, backupEntry.getKey(), backupEntry.getValue());
                if (!res.isEmpty()) restored++;
            }
        }

        return String.valueOf(restored);
    }


    public static void main(String[] args) {
        // level 1
        {
            final CloudStorageSystem cs = new CloudStorageSystem();
            assertEquals(1, "true", (int t) -> cs.AddFile("/dir1/dir2/file.txt", 10));
            assertEquals(2, "false", (int t) -> cs.AddFile("/dir1/dir2/file.txt", 5));
            assertEquals(3, "10", (int t) -> cs.GetFileSize("/dir1/dir2/file.txt"));
            assertEquals(4, "", (int t) -> cs.DeleteFile("/non-existing.file"));
            assertEquals(5, "10", (int t) -> cs.DeleteFile("/dir1/dir2/file.txt"));
            assertEquals(6, "", (int t) -> cs.GetFileSize("/not-existing.file"));
        }

        // level 2
        {
            final CloudStorageSystem cs = new CloudStorageSystem();
            assertEquals(1, "true", (int t) -> cs.AddFile("/dir/file1.txt", 5));
            assertEquals(2, "true", (int t) -> cs.AddFile("/dir/file2", 20));
            assertEquals(3, "true", (int t) -> cs.AddFile("/dir/deeper/file3.mov", 9));
            assertEquals(4, "/dir/file2(20), /dir/deeper/file3.mov(9)", (int t) -> cs.GetNLargest("/dir", 2));
            assertEquals(5, "/dir/file2(20), /dir/file1.txt(5)", (int t) -> cs.GetNLargest("/dir/file", 3));
            assertEquals(6, "", (int t) -> cs.GetNLargest("/another_dir", 1)); // original example has non-numeric n; result is "" regardless
            assertEquals(7, "true", (int t) -> cs.AddFile("/big_file.mp4", 20));
            assertEquals(8, "/big_file.mp4(20), /dir/file2(20)", (int t) -> cs.GetNLargest("/", 2));
        }

        // level 3
        {
            final CloudStorageSystem cs = new CloudStorageSystem();
            assertEquals(1, "true", (int t) -> cs.AddUser("user1", 200));
            assertEquals(2, "false", (int t) -> cs.AddUser("user1", 100));
            assertEquals(3, "150", (int t) -> cs.AddFileBy("user1", "/dir/file.med", 50));
            assertEquals(4, "10", (int t) -> cs.AddFileBy("user1", "/file.big", 140));
            assertEquals(5, "", (int t) -> cs.AddFileBy("user1", "/dir/file.small", 20)); // exceeds capacity
            assertEquals(6, "true", (int t) -> cs.AddFile("/dir/admin_file", 300)); // admin has unlimited capacity
            assertEquals(7, "true", (int t) -> cs.AddUser("user2", 110));
            assertEquals(8, "", (int t) -> cs.AddFileBy("user2", "/dir/file.med", 45)); // file already exists (owned by user1)
            assertEquals(9, "60", (int t) -> cs.AddFileBy("user2", "/new_file", 50));
            assertEquals(10, "70", (int t) -> cs.MergeUser("user1", "user2")); // transfers /new_file ownership to user1
        }

        // level 4
        {
            final CloudStorageSystem cs = new CloudStorageSystem();
            assertEquals(1, "true", (int t) -> cs.AddUser("user", 100));
            assertEquals(2, "50", (int t) -> cs.AddFileBy("user", "/dir/file1", 50));
            assertEquals(3, "20", (int t) -> cs.AddFileBy("user", "/file2.txt", 30));
            assertEquals(4, "0", (int t) -> cs.RestoreUser("user")); // no backup, deletes all user's files
            assertEquals(5, "40", (int t) -> cs.AddFileBy("user", "/file3.mp4", 60));
            assertEquals(6, "30", (int t) -> cs.AddFileBy("user", "/file4.txt", 10));
            assertEquals(7, "2", (int t) -> cs.BackupUser("user")); // backs up /file3.mp4 and /file4.txt
            assertEquals(8, "60", (int t) -> cs.DeleteFile("/file3.mp4"));
            assertEquals(9, "10", (int t) -> cs.DeleteFile("/file4.txt"));
            assertEquals(10, "true", (int t) -> cs.AddFile("/file3.mp4", 140)); // admin adds file with same name
            assertEquals(11, "80", (int t) -> cs.AddFileBy("user", "/dir/file5.new", 20));
            assertEquals(12, "1", (int t) -> cs.RestoreUser("user")); // restores /file4.txt; /file3.mp4 conflicts; deletes /dir/file5.new
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
