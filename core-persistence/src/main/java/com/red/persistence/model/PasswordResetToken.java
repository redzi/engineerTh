package com.red.persistence.model;

import java.util.Date;

/**
 * Created by tom on 2015-05-02.
 */
public class PasswordResetToken
{
    private static final int EXPIRATION = 60 * 24;

    private String token;

    private User user;

    private Date expiryDate;

    public PasswordResetToken()
    {}

    public PasswordResetToken(User user, String token, Date expiryDate)
    {
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }
}
