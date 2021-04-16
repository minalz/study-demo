package cn.minalz.rabbitmq;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
 
/**
 * @description: 订单的控制器
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger =  LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 模拟提交订单
     * @author minalz
     * @return java.lang.Object
     */
    @GetMapping("")
    public Object submit(){
        String orderId = UUID.randomUUID().toString();
//        logger.info("submit order {}", orderId);
        this.rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.orderExchange, //发送至订单交换机
                RabbitMQConfiguration.routingKeyOrder, //订单定routingKey
                orderId //订单号   这里可以传对象 比如直接传订单对象
                , message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000);
            // 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(1000 * 10 + "");
            return message;
        });
        System.out.println("创建订单：orderId " + orderId + " .... " + sdf.format(new Date()));
        return "{'orderId':'"+orderId+"'}";
    }
}