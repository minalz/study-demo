package cn.minalz.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试创建-生产者
 */
@Slf4j
public class MyTestProducer {

    private final RStream<String, String> myTestStream;

    public MyTestProducer(RedissonClient redisson) {
        this.myTestStream = redisson.getStream("myTestStream");
    }

    public void pushTestDataIdToStream(String testDataId) {
        Map<String, String> messageData = new HashMap<>();
        messageData.put("testDataId", testDataId);
        // 添加消息到Stream,并制定修剪策略:基于条目数量,最多保存1000条数据
        StreamAddArgs<String, String> args = StreamAddArgs.entries(messageData).trim().maxLen(10000).noLimit();
        StreamMessageId streamMessageId = myTestStream.add(args);
        log.info("redisson存储测试信息成功testDataId: {}, streamMessageId: {}", testDataId, streamMessageId);
    }
 
}