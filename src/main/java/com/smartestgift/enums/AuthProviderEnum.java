package com.smartestgift.enums;

/**
 * Created by dikkini on 16.02.14.
 * Email: dikkini@gmail.com
 */
public enum AuthProviderEnum {
    APPLICATION(1, "application"),
    FACEBOOK(2, "facebook");

    private int id;
    private String name;

    AuthProviderEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
