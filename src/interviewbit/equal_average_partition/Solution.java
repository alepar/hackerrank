package interviewbit.equal_average_partition;

import java.util.*;

@SuppressWarnings("Duplicates")
public class Solution {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

        int[] ints = {12, 23, 38, 3, 45, 14, 33, 37, 35, 50, 27, 8, 5, 47, 12, 43, 2, 49, 39, 30, 18, 46, 7, 27};
//        int[] ints = {35, 44, 12, 39, 44, 19, 25, 2};
//        int[] ints = {33, 2, 12, 25, 38, 11, 46, 17, 19, 47, 13, 0, 39, 42, 8, 4};
//        int[] ints = {47, 14, 30, 19, 30, 4, 32, 32, 15, 2, 6, 24};
        for (int i : ints) {
            list.add(i);
        }

//        for (int i : new int[]{1, 7, 15, 29, 11, 9}) {
//            list.add(i);
//        }

        System.out.println(new Solution().avgset(list));
    }


    public ArrayList<ArrayList<Integer>> avgset(ArrayList<Integer> a) {
        if (a.size() < 2) {
            return new ArrayList<>();
        }


        Collections.sort(a, Comparator.naturalOrder());

        List<Integer> indexes = getIndexList(a);
        Collections.reverse(indexes);

        int sum = a.stream().mapToInt(value -> value).sum();

        List<Integer>[][][] lists = new List[a.size() / 2 + 1][a.size() + 1][sum / 2 + 1];

        // initialize top row (P(0,x)) of P to True
        for (int i = 0; i < a.size() + 1; i++) {
            lists[0][i][0] = new ArrayList<>();
        }

        for (int l = 1; l < a.size() / 2 + 1; l++) {
            for (int si = 1; si < sum / 2 + 2; si++) {
                int s = si - 1;
                for (int n = 1; n < a.size() + 1; n++) {
                    if (s - a.get(n - 1) >= 0) {
                        List<Integer> prevLevel = lists[l - 1][n - 1][s - a.get(n - 1)];
                        List<Integer> sameLevel = lists[l][n - 1][s];

                        if (prevLevel == null) {
                            lists[l][n][s] = sameLevel;
                        } else {
                            final List<Integer> prevAppended = append(prevLevel, a.get(n - 1));
                            if (sameLevel == null) {
                                lists[l][n][s] = prevAppended;
                            } else {
                                List<Integer> list = chooseList(prevAppended, sameLevel);

                                lists[l][n][s] = list;
                            }
                        }
                    } else {
                        lists[l][n][s] = lists[l][n - 1][s];
                    }
                }
            }
        }

//        System.out.println(a);
//        System.out.println(avg);
//
//        for (int s = 1; s < sum / 2 + 2; s++) {
//            System.out.print(s - 1 + " : ");
//            for (int i = 0; i < a.size() + 1; i++) {
//                System.out.print(lists[12][i][s - 1]);
//            }
//            System.out.println();
//        }

        List<Integer> first = null;
        for (int l = 0; l < a.size() / 2 + 1; l++) {
            for (int si = 1; si < sum / 2 + 2; si++) {
                int s = si - 1;
                for (int i = 1; i < a.size() + 1; i++) {
                    List<Integer> list = lists[l][i][s];

                    if (list != null && (s * a.size() == sum * list.size())) {
                        first = chooseList(first, list);
                    }
                }
            }
        }

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (first == null || first.isEmpty() || first.size() == a.size()) {
            return res;
        }

        res.add((ArrayList<Integer>) first);
        res.add((ArrayList<Integer>) getSecond(first, a));

        return res;
    }

    private List<Integer> chooseList(List<Integer> l1, List<Integer> l2) {
        if (l1 == null && l2 == null) {
            return null;
        }

        if (l1 == null || l1.isEmpty()) {
            return l2;
        }

        if (l2 == null || l2.isEmpty()) {
            return l1;
        }

        if (l1.size() < l2.size()) {
            return l1;
        }

        if (l2.size() < l1.size()) {
            return l2;
        }

        int j = 0;
        while (j < l1.size() && Objects.equals(l1.get(j), l2.get(j))) {
            j++;
        }

        if (j < l1.size()) {
            if (l1.get(j) < l2.get(j)) {
                return l1;
            } else {
                return l2;
            }
        }

        return l1;
    }

    private List<Integer> append(List<Integer> list, Integer e) {
        ArrayList<Integer> newList = new ArrayList<>(list);
        newList.add(e);

        return newList;
    }

    private List<Integer> getIndexList(ArrayList<Integer> a) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            indexes.add(i);
        }
        return indexes;
    }

    public List<Integer> getSecond(List<Integer> first, List<Integer> a) {
        int i1 = 0;
        int i2 = 0;

        List<Integer> r = new ArrayList<>();
        while (i1 < first.size() && i2 < a.size()) {
            if (Objects.equals(first.get(i1), a.get(i2))) {
                i1++;
                i2++;
            } else {
                r.add(a.get(i2));
                i2++;
            }
        }

        while (i2 < a.size()) {
            r.add(a.get(i2++));
        }

        return r;
    }
}