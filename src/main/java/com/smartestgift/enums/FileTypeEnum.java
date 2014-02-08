package com.smartestgift.enums;

/**
 * Created by dikkini on 08.02.14.
 * Email: dikkini@gmail.com
 */
public enum FileTypeEnum {
    USER_IMAGE(1, "a"),
    GIFT_IMAGE(2, "b");

    private int id;
    private String content_type;

    FileTypeEnum(int id, String content_type) {
        this.id = id;
        this.content_type = content_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
}
