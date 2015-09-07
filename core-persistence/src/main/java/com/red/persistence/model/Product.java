package com.red.persistence.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by tom on 2015-09-07.
 */
public class Product implements Serializable
{
    private Long id;

    @NotNull
    private StockName stockName;
    @NotNull
    private BigDecimal pricePerUnit;
    @NotNull
    private Integer unitNo;

    public Product()
    {}

    public Product(StockName stockName, Integer unitNo, BigDecimal pricePerUnit)
    {
        this.stockName = stockName;
        this.unitNo = unitNo;
        this.pricePerUnit = pricePerUnit;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public StockName getStockName()
    {
        return stockName;
    }

    public void setStockName(StockName stockName)
    {
        this.stockName = stockName;
    }

    public BigDecimal getPricePerUnit()
    {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit)
    {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getUnitNo()
    {
        return unitNo;
    }

    public void setUnitNo(Integer unitNo)
    {
        this.unitNo = unitNo;
    }
}
