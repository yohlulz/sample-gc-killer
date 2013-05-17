import java.util.ArrayList;
import java.util.List;

public class KillTheGarbageCollector {
    private static final int LIST_SIZE = 1000000;
    private static final int THRESHOLD = 100;
    private static final int ITERATIONS = 1000;
    private static final int OLD_GEN_ITERATIONS = 10000000;
    private static int NR_THREADS = 3;

    private List<String> outerStore = new ArrayList<>(LIST_SIZE);
    private List<String> oldStore = new ArrayList<>(LIST_SIZE);

    public static void main(String[] args) {

        for (int i = 0; i < NR_THREADS; ++i) {
            new Thread() {
                public void run() {
                    new KillTheGarbageCollector().go();
                }
            }.start();
        }
    }

    private void go() {
        for (int i = 0; i < ITERATIONS; ++i) {
            if (i % THRESHOLD == 0) {
                doOld();
            }
            for (int j = 0; j < LIST_SIZE / NR_THREADS; ++j) {
                outerStore.add(((Integer) j).toString());
            }
            outerStore.clear();
        }
    }

    private void doOld() {
        oldStore.clear();
        for (int j = 0; j < OLD_GEN_ITERATIONS / NR_THREADS; ++j) {
            oldStore.add(((Integer) j).toString());
        }
    }

}