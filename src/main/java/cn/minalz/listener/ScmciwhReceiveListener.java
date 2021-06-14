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
        try {
            String result = record.value().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



     @KafkaListener(topics = "#{'${kafka.qms.receive.topic}'.split(',')}", groupId = "${kafka.receive.group}")
     public void listenQms(ConsumerRecord<?, ?> record, Acknowledgment ack) {
         try {
             String result = record.value().toString();
             ack.acknowledge();
         } catch (Exception e) {
         }
     }

     @KafkaListener(topics = "#{'${kafka.qms.return.receive.topic}'.split(',')}", groupId = "${kafka.receive.group}")
     public void listenQmsReturn(ConsumerRecord<?, ?> record, Acknowledgment ack) {
         try {
             String result = record.value().toString();
             ack.acknowledge();
         } catch (Exception e) {
         }
     }

}
