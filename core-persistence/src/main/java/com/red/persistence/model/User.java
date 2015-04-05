package com.red.persistence.model;

import java.util.ArrayList;
import java.util.List;

public class User
{
    private Long id;
    private String name;
    private String password;
    private boolean admin = false;
    private Email email;
    private List<UserRole> userRole = new ArrayList<UserRole>();

    public User()
    {
    }

    public User(String name, String password, boolean admin, Email email, List<UserRole> userRole)
    {
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.email = email;
        this.userRole = userRole;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isAdmin()
    {
        return admin;
    }

    public void setAdmin(boolean admin)
    {
        this.admin = admin;
    }

    public Email getEmail()
    {
        return email;
    }

    public void setEmail(Email email)
    {
        this.email = email;
    }

    public List<UserRole> getUserRole()
    {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole)
    {
        this.userRole = userRole;
    }
}

