package cn.minalz.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/event")
public class DemoClient implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/publish")
    public void publish(){
        DemoPublisher bean = applicationContext.getBean(DemoPublisher.class);
        bean.sendMsg("发布者发送消息......");
    }

    @GetMapping("/annotationPublish")
    public void annotationPublish(){
        DemoPublisher bean = applicationContext.getBean(DemoPublisher.class);
        bean.sendMsg("发布者发送消息......");
    }
}

