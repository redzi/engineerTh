package com.red.webapp.controlers;

import com.red.persistence.model.StockData;
import com.red.persistence.service.StockDataService;
import com.red.webapp.api.stock.StockCollapse;
import com.red.webapp.api.stock.StockListProvider;
import com.red.webapp.api.stock.StockQuotaProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by tom on 2015-08-25.
 */

@Controller
public class StockController
{
    @Autowired
    private StockQuotaProvider stockQuotaProvider;
    @Autowired
    private StockListProvider stockListProvider;
    @Autowired
    private StockDataService stockDataService;

    @RequestMapping(value="/data/stock", method = RequestMethod.GET)
    public String entryStockPage(Model model)
    {
        Map<String, String> availableStocks = stockListProvider.getStockListData();
        model.addAttribute("availableStocksMap", availableStocks);

        return ("data/stocks");
    }

    @RequestMapping(value="/data/stock/{stock}", method=RequestMethod.GET)
    public @ResponseBody String getStockQuota( @PathVariable("stock") String stock, HttpServletRequest request)
    {
        StockData stockData = stockDataService.loadStockNameByCode(stock);

        JSONObject data = new JSONObject(stockQuotaProvider.getLatestQuota(stock));
        JSONObject dataset = data.getJSONObject("dataset");
        JSONArray quotes = dataset.getJSONArray("data");
        JSONArray quote = quotes.getJSONArray(0);
        BigDecimal price = new BigDecimal(String.valueOf(quote.getDouble(4)));

        data.put("graph", stockQuotaProvider.getGraphData(stock, StockCollapse.MONTHLY));
        data.put("change", stockQuotaProvider.getPercentChange(stock));
        request.getSession().setAttribute("productCode", stock);
        request.getSession().setAttribute("pricePerUnit", price);

        if(stockData != null)
        {
            stockData.setLastPrice(price);
            stockData.setPriceDate(new Date(System.currentTimeMillis()));
            stockDataService.saveStockName(stockData);
        }

        return data.toString();
    }

    @RequestMapping(value = "/data/stock/history", method = RequestMethod.POST)
    public @ResponseBody String saveUser(@RequestParam Map<String,String> requestParams)
    {
        String stock = requestParams.get("stock");
        String compareToCurrency = requestParams.get("stock");

        JSONObject data = new JSONObject(stockQuotaProvider.getGraphData(stock, StockCollapse.MONTHLY));

        return data.toString();
    }

}
