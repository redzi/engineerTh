package com.red.webapp.api.currency;

import java.util.*;

/**
 * Created by tom on 2015-07-05.
 */
public class AvailableCurrencies
{
    private static final Map<String, String> NON_ISO_CURRENCIES = new HashMap<>();

    private Map<String, String> availableCurrencies;

    public AvailableCurrencies(String[] currencies)
    {
        initNonIsoCurrencies();
        availableCurrencies = new TreeMap<>();
        generateAvailableCurrenciesMap(currencies);
    }

    private void initNonIsoCurrencies()
    {
        for (NonIsoCurrencies nonIsoCurrency : NonIsoCurrencies.values())
        {
            NON_ISO_CURRENCIES.put(nonIsoCurrency.name(), nonIsoCurrency.getDescription());
        }
    }

    private void generateAvailableCurrenciesMap(String[] currencies)
    {
        try
        {
            giveDescription(currencies);

        }
        catch (Exception ex)
        {
            //TODO replace println with log
            System.out.println("CurrencyList - A problem occured: " );
            ex.printStackTrace();
            availableCurrencies = new HashMap<>();
        }
    }

    private void giveDescription(String[] currencies)
    {
        Set<String> nonIsoCurrenciesSet = NON_ISO_CURRENCIES.keySet();
        //String lines[] = currencies.split("\\r?\\n");
        Set<String> currenciesSet = new HashSet<>(Arrays.asList(currencies));
        for(String currencyName : currenciesSet)
        {
            System.out.println(currencyName);

            if(nonIsoCurrenciesSet.contains(currencyName))
            {
                availableCurrencies.put(currencyName, NON_ISO_CURRENCIES.get(currencyName));
                continue;
            }
            getDescriptionFromUtilCurrency(currencyName);
        }

    }

    private void getDescriptionFromUtilCurrency(String currencyName)
    {
        try
        {
            Currency currency;
            currency = Currency.getInstance(currencyName);
            String fullName = currency.getDisplayName();
            availableCurrencies.put(currencyName, fullName);
        }
        catch(Exception exc)
        {
            System.out.println("CurrencyList: " + currencyName + " is not a ISO 4217 currency");
        }
    }

    public Map<String, String> getAvailableCurrencies()
    {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(Map<String, String> availableCurrencies)
    {
        this.availableCurrencies = availableCurrencies;
    }
}
