package hashcodetest;
public class HashCodeTest {
    public static void main(String[] args) {
        HashCodeTest h = new HashCodeTest();
        try {
            h.testHashCode();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    final static int TEST_LENGTH = 1;
    final static int NUM_OF_THREADS = 10;
    public void testHashCode() throws InterruptedException {
        IntRange rangeArrayA[] = new IntRange[TEST_LENGTH];
        for (int i = 0; i < TEST_LENGTH; i++) {
            rangeArrayA[i] = new IntRange(i); 
        }
        hashThread[] h = new hashThread[NUM_OF_THREADS]; 
        for (int i = 0; i < NUM_OF_THREADS; i++) { 
            h[i] = new hashThread(rangeArrayA); 
        }
        for (int i = 0; i < NUM_OF_THREADS; i++) { 
            h[i].start(); 
        }
        for (int i = 0; i < NUM_OF_THREADS; i++) {
            h[i].join();
        }
        for (int i = 0; i < TEST_LENGTH; i++) {
            for (int j = 0; j < NUM_OF_THREADS - 1; j++) {
                assert(h[j].hash[i] == h[j + 1].hash[i]);
            }
        }
    }
    class hashThread extends Thread {
		IntRange[] t = new IntRange[TEST_LENGTH];
        public int[] hash;
		public hashThread(IntRange[] rangeArrayA) {
            t = new IntRange[rangeArrayA.length];
            for (int i = 0; i < rangeArrayA.length; i++) {
                t[i] = rangeArrayA[i];
            }
        }
        public void run() {
            hash = new int[t.length];
            for (int i = 0; i < t.length; i++) {
                hash[i] = t[i].hashCode();
            }
        }
    }
}
