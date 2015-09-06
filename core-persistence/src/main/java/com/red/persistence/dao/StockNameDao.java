package com.red.persistence.dao;

import com.red.persistence.model.StockName;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by tom on 2015-09-06.
 */
public interface StockNameDao
{
    StockName loadStockNameByCode(String code);

    List<StockName> loadAllStockNames();

    void saveStockName(StockName stockName);

    void saveOrUpdateStockName(StockName stockName);

    StockName saveStockNameByData(String code, String name);

    void saveAllStockNames(Map<String, String> stockNames);

    void setSessionFactory(SessionFactory sessionFactory);
}
