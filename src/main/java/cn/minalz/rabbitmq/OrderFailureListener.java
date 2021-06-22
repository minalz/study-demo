package cn.minalz.rabbitmq;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.rabbitmq.client.Channel;
 
/**
 * @description: 订单失效监听器
 **/
//@Component
public class OrderFailureListener {
    private static final Logger logger =  LoggerFactory.getLogger(OrderFailureListener.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @RabbitListener(
            queues = RabbitMQConfiguration.dealQueueOrder //设置订单失效的队列
    )
    public void process(String order, Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
 
//        logger.info("【订单号】 - [{}]",  order);
        // 判断订单是否已经支付，如果支付则；否则，取消订单（逻辑代码省略)
 
        // 手动ack
//        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
//        channel.basicAck(deliveryTag, false);
        System.out.println("执行结束：order + " + order + " .... " + sdf.format(new Date()));
 
    }
}