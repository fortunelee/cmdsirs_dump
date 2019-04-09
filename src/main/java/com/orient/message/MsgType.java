package com.orient.message;

import java.io.Serializable;

public class MsgType implements Serializable {


    public MsgType() {
        super();
    }

    public MsgType(String msgType, String msg) {
        this.msgType = msgType;
        this.msg = msg;
    }

    private String msgType;

    private String msg;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgType{" +
                "msgType='" + msgType + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
