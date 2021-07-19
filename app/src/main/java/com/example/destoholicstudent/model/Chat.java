package com.example.destoholicstudent.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Chat {
    public String msg;
    public String name;
    public String id;
    public long datetime;

    public Chat() {

    }

    public Chat(String msg, String name, String id, long datetime) {
        this.msg = msg;
        this.name = name;
        this.id = id;
        this.datetime = datetime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    @NotNull
    @Override
    public String toString() {
        return "Chat{" +
                "msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
