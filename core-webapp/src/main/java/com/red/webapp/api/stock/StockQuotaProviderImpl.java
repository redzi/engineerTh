package com.red.webapp.api.stock;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tom on 2015-08-25.
 */
public class StockQuotaProviderImpl implements StockQuotaProvider
{
    private static final String API_KEY = "kMprXsYZhL2iYpUtpkYS";
    private static final String endpoint = "https://www.quandl.com/api/v3/datasets/WIKI/";

    public String getLatestQuota(String stock)
    {
        String data = "";
        try
        {
            URL url = new URL(getEndpointForLatestQuota(stock));
            data = IOUtils.toString(url);
        }
        catch (IOException ex)
        {
            //TODO replace println with log
            System.out.println("StockQuotaProviderImpl - A problem occured!");
        }
        return data;
    }

    public JSONObject getPercentChange(String stock)
    {
        JSONObject data = new JSONObject();
        try
        {
            URL urlForPercentChange = new URL(getEndpointForPastQuota(stock, StockCollapse.DAILY));
            String daily = IOUtils.toString(urlForPercentChange);
            urlForPercentChange = new URL(getEndpointForPastQuota(stock, StockCollapse.WEEKLY));
            String weekly = IOUtils.toString(urlForPercentChange);
            urlForPercentChange = new URL(getEndpointForPastQuota(stock, StockCollapse.MONTHLY));
            String monthly = IOUtils.toString(urlForPercentChange);
            urlForPercentChange = new URL(getEndpointForPastQuota(stock, StockCollapse.ANNUAL));
            String annual = IOUtils.toString(urlForPercentChange);

            data.put(StockCollapse.DAILY.toString().toLowerCase(), daily);
            data.put(StockCollapse.WEEKLY.toString().toLowerCase(), weekly);
            data.put(StockCollapse.MONTHLY.toString().toLowerCase(), monthly);
            data.put(StockCollapse.ANNUAL.toString().toLowerCase(), annual);

        }
        catch (IOException ex)
        {
            //TODO replace println with log
            System.out.println("StockQuotaProviderImpl - A problem occured!");
        }
        return data;
    }

    public String getGraphData(String stock)
    {
        String data = "";
        try
        {
            URL url = new URL(getEndpointForLatestQuota(stock));
            data = IOUtils.toString(url);
        }
        catch (IOException ex)
        {
            //TODO replace println with log
            System.out.println("StockQuotaProviderImpl - A problem occured!");
        }
        return data;
    }

    private String getEndpointForLatestQuota(String stock)
    {
        return endpoint + stock + ".json?" +"start_date="+ getDateNotWeekend() + "&end_date=" + getDateNotWeekend() + "&api_key=" + API_KEY;
    }

    private String getEndpointForPastQuota(String stock, StockCollapse collapse)
    {
        return endpoint + stock + ".json?" +"start_date="+ getDate(collapse) + "&end_date=" + getDate(collapse) + "&api_key=" + API_KEY;
    }

    private String getEndpointForPercentageDifference(String stock, StockCollapse collapse)
    {
        return endpoint + stock + ".json?" +"start_date="+ getDate(collapse) + "&end_date=" + getDateNotWeekend() + "&collapse=" + StockCollapse.DAILY.toString().toLowerCase() + "&rows=1" + "&transform=rdiff" + "&api_key=" + API_KEY;
    }



    private String getDate(StockCollapse collapse)
    {
        DateTime time = DateTime.now();
        time = checkIfNotWeekend(time);
        switch(collapse)
        {
            case DAILY:
                time = time.minus(Period.days(1));
                break;
            case WEEKLY:
                time = time.minus(Period.weeks(1));
                break;
            case MONTHLY:
                time = time.minus(Period.months(1));
                break;
            case ANNUAL:
                time = time.minus(Period.years(1));
                break;
        }
        time = checkIfNotWeekend(time);
        return time.toString(getFormatter());
    }

    private String getDateNotWeekend()
    {
        DateTime time = DateTime.now();
        time = checkIfNotWeekend(time);
        return time.toString(getFormatter());
    }

    private DateTime checkIfNotWeekend(DateTime time)
    {
        while(time.getDayOfWeek() > 5)
        {
            time = time.minus(Period.days(1));
        }
        return time;
    }

    private DateTimeFormatter getFormatter()
    {
        return DateTimeFormat.forPattern("yyyy-MM-dd");
    }


}
