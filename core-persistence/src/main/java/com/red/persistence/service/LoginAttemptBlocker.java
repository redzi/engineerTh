package com.red.persistence.service;

/**
 * Created by tom on 2015-05-02.
 */
public interface LoginAttemptBlocker
{
    void loginSuccessful(String username);

    public void loginFailed(String username);

    public boolean isBlocked(String username);

}
