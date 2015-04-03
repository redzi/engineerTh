package com.red.webapp.controlers;

import com.red.persistence.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value="/list")
public class UserController
{
    private Set<User> userSet;

    public UserController()
    {
        userSet = new HashSet<>();
        System.out.println("controller is being initialized");
        userSet.add(new User("Tomek", "Kuku"));
        userSet.add(new User("Kasia", "Cicha"));
        userSet.add(new User("Bobek", "Kot"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listGet(Model model)
    {
        model.addAttribute("users", userSet);
        return ("login/index");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String listPost(Model model, User user)
    {
        userSet.add(user);
        model.addAttribute("users", userSet);
        return ("login/index");
    }
}
