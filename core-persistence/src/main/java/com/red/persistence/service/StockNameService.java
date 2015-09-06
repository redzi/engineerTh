package com.red.persistence.service;

import com.red.persistence.dao.StockNameDao;
import com.red.persistence.model.StockName;

import java.util.List;
import java.util.Map;

/**
 * Created by tom on 2015-09-06.
 */
public interface StockNameService
{
    StockName loadStockNameByCode(String code);

    List<StockName> loadAllStockNames();

    Map<String, String> loadAllStockNamesInMap();

    void saveStockName(StockName stockName);

    void saveOrUpdateStockName(StockName stockName);

    StockName saveStockNameByData(String code, String name);

    void saveAllStockNames(Map<String, String> stockNames);

    void setStockNameDao(StockNameDao userDao);

}
