package cn.minalz;

import cn.minalz.kafka.KafkaProducer;
import cn.minalz.kafka.KafkaSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: minalz
 * @date: 2021-06-23 00:03
 **/
@SpringBootApplication
public class KafkaApplication {

    @Autowired
    KafkaSendUtil kafkaSendUtil;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);
//        KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);
        KafkaSendUtil kafkaSendUtil = context.getBean(KafkaSendUtil.class);
        for (int i = 0; i < 500; i++) {
//            kafkaProducer.send();
            List data = new ArrayList<>();
            data.add("测试发送消息" + i);
            kafkaSendUtil.sendmsg(data, "kafka-test", "00000000--" + i);
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }
}
