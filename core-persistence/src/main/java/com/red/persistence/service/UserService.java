package com.red.persistence.service;

import com.red.persistence.dao.UserDao;
import com.red.persistence.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by tom on 2015-05-02.
 */
public interface UserService
{
    UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException;

    com.red.persistence.model.User loadUserByName(String name);

    List<User> loadAllUsers();

    void saveUser(com.red.persistence.model.User user);

    void updateUser(com.red.persistence.model.User user);

    com.red.persistence.model.User saveUserRole(String name, String password, String email);

    com.red.persistence.model.User saveAdminRole(String name, String password, String email);

    void setUserDao(UserDao userDao);

    void setUserSecurityService(UserSecurityService userSecurityService);

}
