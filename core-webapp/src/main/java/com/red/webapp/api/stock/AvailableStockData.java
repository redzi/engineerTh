package com.red.webapp.api.stock;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tom on 2015-08-25.
 */
public class AvailableStockData
{
    private Map<String, String> availableStockData;

    public AvailableStockData()
    {
        availableStockData = new TreeMap<>();
    }

    public void processData(String data)
    {
        JSONObject stockData = new JSONObject(data);
        JSONArray dataArray = stockData.getJSONArray("docs");
        for(int i = 0; i < dataArray.length(); i++)
        {
            JSONObject obj = dataArray.getJSONObject(i);
            String name = extractName(obj.getString("name"), obj.getString("code"));

            availableStockData.put(obj.getString("code"), name);
        }
    }


    public Map<String, String> getAvailableStockData()
    {
        return availableStockData;
    }

    public void setAvailableStockData(Map<String, String> availableStockData)
    {
        this.availableStockData = availableStockData;
    }

    private String extractName(String name, String code)
    {
        String extractedName = name;
        String search = "("+code+")";
        if(name.contains(code))
        {
            extractedName = name.substring(0, name.indexOf(search));
        }
        if(extractedName.equals(""))
        {
            extractedName = code;
        }
        return extractedName;
    }
}
