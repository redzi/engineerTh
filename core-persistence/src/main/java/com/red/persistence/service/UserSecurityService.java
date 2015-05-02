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

public class UserSecurityService implements UserDetailsService
{
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        com.red.persistence.model.User user = userDao.loadByUserName(username);
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

    public String encodePassword(String rawPassword)
    {
        return passwordEncoder.encode(rawPassword);
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
