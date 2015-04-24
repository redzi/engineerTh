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


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (address != null ? !address.equals(email.address) : email.address != null) return false;
        if (user != null ? !user.equals(email.user) : email.user != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = address != null ? address.hashCode() : 0;
        return result;
    }

    @Override
    public String toString()
    {
        return address;
    }
}
