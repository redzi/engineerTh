package com.red.persistence.dao;

import com.red.persistence.model.Email;
import com.red.persistence.model.User;
import com.red.persistence.model.UserRole;
import org.hibernate.SessionFactory;

import java.util.List;

public interface UserDao
{

    User findByUserName(String username);

    List<User> findAllUsers();

    void saveUser(User user);

    void saveUserByData(String name, String password, boolean admin, Email email, List<UserRole> userRoleList);

    void setSessionFactory(SessionFactory sessionFactory);
}
