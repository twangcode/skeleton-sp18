package hw3.hash;

import java.util.HashMap;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        HashMap<Integer, Integer> buckets = new HashMap<>();
        int N = oomages.size();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (buckets.containsKey(bucketNum)) {
                buckets.put(bucketNum, buckets.get(bucketNum) + 1);
            }
            else {
                buckets.put(bucketNum, 1);
            }
        }

        for (int value : buckets.values()) {
            if (value >= N / 2.5 || value <= N / 50) {
                return false;
            }
        }
        return true;
    }
}
