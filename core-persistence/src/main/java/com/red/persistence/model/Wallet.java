package com.red.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tom on 2015-09-08.
 */
public class Wallet implements Serializable
{
    private Long id;

    private List<Product> products;
    private User user;

    private BigDecimal balance;

    public Wallet()
    {}

    public Wallet(List<Product> products, User user, BigDecimal balance)
    {
        this.products = products;
        this.user = user;
        this.balance = balance;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }
}
