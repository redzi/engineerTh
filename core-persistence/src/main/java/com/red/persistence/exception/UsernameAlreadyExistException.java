package com.red.persistence.exception;

/**
 * Created by tom on 2015-05-02.
 */
public class UsernameAlreadyExistException extends RuntimeException
{
    public UsernameAlreadyExistException(String description)
    {
        super(description);
    }
}
