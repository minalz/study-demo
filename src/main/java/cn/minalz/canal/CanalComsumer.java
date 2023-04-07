package cn.minalz.canal;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Canal消息消费者  
 */  
@Component
@RabbitListener(queues = RabbitConstant.CanalQueue)
public class CanalComsumer {  

    @RabbitHandler
    public void process(Map<String, Object> msg) {
        System.out.println("收到canal消息：" + msg);  
        boolean isDdl = (boolean) msg.get("isDdl");  
  
        // 不处理DDL事件  
        if (isDdl) {  
            return;  
        }  
  
        // TiCDC的id，应该具有唯一性，先保存再说  
        int tid = (int) msg.get("id");  
        // TiCDC生成该消息的时间戳，13位毫秒级  
        long ts = (long) msg.get("ts");  
        // 数据库  
        String database = (String) msg.get("database");  
        // 表  
        String table = (String) msg.get("table");  
        // 类型：INSERT/UPDATE/DELETE  
        String type = (String) msg.get("type");  
        // 每一列的数据值  
        List<?> data = (List<?>) msg.get("data");
        // 仅当type为UPDATE时才有值，记录每一列的名字和UPDATE之前的数据值  
        List<?> old = (List<?>) msg.get("old");  
  
        // 跳过sys_backup，防止无限循环  
        if ("sys_backup".equalsIgnoreCase(table)) {  
            return;  
        }  
  
        // 只处理指定类型  
        if (!"INSERT".equalsIgnoreCase(type)  
                && !"UPDATE".equalsIgnoreCase(type)  
                && !"DELETE".equalsIgnoreCase(type)) {  
            return;  
        }  
    }  
}