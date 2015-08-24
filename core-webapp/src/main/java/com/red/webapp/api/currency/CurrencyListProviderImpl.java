package com.red.webapp.api.currency;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tom on 2015-06-14.
 */
public class CurrencyListProviderImpl implements CurrencyListProvider<String, String>
{
    private static final String endpoint = "http://api.fixer.io/latest";

    public Map<String, String> getCurrencyList()
    {
        AvailableCurrencies availableCurrencies;

        try
        {
            URL url = new URL(endpoint);
            String data = IOUtils.toString(url);

            availableCurrencies = new AvailableCurrencies(getCurrenciesFromJson(data));
        }
        catch(IOException ex)
        {
            //TODO replace println with log
            System.out.println("CurrencyListProviderImpl - A problem occured!");
            return new HashMap<>();
        }

        return availableCurrencies.getAvailableCurrencies();
    }

    private String[] getCurrenciesFromJson(String data)
    {
        JSONObject obj = new JSONObject(data);
        JSONObject rates = obj.getJSONObject("rates");

        return rates.getNames(rates);
     }
}
