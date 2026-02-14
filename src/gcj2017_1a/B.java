package gcj2017_1a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class B {

    private static class Package {
        private final int contents[];

        private Package(int N) {
            this.contents = new int[N];
        }

        public void set(int n, int val) {
            contents[n] = val;
        }
    }

    private static class Grouping {
        private final int groups[];

        private Grouping(int P) {
            this.groups = new int[P];
            for (int p=0; p<P; p++) {
                groups[p] = -1;
            }
        }

        public Grouping(int[] groups) {
            this.groups = groups;
        }

        public Grouping cloneGroup() {
            return new Grouping(Arrays.copyOf(groups, groups.length));
        }
    }

    private static class KitsCandidates {
        final int kitsGrouped;
        final List<Grouping> candidates;
        final int curPkg;

        private KitsCandidates(int curPkg, int kitsGrouped, List<Grouping> candidates) {
            this.kitsGrouped = kitsGrouped;
            this.candidates = candidates;
            this.curPkg = curPkg;
        }
    }

    public static void main(String[] args) {
        try(Scanner stdin = new Scanner(System.in)) {
            final int T = stdin.nextInt();

            for (int t=0; t<T; t++) {
                final int N = stdin.nextInt();
                final int P = stdin.nextInt();

                final Package[] packages = new Package[P];
                for (int p=0; p<P; p++) {
                    packages[p] = new Package(N);
                }

                final Package portion = new Package(N);
                for (int n=0; n<N; n++) {
                    portion.set(n, stdin.nextInt());
                }

                for (int n=0; n<N; n++) {
                    for (int p=0; p<P; p++) {
                        packages[p].set(n, stdin.nextInt());
                    }
                }

                KitsCandidates candidates = new KitsCandidates(0, 0, new ArrayList<>());
                candidates.candidates.add(new Grouping(P));

                while (candidates.curPkg < P) {
                    candidates = recurse(candidates, portion, packages);
                }

                System.out.format("Case #%d: %d\n", t+1, candidates.kitsGrouped);
            }
        }
    }

    private static KitsCandidates recurse(KitsCandidates candidates, Package portion, Package[] packages) {
        final KitsCandidates optCandidate = new KitsCandidates(candidates.curPkg+1, candidates.kitsGrouped+1, new ArrayList<>());

        for (Grouping group : candidates.candidates) {
            combine(group, candidates, optCandidate.candidates, 0, portion, packages);
        }

        if (!optCandidate.candidates.isEmpty()) {
            return optCandidate;
        } else {
            return new KitsCandidates(candidates.curPkg+1, candidates.kitsGrouped, candidates.candidates);
        }
    }

    private static void combine(Grouping group, KitsCandidates candidates, List<Grouping> newGroupings, int idx, Package portion, Package[] packages) {
        if (idx == candidates.curPkg) {
            group.groups[candidates.curPkg] = candidates.kitsGrouped+1;
            if (validate(group, candidates.kitsGrouped+1, candidates.curPkg, portion, packages)) {
                newGroupings.add(group.cloneGroup());
            }
            group.groups[candidates.curPkg] = -1;
        } else {
            if (group.groups[idx] == -1) {
                group.groups[idx] = candidates.kitsGrouped+1;
                combine(group, candidates, newGroupings, idx+1, portion, packages);
                group.groups[idx] = -1;
            }
            combine(group, candidates, newGroupings, idx+1, portion, packages);
        }
    }

    private static boolean validate(Grouping group, int groupToValidate, int maxIdx, Package portion, Package[] packages) {
        int leftBound=0; int rightBound=0;
        int value = 0;
        for (int i=0; i<=maxIdx; i++) {
            if (group.groups[i] == groupToValidate) {
                value += packages[i].contents[0];
            }
        }

        int factor = (int)((double)value/portion.contents[0]+0.5);
        if (Math.abs(value-portion.contents[0]*factor) > portion.contents[0]*factor/10) {
            return false;
        }

        leftBound = rightBound = factor;
        for (int d=1; ; d++) {
            if (Math.abs(value-portion.contents[0]*(factor+d)) > portion.contents[0]*(factor+d)/10) {
                break;
            }
            rightBound++;
        }
        for (int d=-1; ; d--) {
            if (Math.abs(value-portion.contents[0]*(factor+d)) > portion.contents[0]*(factor+d)/10) {
                break;
            }
            leftBound++;
        }

        for (int n=1; n<portion.contents.length; n++) {
            value = 0;
            for (int i=0; i<=maxIdx; i++) {
                if (group.groups[i] == groupToValidate) {
                    value += packages[i].contents[n];
                }
            }

            for (int f=leftBound; f<=rightBound; f++) {
                if (Math.abs(value-portion.contents[n]*f) > portion.contents[n]*f/10) {
                    leftBound++;
                } else {
                    break;
                }
            }
            for (int f=rightBound; f>=leftBound; f--) {
                if (Math.abs(value-portion.contents[n]*f) > portion.contents[n]*f/10) {
                    rightBound--;
                } else {
                    break;
                }
            }
            if (rightBound<leftBound) {
                return false;
            }
        }

        return true;
    }

}
