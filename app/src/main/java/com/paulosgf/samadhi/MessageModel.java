package com.paulosgf.samadhi;

import java.io.Serializable;

public class MessageModel implements Serializable {
    private String message;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
