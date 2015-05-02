package com.red.persistence.service;

/**
 * Created by tom on 2015-05-02.
 */
public interface LoginAttemptBlocker
{
    void loginSuccessful(String key);

    public void loginFailed(String key);

    public boolean isBlocked(String key);

}
