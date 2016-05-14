package com.red.persistence.dao;

import com.red.persistence.model.StockData;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by tom on 2015-09-06.
 */
public interface StockNameDao
{
    StockData loadStockNameByCode(String code);

    List<StockData> loadAllStockNames();

    void saveStockName(StockData stockData);

    void saveOrUpdateStockName(StockData stockData);

    StockData saveStockNameByData(String code, String name);

    void saveAllStockNames(Map<String, String> stockNames);

    void setSessionFactory(SessionFactory sessionFactory);
}
