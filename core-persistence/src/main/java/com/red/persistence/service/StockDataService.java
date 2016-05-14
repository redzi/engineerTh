package com.red.persistence.service;

import com.red.persistence.dao.StockNameDao;
import com.red.persistence.model.StockData;

import java.util.List;
import java.util.Map;

/**
 * Created by tom on 2015-09-06.
 */
public interface StockDataService
{
    StockData loadStockNameByCode(String code);

    List<StockData> loadAllStockNames();

    Map<String, String> loadAllStockNamesInMap();

    void saveStockName(StockData stockData);

    void saveOrUpdateStockName(StockData stockData);

    StockData saveStockNameByData(String code, String name);

    void saveAllStockNames(Map<String, String> stockNames);

    void setStockNameDao(StockNameDao userDao);

}
