package cn.minalz.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.redisson.client.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class RedisStreamInitiator implements ApplicationRunner {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) {
        log.info("【initMyTestStreamAndGroup Starting...】");
        initMyTestStreamAndGroup();
        log.info("【initMyTestStreamAndGroup End...】");
    }

    private void initMyTestStreamAndGroup() {
        RStream<String, String> messageStream = redissonClient.getStream("myTestStream");
        if (!messageStream.isExists()) {
            Map<String, String> initMap = new HashMap<>();
            initMap.put("init", "value");
            StreamAddArgs<String, String> args = StreamAddArgs.entries(initMap).trim().maxLen(1000).noLimit();
            StreamMessageId initMessageId = messageStream.add(args);
            messageStream.remove(initMessageId);
        }
        boolean isExistGroup = messageStream.listGroups().stream().anyMatch(group -> Objects.equals(group.getName(), "my_test_group"));
        if (!isExistGroup) {
            try {
                messageStream.createGroup(StreamCreateGroupArgs.name("my_test_group"));
            } catch (RedisException e) {
                log.error("测试消息组已存在,无需创建");
            }
        }
        MyTestConsumer consumer = new MyTestConsumer(redissonClient, "my_test_consumer_1");
        consumer.startProcessing();
    }

}
