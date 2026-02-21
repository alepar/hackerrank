package prep2026.phase2.trees;

// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/description/
public class LowestCommonAncestorBST {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    class Solution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            while (root != null) {
                if (root == p || root == q) return root;

                if (root.val < p.val && root.val > q.val) return root;
                if (root.val > p.val && root.val < q.val) return root;

                if (root.val < p.val && root.val < q.val)
                    root = root.right;
                else
                    root = root.left;
            }

            return null;
        }
    }

    // Helper: build BST by inserting values in given order
    static TreeNode buildBST(int[] vals) {
        if (vals.length == 0) return null;
        TreeNode root = new TreeNode(vals[0]);
        for (int i = 1; i < vals.length; i++) insert(root, vals[i]);
        return root;
    }

    static void insert(TreeNode root, int val) {
        if (val < root.val) {
            if (root.left == null) root.left = new TreeNode(val);
            else insert(root.left, val);
        } else {
            if (root.right == null) root.right = new TreeNode(val);
            else insert(root.right, val);
        }
    }

    // Helper: find node by value
    static TreeNode find(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        return val < root.val ? find(root.left, val) : find(root.right, val);
    }

    public static void main(String[] args) {
        LowestCommonAncestorBST outer = new LowestCommonAncestorBST();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        //       6
        //      / \
        //     2   8
        //    / \ / \
        //   0  4 7  9
        //     / \
        //    3   5
        TreeNode tree1 = buildBST(new int[]{6, 2, 8, 0, 4, 7, 9, 3, 5});

        // Test 1: Example 1 - LCA(2,8) = 6
        {
            TreeNode result = sol.lowestCommonAncestor(tree1, find(tree1, 2), find(tree1, 8));
            if (result != null && result.val == 6) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        // Test 2: Example 2 - LCA(2,4) = 2 (ancestor is one of the nodes)
        {
            TreeNode result = sol.lowestCommonAncestor(tree1, find(tree1, 2), find(tree1, 4));
            if (result != null && result.val == 2) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        // Test 3: Both nodes in right subtree - LCA(7,9) = 8
        {
            TreeNode result = sol.lowestCommonAncestor(tree1, find(tree1, 7), find(tree1, 9));
            if (result != null && result.val == 8) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        // Test 4: Leaf nodes across subtrees - LCA(0,5) = 2
        {
            TreeNode result = sol.lowestCommonAncestor(tree1, find(tree1, 0), find(tree1, 5));
            if (result != null && result.val == 2) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        // Test 5: Adjacent parent-child - LCA(4,3) = 4
        {
            TreeNode result = sol.lowestCommonAncestor(tree1, find(tree1, 4), find(tree1, 3));
            if (result != null && result.val == 4) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        // Test 6: Root is LCA - LCA(0,9) = 6
        {
            TreeNode result = sol.lowestCommonAncestor(tree1, find(tree1, 0), find(tree1, 9));
            if (result != null && result.val == 6) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        //   2
        //    \
        //     3
        TreeNode tree2 = buildBST(new int[]{2, 3});

        // Test 7: Two-node tree - LCA(2,3) = 2
        {
            TreeNode result = sol.lowestCommonAncestor(tree2, find(tree2, 2), find(tree2, 3));
            if (result != null && result.val == 2) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + (result == null ? "null" : result.val)); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}
