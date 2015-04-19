package com.red.persistence.model;


public class UserRole
{
    private Long id;
    private User user;
    private Integer role;

    public UserRole()
    {}

    public UserRole(User user, Integer role)
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

    public Integer getRole()
    {
        return role;
    }

    public void setRole(Integer role)
    {
        this.role = role;
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
