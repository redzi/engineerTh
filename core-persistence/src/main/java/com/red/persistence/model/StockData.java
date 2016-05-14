package com.red.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tom on 2015-09-06.
 */
public class StockData implements Serializable
{
    private Long id;
    private String code;
    private String name;
    private BigDecimal lastPrice;
    private Date priceDate;

    public StockData()
    {
    }

    public StockData(String code)
    {
        this.code = code;
    }

    public StockData(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getLastPrice()
    {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice)
    {
        this.lastPrice = lastPrice;
    }

    public Date getPriceDate()
    {
        return priceDate;
    }

    public void setPriceDate(Date priceDate)
    {
        this.priceDate = priceDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockData stockData = (StockData) o;

        if (code != null ? !code.equals(stockData.code) : stockData.code != null) return false;
        if (name != null ? !name.equals(stockData.name) : stockData.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
