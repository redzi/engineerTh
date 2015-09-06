package com.red.webapp.api.stock;

import org.json.JSONObject;

/**
 * Created by tom on 2015-08-25.
 */
public interface StockQuotaProvider
{
    String getLatestQuota(String stock);
    JSONObject getPercentChange(String stock);

}
