package com.red.webapp.api.product;

import com.red.persistence.model.Product;
import com.red.persistence.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 2015-09-08.
 */

@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Cart
{
    private List<Product> products;
    private String price;
    private User user;

    public Cart()
    {
        products = new ArrayList<>();
    }

    public boolean isEmpty()
    {
        return products.isEmpty();
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
