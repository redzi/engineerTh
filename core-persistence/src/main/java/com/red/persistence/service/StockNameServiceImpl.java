package com.red.persistence.service;

import com.red.persistence.dao.StockNameDao;
import com.red.persistence.model.StockName;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tom on 2015-09-06.
 */
public class StockNameServiceImpl implements StockNameService
{

    private StockNameDao stockNameDao;


    public StockName loadStockNameByCode(String code)
    {
        return stockNameDao.loadStockNameByCode(code);
    }

    public List<StockName> loadAllStockNames()
    {
        return stockNameDao.loadAllStockNames();
    }

    public Map<String, String> loadAllStockNamesInMap()
    {
        Map<String, String> stockNameMap = new TreeMap<>();

        for(StockName stockName : stockNameDao.loadAllStockNames())
        {
            stockNameMap.put(stockName.getCode(), stockName.getName());
        }
        return stockNameMap;
    }

    public void saveStockName(StockName stockName)
    {
        stockNameDao.saveStockName(stockName);
    }

    public void saveOrUpdateStockName(StockName stockName)
    {
        stockNameDao.saveOrUpdateStockName(stockName);
    }

    public StockName saveStockNameByData(String code, String name)
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
