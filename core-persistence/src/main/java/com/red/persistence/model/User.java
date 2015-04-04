package com.red.persistence.model;

public class User
{
    private String firstname;
    private String lastname;
    private boolean admin = false;

    public User()
    {
    }

    public User(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public boolean isAdmin()
    {
        return admin;
    }

    public void setAdmin(boolean admin)
    {
        this.admin = admin;
    }
}

