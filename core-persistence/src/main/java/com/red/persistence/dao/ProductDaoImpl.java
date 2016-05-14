package com.red.persistence.dao;

import org.hibernate.SessionFactory;

/**
 * Created by tom on 2016-05-08.
 */
public class ProductDaoImpl implements ProductDao
{
    private SessionFactory sessionFactory;






    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
}
