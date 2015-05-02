package com.red.persistence.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by tom on 2015-05-02.
 */
public class LoginAttemptBlockerImpl implements LoginAttemptBlocker
{

    private final int MAX_ATTEMPT = 10;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptBlockerImpl()
    {
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<String, Integer>()
        {
            @Override
            public Integer load(String key) throws Exception
            {
                return 0;
            }
        });
    }

    public void loginSuccessful(String key)
    {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key)
    {
        Integer attemptsNo;
        try
        {
            attemptsNo = attemptsCache.get(key);
        }
        catch(ExecutionException ex)
        {
            attemptsNo = 0;
        }
        attemptsCache.put(key, ++attemptsNo);
    }

    public boolean isBlocked(String key)
    {
        try
        {
            return attemptsCache.get(key) > MAX_ATTEMPT;
        }
        catch(ExecutionException ex)
        {
            return false;
        }
    }
}
