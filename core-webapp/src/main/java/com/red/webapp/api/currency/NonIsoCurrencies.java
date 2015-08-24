package com.red.webapp.api.currency;

/**
 * Created by tom on 2015-07-05.
 */
public enum NonIsoCurrencies
{
    BTC("Bitcoin"),
    JEP("Jersey Pound"),
    XCP("Counterparty"),
    VAL("Vatican City Lira"),
    SML("Local Currency Payment System"),
    CNH("Offshore Yuan"),
    GGP("Guernsey Pound");

    private final String description;

    private NonIsoCurrencies(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
