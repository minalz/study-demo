package cn.minalz.agent;

public class TimeStatistics {

    public static ThreadLocal<Long> t = new ThreadLocal<>();

    public static void start() {
        t.set(System.currentTimeMillis());
    }

    public static void end() {
        long time = System.currentTimeMillis() - t.get();
        System.out.println(Thread.currentThread().getStackTrace()[2] + " spend: " + time);
    }

}
