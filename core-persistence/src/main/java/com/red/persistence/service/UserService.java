package com.red.persistence.service;

import com.red.persistence.dao.UserDao;
import com.red.persistence.model.Email;
import com.red.persistence.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService implements UserDetailsService
{
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        com.red.persistence.model.User user = userDao.findByUserName(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.red.persistence.model.User user, List<GrantedAuthority> authorities)
    {
        return new User(user.getName(), user.getPassword(), user.isAdmin(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles)
    {
        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (UserRole userRole : userRoles)
        {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> result = new ArrayList<>(setAuths);

        return result;
    }

    public com.red.persistence.model.User loadUserByName(String name)
    {
        return  userDao.findByUserName(name);
    }

    public List<com.red.persistence.model.User> loadAllUsers()
    {
        return userDao.findAllUsers();
    }

    public void saveUser(com.red.persistence.model.User user)
    {
        userDao.saveUser(user);
    }

    public void saveUserByData(String name, String password, boolean admin, Email email, List<UserRole> userRoleList)
    {
        userDao.saveUserByData(name, password, admin, email, userRoleList);
    }

    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

}
