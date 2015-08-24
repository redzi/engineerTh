package com.red.webapp.api.currency;

import java.util.Map;

/**
 * Created by tom on 2015-06-14.
 */
public interface CurrencyListProvider<K, V>
{
    Map<K, V> getCurrencyList();
}
