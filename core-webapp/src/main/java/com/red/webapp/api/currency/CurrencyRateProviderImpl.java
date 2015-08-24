package com.red.webapp.api.currency;

import com.red.webapp.json.CurrencyRate;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CurrencyRateProviderImpl implements CurrencyRateProvider
{
    private static final String API_KEY = "53df5e3c2150913ac20e5ae923683378";
    private static final String endpoint = "http://api.fixer.io/latest";
    private static final String historyEndpoint = "http://currencies.apps.grandtrunk.net/getrange/";

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
        return endpoint + "?" + "base=" + baseCurrency; //+ "&" + "access_key=" + API_KEY;
    }

    public String getHistoricalCurrencyRates(String baseCurrency, String compareToCurrency)
    {
        String data = "";
        String targetEndPoint = getHistoryEndPoint(baseCurrency, compareToCurrency);

        JSONObject quotes = new JSONObject();

        try
        {
            URL url = new URL(targetEndPoint);
            data = IOUtils.toString(url);
            String[] historicalRates = data.split("\\r?\\n");
            for(int i = 0; i < historicalRates.length; i++)
            {
                String line = historicalRates[i];
                String date = line.substring(0, line.indexOf(" "));
                String rate = line.substring(line.indexOf(" ")+1);
                JSONObject rates = new JSONObject();
                rates.put(date, rate);
                quotes.put(String.valueOf(i), rates);
            }
        }
        catch(IOException ex)
        {
            return "";
        }
        return quotes.toString();
    }

    private String getHistoryEndPoint(String baseCurrency, String compareToCurrency)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date nowDate = cal.getTime();
        cal.add(Calendar.MONTH, -1);
        Date monthAgoDate = cal.getTime();

        String now = df.format(nowDate);
        String monthAgo = df.format(monthAgoDate);

        return historyEndpoint + monthAgo + "/" + now + "/" + baseCurrency + "/" + compareToCurrency;
    }
}
