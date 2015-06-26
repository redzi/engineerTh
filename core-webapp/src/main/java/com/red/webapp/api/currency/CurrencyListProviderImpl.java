package com.red.webapp.api.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tom on 2015-06-14.
 */
public class CurrencyListProviderImpl implements CurrencyListProvider
{
    private static final String endpoint = "http://jsonrates.com/currencies.json";

    public Map<String, String> getCurrencyList()
    {
        Map<String,String> availableCurrencies;

        try
        {
            URL url = new URL(endpoint);
            String data = IOUtils.toString(url);
            ObjectMapper mapper = new ObjectMapper();
            availableCurrencies = mapper.readValue(data,
                    new TypeReference<HashMap<String,String>>(){});
        }
        catch(IOException ex)
        {
            return new HashMap<String, String>();
        }

        Map<String, String> availableSortedCurrencies = new TreeMap<>(availableCurrencies);

        return availableSortedCurrencies;
    }
}
