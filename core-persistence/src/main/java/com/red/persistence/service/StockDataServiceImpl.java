package com.red.persistence.service;

import com.red.persistence.dao.StockNameDao;
import com.red.persistence.model.StockData;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tom on 2015-09-06.
 */
public class StockDataServiceImpl implements StockDataService
{

    private StockNameDao stockNameDao;


    public StockData loadStockNameByCode(String code)
    {
        return stockNameDao.loadStockNameByCode(code);
    }

    public List<StockData> loadAllStockNames()
    {
        return stockNameDao.loadAllStockNames();
    }

    public Map<String, String> loadAllStockNamesInMap()
    {
        Map<String, String> stockNameMap = new TreeMap<>();

        for(StockData stockData : stockNameDao.loadAllStockNames())
        {
            stockNameMap.put(stockData.getCode(), stockData.getName());
        }
        return stockNameMap;
    }

    public void saveStockName(StockData stockData)
    {
        stockNameDao.saveStockName(stockData);
    }

    public void saveOrUpdateStockName(StockData stockData)
    {
        stockNameDao.saveOrUpdateStockName(stockData);
    }

    public StockData saveStockNameByData(String code, String name)
    {
        return stockNameDao.saveStockNameByData(code, name);
    }

    public void saveAllStockNames(Map<String, String> stockNames)
    {
        stockNameDao.saveAllStockNames(stockNames);
    }

    public void setStockNameDao(StockNameDao stockNameDao)
    {
        this.stockNameDao = stockNameDao;
    }
}
