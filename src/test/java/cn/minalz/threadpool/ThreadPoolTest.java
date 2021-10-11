package cn.minalz.threadpool;

import cn.minalz.KernelApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KernelApplication.class)
public class ThreadPoolTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void testThreadPool() {
        for (int i = 0; i < 100; i++) {
            asyncService.executeAsync();
        }
    }
}
