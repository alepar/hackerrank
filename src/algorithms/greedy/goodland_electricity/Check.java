package algorithms.greedy.goodland_electricity;

public class Check {
    public static void main(String[] args) {

        final int[] towers = new int[] { 16, 51, 75, 92, 122, 148, 184, 201, 229, 261, 286, 303, 328, 360, 376, 400, 426, 440, 477, 511, 527, 556, 587, 598, 632, 663, 696, 707, 738, 775, 803, 824, 858, 892, 919, 949, 962, 993 };

        for (int i=1; i < towers.length; i++) {
            System.out.println(towers[i]-towers[i-1]);
        }

    }
}
