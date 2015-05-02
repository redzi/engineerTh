package com.red.persistence.service;

import com.red.persistence.dao.UserDao;
import com.red.persistence.exception.UsernameAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by tom on 2015-05-02.
 */
public class UserServiceImpl implements UserService
{
    private UserDao userDao;
    private UserSecurityService userSecurityService;

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        return userSecurityService.loadUserByUsername(username);
    }

    public com.red.persistence.model.User loadUserByName(String name)
    {
        return  userDao.loadByUserName(name);
    }

    public List<com.red.persistence.model.User> loadAllUsers()
    {
        return userDao.loadAllUsers();
    }

    public void saveUser(com.red.persistence.model.User user)
    {
        userDao.saveUser(user);
    }

    public com.red.persistence.model.User saveUserRole(String name, String password, String email)
    {
        if(!checkIfUserExists(name))
        {
            throw new UsernameAlreadyExistException("User not exists");
        }
        String hashedPassword = userSecurityService.encodePassword(password);
        return userDao.saveUserByData(name, hashedPassword, email, SecurityRole.ROLE_USER.getRoleInt());
    }

    public com.red.persistence.model.User saveUserAdminRole(String name, String password, String email)
    {
        if(!checkIfUserExists(name))
        {
            throw new UsernameAlreadyExistException("User not exists");
        }
        String hashedPassword = userSecurityService.encodePassword(password);
        return userDao.saveUserByData(name, hashedPassword, email, SecurityRole.ROLE_ADMIN.getRoleInt());
    }

    private boolean checkIfUserExists(final String userName)
    {
        com.red.persistence.model.User user = userDao.loadByUserName(userName);
        if(user != null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    public void setUserSecurityService(UserSecurityService userSecurityService)
    {
        this.userSecurityService = userSecurityService;
    }
}
