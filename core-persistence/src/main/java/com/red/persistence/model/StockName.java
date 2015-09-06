package com.red.persistence.model;

/**
 * Created by tom on 2015-09-06.
 */
public class StockName
{
    private Long id;
    private String code;
    private String name;

    public StockName()
    {
    }

    public StockName(String code, String name)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockName stockName = (StockName) o;

        if (code != null ? !code.equals(stockName.code) : stockName.code != null) return false;
        if (id != null ? !id.equals(stockName.id) : stockName.id != null) return false;
        if (name != null ? !name.equals(stockName.name) : stockName.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "StockName{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
