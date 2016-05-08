package com.red.webapp.api.product.model;

import com.red.persistence.model.Product;

import java.util.List;

/**
 * Created by tom on 2016-04-11.
 */
public class ProductModel
{
    private List<Product> products;

    public ProductModel(List<Product> products)
    {
        this.products = products;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }
}
