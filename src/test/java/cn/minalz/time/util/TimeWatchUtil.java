package cn.minalz.time.util;

import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * 时间监测工具方法
 * @author minalz
 */
public class TimeWatchUtil {

    /**
     * 统计输出总耗时
     */
    @Test
    public void test01() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(1000);
        sw.stop();
        System.out.println("总共耗时：" + sw.getTotalTimeMillis());
    }

    /**
     * 输出最后一个任务的耗时
     */
    @Test
    public void test02() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start("A");//setting a task name
        //long task simulation
        Thread.sleep(1000);
        sw.stop();
        System.out.println("总共耗时：" + sw.getLastTaskTimeMillis());
    }

    /**
     * 以优雅的格式打出所有任务的耗时以及占比
     */
    @Test
    public void test03() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start("A");
        Thread.sleep(500);
        sw.stop();
        sw.start("B");
        Thread.sleep(300);
        sw.stop();
        sw.start("C");
        Thread.sleep(200);
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

}
