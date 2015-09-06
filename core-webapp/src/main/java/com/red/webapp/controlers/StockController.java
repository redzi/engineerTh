package com.red.webapp.controlers;

import com.red.webapp.api.stock.StockListProvider;
import com.red.webapp.api.stock.StockQuotaProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by tom on 2015-08-25.
 */

@Controller
public class StockController
{
    @Autowired
    StockQuotaProvider stockQuotaProvider;
    @Autowired
    StockListProvider stockListProvider;

    @RequestMapping(value="/data/stock", method = RequestMethod.GET)
    public String entryStockPage(Model model)
    {
        Map<String, String> availableStocks = stockListProvider.getStockListData();
        model.addAttribute("availableStocksMap", availableStocks);

        return ("data/stocks");
    }

    @RequestMapping(value="/data/stock/{stock}", method=RequestMethod.GET)
    public @ResponseBody String getStockQuota( @PathVariable("stock") String stock)
    {
        JSONObject data = new JSONObject(stockQuotaProvider.getLatestQuota(stock));
        data.put("change", stockQuotaProvider.getPercentChange(stock));

        return data.toString();
    }

    @RequestMapping(value = "/data/stock/history", method = RequestMethod.POST)
    public @ResponseBody String saveUser(@RequestParam Map<String,String> requestParams)
    {
        String baseCurrency = requestParams.get("stock");
        String compareToCurrency = requestParams.get("stock");

        return null;
    }





}
