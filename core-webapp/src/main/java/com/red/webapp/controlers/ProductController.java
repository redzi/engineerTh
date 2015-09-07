package com.red.webapp.controlers;

import com.red.persistence.model.Product;
import com.red.persistence.model.StockName;
import com.red.persistence.service.StockNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private static final String CART = "cart";

    @Autowired
    StockNameService stockNameService;

    @RequestMapping(value="/data/product/{product}", method = RequestMethod.GET)
    public ModelAndView setCart(Model model, HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        request.getSession();
        if( request.getSession().getAttribute(CART) == null)
        {
            request.getSession().setAttribute(CART, new ArrayList<Product>());
        }
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

        List<Product> cart = (List<Product>) request.getSession().getAttribute(CART);
        cart.add(product);
        return "redirect:/data/stock";
    }

    @RequestMapping(value="/data/product/cart", method = RequestMethod.GET)
    public String viewCartContent(Model model, HttpServletRequest request)
    {
        List<Product> products = (List<Product>) request.getSession().getAttribute(CART);
        if( request.getSession().getAttribute(CART) != null)
        {
            model.addAttribute(CART, request.getSession().getAttribute(CART));
            List<String> totals = calculateTotals(products, model);
            model.addAttribute("totals", totals);
        }

        return ("data/cart");
    }

    private List<String> calculateTotals(List<Product> products, Model model)
    {
        List<BigDecimal> totals = new ArrayList<>();
        List<String> totalsString = new ArrayList<>();
        for(Product product : products)
        {
            BigDecimal total = product.getPricePerUnit().multiply(new BigDecimal(product.getUnitNo()));
            totals.add(total);
        }
        BigDecimal total = new BigDecimal(0);
        for(BigDecimal bigDecimal : totals)
        {
            total = total.add(bigDecimal);
        }
        model.addAttribute("totalPrice", total.toString());
        for(BigDecimal bigDecimal : totals)
        {
            totalsString.add(bigDecimal.toString());
        }
        return totalsString;
    }

}
