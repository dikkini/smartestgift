package com.smartestgift.enums;

/**
 * Created by dikkini on 08.02.14.
 * Email: dikkini@gmail.com
 */
public enum RolesEnum {
    USER_IMAGE(1, "ROLE_USER"),
    GIFT_IMAGE(2, "ROLE_ADMIN");

    private int id;
    private String role;

    RolesEnum(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
