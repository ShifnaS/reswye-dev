package com.nexgensm.reswye.util;

public class Message {

    private  String message;
    private  int flag;


    public Message(String message,int flag) {
        this.message = message;
        this.flag=flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
