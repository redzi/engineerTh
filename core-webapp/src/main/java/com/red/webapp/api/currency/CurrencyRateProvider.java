package com.red.webapp.api.currency;

import com.red.webapp.json.CurrencyRate;

/**
 * Created by tom on 2015-05-24.
 */
public interface CurrencyRateProvider
{
    CurrencyRate getCurrencyRates(String baseCurrency);
    String getCurrencyRatesJson(String baseCurrency);
    String getHistoricalCurrencyRates(String baseCurrency, String compareToCurrency);
}
