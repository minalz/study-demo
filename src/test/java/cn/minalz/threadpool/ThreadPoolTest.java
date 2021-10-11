package cn.minalz.threadpool;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadPoolTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void testThreadPool() {
        asyncService.executeAsync();
    }
}
