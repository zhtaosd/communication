package com.zht.communication;

import java.io.Serializable;

public class Friend implements Serializable {
    private String name;
    private String password;

    public Friend(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
