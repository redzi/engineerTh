package com.red.persistence.dao;

import com.red.persistence.model.Email;
import com.red.persistence.model.User;
import com.red.persistence.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDaoImpl implements UserDao
{
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public User loadByUserName(String username)
    {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", username));
        return (User) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<User> loadAllUsers()
    {
        Criteria criteria = getSession().createCriteria(User.class);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveUser(User user)
    {
        getSession().save(user);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveOrUpdateUser(User user)
    {
        getSession().saveOrUpdate(user);
    }


    @SuppressWarnings("unchecked")
    @Transactional
    public User saveUserByData(String name, String password, String emailAddress, Integer userRoleVal)
    {
        User user = new User();
        Email email = new Email();
        UserRole userRole = new UserRole();

        email.setAddress(emailAddress);
        email.setUser(user);

        userRole.setRole(userRoleVal);
        userRole.setUser(user);

        user.setEmail(email);
        user.setUserRole(userRole);
        user.setName(name);
        user.setPassword(password);

        getSession().saveOrUpdate(user);

        return user;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
}
