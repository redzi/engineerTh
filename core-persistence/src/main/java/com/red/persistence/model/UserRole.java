package com.red.persistence.model;


public class UserRole
{
    private Long id;
    private User user;
    private String role;

    public UserRole()
    {}

    public UserRole(Integer userRoleId, User user, String role)
    {
        this.user = user;
        this.role = role;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
