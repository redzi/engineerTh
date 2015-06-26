package com.red.webapp.api.currency;

import com.red.webapp.json.CurrencyRate;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

public class CurrencyRateProviderImpl implements CurrencyRateProvider
{
    private static final String API_KEY = "jr-9f2441c653fff10ca4f199636e01a72c";
    private static final String endpoint = "http://jsonrates.com/get/";
    private static final String historyEndpoint = "http://jsonrates.com/historical/";

    public CurrencyRate getCurrencyRates(String baseCurrency)
    {
        String url = getEndPoint(baseCurrency);
        // Set the Accept header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new CurrencyRateHttpMessageConverter());

        // Make the HTTP GET request, marshaling the response from JSON to an array of Events
        ResponseEntity<CurrencyRate> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CurrencyRate.class);

        if(requestEntity.hasBody())
        {
            return responseEntity.getBody();
        }
        return new CurrencyRate();
    }

    public String getCurrencyRatesJson(String baseCurrency)
    {
        String data = "";
        String targetEndPoint = getEndPoint(baseCurrency);

        try
        {
            URL url = new URL(targetEndPoint);
            data = IOUtils.toString(url);
        }
        catch(IOException ex)
        {
            return "";
        }
        return data;
    }

    private String getEndPoint(String baseCurrency)
    {
        return endpoint + "?" + "base=" + baseCurrency + "&" + "apiKey=" + API_KEY;
    }

    public String getHistoricalCurrencyRates(String baseCurrency, String compareToCurrency)
    {
        String data = "";
        String targetEndPoint = getHistoryEndPoint(baseCurrency, compareToCurrency);

        try
        {
            URL url = new URL(targetEndPoint);
            data = IOUtils.toString(url);
        }
        catch(IOException ex)
        {
            return "";
        }
        return data;
    }

    private String getHistoryEndPoint(String baseCurrency, String compareToCurrency)
    {
        return historyEndpoint + "?from=" +  baseCurrency + "&to=" + compareToCurrency + "&dateStart=" +"2015-06-01" + "&dateEnd=" + "2015-06-20" + "&apiKey=" + API_KEY;
    }
}
