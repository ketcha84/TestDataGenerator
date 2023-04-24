package testData.generator.times;

final public class TimeCounter {
    private static long start;
    private static long stop;
    private static boolean enabled = false;

    public static void setStart() {
        start = System.currentTimeMillis();
        enabled = true;

    }

    public static long stopCount() {
        if (enabled == false) {
            return -1;
        } else {
            stop = System.currentTimeMillis();
            enabled = false;
            return TimeCounter.stop - TimeCounter.start;
        }
    }
}
