package cn.minalz.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 参考链接：
 * <a href="https://blog.csdn.net/weixin_42653522/article/details/117151913">Spring事件监听</a>
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMsg();
        System.out.println("bean-listener 收到了 publisher 发布的消息: " + msg);
    }
}
