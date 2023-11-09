package cn.minalz.event;

import org.springframework.context.ApplicationEvent;

public class DemoEvent extends ApplicationEvent {
    private static final long serialVersionUID = -2753705718295396328L;
    private String msg;

    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
