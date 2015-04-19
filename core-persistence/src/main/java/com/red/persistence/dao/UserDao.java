package com.red.persistence.dao;

import com.red.persistence.model.User;
import org.hibernate.SessionFactory;

import java.util.List;

public interface UserDao
{

    User findByUserName(String username);

    List<User> findAllUsers();

    void saveUser(User user);

    User saveUserByData(String name, String password, String emailAddress, Integer userRoleVal);

    void setSessionFactory(SessionFactory sessionFactory);
}
