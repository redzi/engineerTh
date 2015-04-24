package com.red.persistence.service;

public enum SecurityRole
{
    ROLE_ADMIN(1, "ROLE_ADMIN"), ROLE_USER(2, "ROLE_USER");

    private final String roleStr;
    private final Integer role;

    private SecurityRole(Integer role, String roleStr)
    {
        this.roleStr = roleStr;
        this.role = role;
    }

    public String getRoleStr()
    {
        return roleStr;
    }

    public Integer getRoleInt()
    {
        return role;
    }
}
