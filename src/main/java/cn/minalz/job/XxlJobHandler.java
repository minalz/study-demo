package cn.minalz.job;

import cn.minalz.redisson.MyTestProducer;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XxlJobHandler {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 更新状态
     **/
    @XxlJob("UpdateStatus")
    public void updateStatus() {
        String jobParam = XxlJobHelper.getJobParam();
        MyTestProducer myTestProducer = new MyTestProducer(redissonClient);
        for (int i = 1; i <= 10000; i++) {
            myTestProducer.pushTestDataIdToStream(i + "");
        }
        log.info("任务执行,并且生产者推送消息到myTestStream中: {}", jobParam);
    }

}
