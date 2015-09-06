package com.red.persistence.dao;

import com.red.persistence.model.StockName;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tom on 2015-09-06.
 */
public class StockNameDaoImpl implements StockNameDao
{
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public StockName loadStockNameByCode(String code)
    {
        Criteria criteria = getSession().createCriteria(StockName.class);
        criteria.add(Restrictions.eq("code", code));
        criteria.uniqueResult();
        List<StockName> stockNames = new ArrayList<>();
        stockNames = criteria.list();

        return stockNames.isEmpty() ? null : stockNames.get(0);
    }

    @Transactional
    public StockName saveStockNameByData(String code, String name)
    {
        StockName stockNameToSave = new StockName(code, name);
        getSession().saveOrUpdate(stockNameToSave);
        return stockNameToSave;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<StockName> loadAllStockNames()
    {
        Criteria criteria = getSession().createCriteria(StockName.class);
        List<StockName> stockNames = criteria.list();

        return stockNames;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveStockName(StockName stockName)
    {
        getSession().saveOrUpdate(stockName);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveOrUpdateStockName(StockName stockName)
    {
        getSession().saveOrUpdate(stockName);
    }

    @Transactional
    public void saveAllStockNames(Map<String, String> stockNames)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        int i = 0;
        for (Map.Entry<String, String> entry : stockNames.entrySet())
        {
            if(entry.getKey() == null && entry.getValue() == null)
            {
                continue;
            }
            StockName stockName = new StockName(entry.getKey(), entry.getValue());

            session.save(stockName);
            if ( i % 20 == 0 )
            {
                session.flush();
                session.clear();
            }

            i++;
        }
        tx.commit();
        session.close();
    }



    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
}
