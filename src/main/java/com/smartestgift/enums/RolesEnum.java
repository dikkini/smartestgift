package com.smartestgift.enums;

/**
 * Created by dikkini on 08.02.14.
 * Email: dikkini@gmail.com
 */
public enum RolesEnum {
    USER_ROLE(2, "ROLE_USER"),
    ADMIN_ROLE(1, "ROLE_ADMIN");

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
