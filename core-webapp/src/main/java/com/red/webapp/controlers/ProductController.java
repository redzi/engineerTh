package com.red.webapp.controlers;

import com.red.persistence.model.Product;
import com.red.persistence.model.StockName;
import com.red.persistence.service.StockNameService;
import com.red.webapp.api.product.Cart;
import com.red.webapp.api.product.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 2015-09-07.
 */
@Controller
public class ProductController
{
    @Autowired
    private Cart cart;
    @Autowired
    private StockNameService stockNameService;

    @RequestMapping(value="/data/product/{product}", method = RequestMethod.GET)
    public ModelAndView setCart(Model model, HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();

        BigDecimal price = (BigDecimal) request.getSession().getAttribute("pricePerUnit");
        String stock = (String) request.getSession().getAttribute("productCode");

        mav.addObject("pricePerUnit", price);
        mav.addObject("productCode", stock);

        mav.setViewName("data/product");

        return mav;
    }

    @RequestMapping(value = "/data/product/addProduct", method = RequestMethod.POST)
    public String addProduct(String stockName, String unitNo, HttpServletRequest request)
    {
        Product product = new Product();

        BigDecimal price = (BigDecimal) request.getSession().getAttribute("pricePerUnit");

        StockName stockCode =  stockNameService.loadStockNameByCode(stockName);
        if(stockCode == null)
        {
            throw new RuntimeException("there is no such StockName: " + stockName);
        }

        product.setPricePerUnit(price);
        product.setStockName(stockCode);
        product.setUnitNo(Integer.parseInt(unitNo));

        List<Product> cart = this.cart.getProducts();
        cart.add(product);
        return "redirect:/data/stock";
    }

    @RequestMapping(value="/data/product/cart", method = RequestMethod.GET)
    public String viewCartContent(Model model, HttpServletRequest request)
    {
        if( !cart.isEmpty())
        {
            List<Product> products = cart.getProducts();
            ProductModel combinedData = calculateTotals(products, model);

            model.addAttribute("cartData", combinedData);
        }

        return ("data/cart");
    }

    @RequestMapping(value="/data/product/cart/{id}", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable("id") String id, Model model, HttpServletRequest request)
    {
        List<Product> products = cart.getProducts();
        if( !products.isEmpty())
        {
            products.remove( Integer.parseInt(id) - 1);
            if( !products.isEmpty())
            {
                ProductModel combinedData = calculateTotals(products, model);

                model.addAttribute("cartData", combinedData);
            }
        }

        return ("data/cart");
    }

    private ProductModel calculateTotals(List<Product> products, Model model)
    {
        List<BigDecimal> prices = new ArrayList<>();

        products.stream()
                .forEach( p -> {
                    BigDecimal total = p.getPricePerUnit().multiply(new BigDecimal(p.getUnitNo()));
                    prices.add(total);
                });

        BigDecimal total = prices.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalPrice", total.toString());

        return new ProductModel(products);
    }
}
