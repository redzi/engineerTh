package com.red.persistence.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User
{
    private Long id;

    @NotEmpty
    @Size(min=2, max=30)
    private String name;

    @NotEmpty
    @Size(min = 6, message = "Your password must at least 6 characters long")
    private String password;

    @NotNull
    private Email email;
    private UserRole userRole;

    public User()
    {
    }

    public User(String name, String password, Email email, UserRole userRole)
    {
        this.name = name;
        this.password = password;
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

    public Email getEmail()
    {
        return email;
    }

    public void setEmail(Email email)
    {
        this.email = email;
    }

    public UserRole getUserRole()
    {
        return userRole;
    }

    public void setUserRole(UserRole userRole)
    {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!email.equals(user.email)) return false;
        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;
        if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }
}

