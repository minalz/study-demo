package cn.minalz.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zhouwei
 * @date 2023/11/9 15:12
 */
@Component
public class DemoAnnotationListener {

//    @Async
//    @EventListener(value = {DemoEvent.class})
@EventListener(value = {DemoEvent.class}, condition = "#event.msg == 'my-event'")
    public void processApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("bean-listener annotation 收到了 publisher 发布的消息: " + msg);
    }

}
