package com.red.persistence.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tom on 2015-09-07.
 */
public class Product implements Serializable
{
    private Long id;

    @NotNull
    private StockData stockData;
    @NotNull
    private BigDecimal pricePerUnit;
    @NotNull
    private Integer unitNo;
    private Date purchaseDate;
    private Wallet wallet;

    public Product()
    {}

    public Product(StockData stockData, Integer unitNo, BigDecimal pricePerUnit, Wallet wallet)
    {
        this.stockData = stockData;
        this.unitNo = unitNo;
        this.pricePerUnit = pricePerUnit;
        this.wallet = wallet;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public StockData getStockData()
    {
        return stockData;
    }

    public void setStockData(StockData stockData)
    {
        this.stockData = stockData;
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

    public Wallet getWallet()
    {
        return wallet;
    }

    public void setWallet(Wallet wallet)
    {
        this.wallet = wallet;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (pricePerUnit != null ? !pricePerUnit.equals(product.pricePerUnit) : product.pricePerUnit != null)
            return false;
        if (stockData != null ? !stockData.equals(product.stockData) : product.stockData != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = stockData != null ? stockData.hashCode() : 0;
        result = 31 * result + (pricePerUnit != null ? pricePerUnit.hashCode() : 0);
        return result;
    }
}
