package com.red.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tom on 2015-09-08.
 */
public class Wallet implements Serializable
{
    private Long id;

    private Set<Product> products  = new HashSet<>();
    private User user;

    private BigDecimal balance = BigDecimal.ZERO;

    public Wallet()
    {}

    public Wallet(Set<Product> products, User user, BigDecimal balance)
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

    public Set<Product> getProducts()
    {
        return products;
    }

    public void setProducts(Set<Product> products)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wallet wallet = (Wallet) o;

        if (balance != null ? !balance.equals(wallet.balance) : wallet.balance != null) return false;
        if (products != null ? !products.equals(wallet.products) : wallet.products != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = products != null ? products.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
