package cn.minalz.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class ScmciwhReceiveListener {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @KafkaListener(topics = "#{'${kafka.receive.topic}'.split(',')}", groupId = "${kafka.receive.group}")
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        String result = record.value().toString();
        try {
            logger.info("消费端接收成功：" + result);
            ack.acknowledge();
        } catch (Exception e) {
            logger.error("消费端接收失败：" + result);
            e.printStackTrace();
        }
    }

}
