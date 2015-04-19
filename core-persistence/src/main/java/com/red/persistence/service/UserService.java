package com.red.persistence.service;

import com.red.persistence.dao.UserDao;
import com.red.persistence.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService
{
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        com.red.persistence.model.User user = userDao.findByUserName(username);
        if(user != null)
        {
            List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

            return buildUserForAuthentication(user, authorities);
        }
        else
        {
            throw new UsernameNotFoundException("User "+username+" not found");
        }
    }

    private User buildUserForAuthentication(com.red.persistence.model.User user, List<GrantedAuthority> authorities)
    {
        return new User(user.getName(), user.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(UserRole userRole)
    {
        List<String> roles = new ArrayList<>();

        if (userRole.getRole().equals(1))
        {
            roles.add(SecurityRole.ROLE_USER.getRoleStr());
            roles.add(SecurityRole.ROLE_ADMIN.getRoleStr());

        }
        else if (userRole.getRole().equals(2))
        {
            roles.add(SecurityRole.ROLE_USER.getRoleStr());
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
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

    public com.red.persistence.model.User saveUserRole(String name, String password, String email)
    {
        if(!checkIfUserExists(name))
        {
            return null;
        }
        String hashedPassword = passwordEncoder.encode(password);
        return userDao.saveUserByData(name, hashedPassword, email, SecurityRole.ROLE_USER.getRoleInt());
    }

    public com.red.persistence.model.User saveUserAdminRole(String name, String password, String email)
    {
        if(!checkIfUserExists(name))
        {
            return null;
        }
        String hashedPassword = passwordEncoder.encode(password);
        return userDao.saveUserByData(name, hashedPassword, email, SecurityRole.ROLE_ADMIN.getRoleInt());
    }

    private boolean checkIfUserExists(final String userName)
    {
        com.red.persistence.model.User user = userDao.findByUserName(userName);
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

    public void setPasswordEncoder(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }
}
