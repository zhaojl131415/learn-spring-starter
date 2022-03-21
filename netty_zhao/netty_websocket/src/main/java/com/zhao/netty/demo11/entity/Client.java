package com.zhao.netty.demo11.entity;

public class Client {
    private long id;
    private String event;

    public Client() {
        id = 0L;
        event = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
