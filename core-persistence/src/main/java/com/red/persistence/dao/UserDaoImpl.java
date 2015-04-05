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
    public User findByUserName(String username)
    {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", username));
        return (User) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<User> findAllUsers()
    {
        Criteria criteria = getSession().createCriteria(User.class);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveUser(User user)
    {
        getSession().saveOrUpdate(user);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveUserByData(String name, String password, boolean admin, Email email, List<UserRole> userRoleList)
    {
        User user = new User(name, password, admin, email, userRoleList);
        getSession().saveOrUpdate(user);
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
