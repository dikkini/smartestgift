package com.paymybill.handler;

public enum RoleEnum {
    USER(1L, "user"),
    ADMIN(2L, "admin");

    private Long id;
    private String roleName;

    RoleEnum(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
