package com.red.persistence.dao;

import com.red.persistence.model.StockData;
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
    public StockData loadStockNameByCode(String code)
    {
        Criteria criteria = getSession().createCriteria(StockData.class);
        criteria.add(Restrictions.eq("code", code));
        criteria.uniqueResult();
        List<StockData> stockDatas = new ArrayList<>();
        stockDatas = criteria.list();

        return stockDatas.isEmpty() ? null : stockDatas.get(0);
    }

    @Transactional
    public StockData saveStockNameByData(String code, String name)
    {
        StockData stockNameToSave = new StockData(code, name);
        getSession().saveOrUpdate(stockNameToSave);
        return stockNameToSave;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<StockData> loadAllStockNames()
    {
        Criteria criteria = getSession().createCriteria(StockData.class);
        List<StockData> stockDatas = criteria.list();

        return stockDatas;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveStockName(StockData stockData)
    {
        getSession().saveOrUpdate(stockData);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveOrUpdateStockName(StockData stockData)
    {
        getSession().saveOrUpdate(stockData);
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
            StockData stockData = new StockData(entry.getKey(), entry.getValue());

            session.save(stockData);
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
