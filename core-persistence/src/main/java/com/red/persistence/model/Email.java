package com.red.persistence.model;

public class Email
{
    private Long id;
    private String address;
    private User user;

    public Email()
    {}

    public Email(String address)
    {
        this.address = address;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
