package com.red.webapp.controlers;

import com.red.webapp.api.currency.CurrencyListProvider;
import com.red.webapp.api.currency.CurrencyRateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by tom on 2015-06-14.
 */

@Controller
public class CurrencyController
{
    @Autowired
    CurrencyRateProvider currencyRateProvider;
    @Autowired
    CurrencyListProvider currencyListProvider;

    @RequestMapping(value="/data/currencies", method = RequestMethod.GET)
    public String entryCurrencyPage(Model model)
    {
        Map<String, String> availableCurrencies = currencyListProvider.getCurrencyList();
        model.addAttribute("availableCurrenciesMap", availableCurrencies);

        return ("data/currencies");
    }

    @RequestMapping(value="/data/currencies/{currency}", method=RequestMethod.GET)
    public @ResponseBody String getCurrencyExchangeRates( @PathVariable("currency") String currency )
    {
        return currencyRateProvider.getCurrencyRatesJson(currency);
    }

    @RequestMapping(value = "/data/currencies/history", method = RequestMethod.POST)
    public @ResponseBody String saveUser(@RequestParam Map<String,String> requestParams)
    {
        String baseCurrency = requestParams.get("baseCurrency");
        String compareToCurrency = requestParams.get("compareToCurrency");

        return currencyRateProvider.getHistoricalCurrencyRates(baseCurrency, compareToCurrency);
    }

}
