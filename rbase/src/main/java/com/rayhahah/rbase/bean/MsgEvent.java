package com.rayhahah.rbase.bean;

/**
 * 信息封装类
 */
public class MsgEvent {

    private String action;
    private Object data;

    public MsgEvent(String action, Object data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }
}
