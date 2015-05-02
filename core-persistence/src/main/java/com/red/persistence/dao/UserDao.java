package com.red.persistence.dao;

import com.red.persistence.model.User;
import org.hibernate.SessionFactory;

import java.util.List;

public interface UserDao
{

    User loadByUserName(String username);

    List<User> loadAllUsers();

    void saveUser(User user);

    void saveOrUpdateUser(User user);

    User saveUserByData(String name, String password, String emailAddress, Integer userRoleVal);

    void setSessionFactory(SessionFactory sessionFactory);
}
