package cn.minalz.kafka;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;

@Component
public class KafkaSendUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendmsg(List data,String topic,String uuid) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic,
                JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("发送消息成功：" + topic + " : " + uuid);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("发送消息失败：" + topic + " : " + uuid);
            }
        });
    }

}
