package com.red.persistence.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class User implements Serializable
{
    private Long id;

    @NotEmpty
    @Size(min=2, max=30)
    private String name;

    @NotEmpty
    @Size(min = 6, message = "Your password must be at least 6 characters long")
    private String password;

    @com.red.persistence.validation.Email
    private Email email;

    private UserRole userRole;

    private Wallet wallet;

    public User()
    {
    }

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, Email email, UserRole userRole)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    @JsonIgnore
    public Long getId()
    {
        return id;
    }

    @JsonIgnore
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

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    @JsonIgnore
    public void setPassword(String password)
    {
        this.password = password;
    }

    @JsonIgnore
    public Email getEmail()
    {
        return email;
    }

    @JsonIgnore
    public void setEmail(Email email)
    {
        this.email = email;
    }

    @JsonIgnore
    public UserRole getUserRole()
    {
        return userRole;
    }

    @JsonIgnore
    public void setUserRole(UserRole userRole)
    {
        this.userRole = userRole;
    }

    @JsonIgnore
    public Wallet getWallet()
    {
        return wallet;
    }

    @JsonIgnore
    public void setWallet(Wallet wallet)
    {
        this.wallet = wallet;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) return false;
        if (wallet != null ? !wallet.equals(user.wallet) : user.wallet != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (wallet != null ? wallet.hashCode() : 0);
        return result;
    }
}

