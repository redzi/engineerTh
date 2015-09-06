package com.red.webapp.api.stock;

import com.red.persistence.service.StockNameService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tom on 2015-08-25.
 */
public class StockListProviderImpl implements StockListProvider
{
    private static final String endpoint = "https://www.quandl.com/api/v2/datasets.json?query=*&source_code=WIKI";
    private static final String API_KEY = "kMprXsYZhL2iYpUtpkYS";

    private StockNameService stockNameService;

    //TODO maybe implement a cache based on guava
    AvailableStockData availableStockData = new AvailableStockData();

    public Map<String, String> getStockListData()
    {
        if(stockNameService.loadAllStockNames().isEmpty())
        {
            try
            {
                for (int i = 1; i <= 12; i++)
                {
                    URL url = new URL(getEndpoint(i));
                    String data = IOUtils.toString(url);

                    availableStockData.processData(data);
                }
                stockNameService.saveAllStockNames(availableStockData.getAvailableStockData());
                return availableStockData.getAvailableStockData();
            }
            catch (IOException ex)
            {
                //TODO replace println with log
                System.out.println("StockListProviderImpl - A problem occured!");
                return new HashMap<>();
            }
        }
        return stockNameService.loadAllStockNamesInMap();
    }

    private String getEndpoint(int page)
    {
        return endpoint + "&page=" + page + "&auth_token=" + API_KEY;
    }

    @Required
    public void setStockNameService(StockNameService stockNameService)
    {
        this.stockNameService = stockNameService;
    }
}
