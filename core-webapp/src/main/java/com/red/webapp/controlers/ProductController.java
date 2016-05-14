package com.red.webapp.controlers;

import com.red.persistence.model.Product;
import com.red.persistence.model.StockData;
import com.red.persistence.model.User;
import com.red.persistence.model.Wallet;
import com.red.persistence.service.StockDataService;
import com.red.persistence.service.UserService;
import com.red.webapp.api.product.Cart;
import com.red.webapp.api.product.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by tom on 2015-09-07.
 */
@Controller
public class ProductController
{
    @Autowired
    private Cart cart;
    @Autowired
    private StockDataService stockDataService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/data/product/{product}", method = RequestMethod.GET)
    public ModelAndView setCart(Model model, HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.loadUserByName(name);
        Wallet wallet = user.getWallet();
        // TODO this should go
        if(wallet == null)
        {
            wallet = new Wallet();
            wallet.setUser(user);
            user.setWallet(wallet);
            userService.saveUser(user);
        }

        cart.setUser(user);

        BigDecimal price = (BigDecimal) request.getSession().getAttribute("pricePerUnit");
        String stock = (String) request.getSession().getAttribute("productCode");
        BigDecimal balance = wallet.getBalance();

        mav.addObject("pricePerUnit", price);
        mav.addObject("productCode", stock);
        mav.addObject("balance", balance);

        mav.setViewName("data/product");

        return mav;
    }

    @RequestMapping(value = "/data/product/addProduct", method = RequestMethod.POST)
    public String addProduct(String stockName, String unitNo, HttpServletRequest request, Model model)
    {
        Product product = new Product();

        BigDecimal price = (BigDecimal) request.getSession().getAttribute("pricePerUnit");

        StockData stockCode = stockDataService.loadStockNameByCode(stockName);
        if (stockCode == null)
        {
            throw new RuntimeException("there is no such StockName: " + stockName);
        }

        product.setPricePerUnit(price);
        product.setStockData(stockCode);
        product.setUnitNo(Integer.parseInt(unitNo));

        List<Product> productsInCart = cart.getProducts();

        User user = cart.getUser();
        BigDecimal balance = user.getWallet().getBalance();

        user.getWallet().setBalance(new BigDecimal(200));
        userService.saveUser(user);

        BigDecimal balanceAfter = balance.subtract(product.getPricePerUnit().multiply(new BigDecimal(product.getUnitNo())));
        if (balanceAfter.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalStateException();
        }

        if (cart.getProducts().contains(product))
        {
            int unitNumberToAdd = product.getUnitNo();
            Product prod = cart.getProducts().stream().filter(p -> p.equals(product)).findFirst().get();
            prod.setUnitNo(prod.getUnitNo()+unitNumberToAdd);
        }
        else
        {
            cart.getProducts().add(product);
        }

        List<Product> products = cart.getProducts();
        ProductModel combinedData = calculateTotals(products, model);

        model.addAttribute("cartData", combinedData);

        return "data/cart";
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

        return "data/cart";
    }

    @RequestMapping(value="/data/product/cart/{id}", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable("id") String id, Model model, HttpServletRequest request)
    {
        List<Product> products = cart.getProducts();
        if( !products.isEmpty())
        {
            products.remove( Integer.parseInt(id) - 1);
            ProductModel combinedData = calculateTotals(products, model);
            model.addAttribute("cartData", combinedData);
        }

        return "data/cart";
    }

    @RequestMapping(value="/data/product/cart/buy", method = RequestMethod.POST)
    public String buyProducts(Model model, HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.loadUserByName(name);

        List<Product> productsInCart = cart.getProducts();
        BigDecimal balance = user.getWallet().getBalance();

        BigDecimal cost = productsInCart.stream().map(p -> {
            return p.getPricePerUnit().multiply(new BigDecimal(p.getUnitNo()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balanceAfter = balance.subtract(cost);
        if(balanceAfter.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalStateException();
        }

        user.getWallet().setBalance(balance.subtract(cost));

        productsInCart.stream().forEach(p -> {
            p.setWallet(user.getWallet());
            p.setPurchaseDate(new Date(System.currentTimeMillis()));
        });

        checkIfTheSameProduct( productsInCart, user.getWallet().getProducts());

        user.getWallet().getProducts().addAll(productsInCart);

        userService.saveUser(user);
        cart.getProducts().clear();

        return "data/currentInvestments";
    }

    @RequestMapping(value="/data/product/investments", method = RequestMethod.GET)
    public String allInvestments(Model model, HttpServletRequest request)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.loadUserByName(name);

        //...

        return "data/currentInvestments";
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

    private void checkIfTheSameProduct(List<Product> cartProducts, Set<Product> userProducts)
    {
        Set<Product> productsToRemoveFromCart = new HashSet<>();
        cartProducts.stream().forEach(p -> {
            if(userProducts.contains(p))
            {
                int unitNo = p.getUnitNo();
                productsToRemoveFromCart.add(p);
                userProducts.stream().filter(pr -> pr.equals(p)).forEach(pr -> pr.setUnitNo(pr.getUnitNo()+ unitNo));
            }
        });
        cartProducts.removeAll(productsToRemoveFromCart);
    }
}
