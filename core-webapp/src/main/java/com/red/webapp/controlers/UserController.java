package com.red.webapp.controlers;

import com.red.persistence.model.User;
import com.red.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    public UserController()
    {}

    @RequestMapping(value="authentication/registration", method = RequestMethod.GET)
    public String listGet(Model model)
    {
        User user = new User();
        model.addAttribute(user);
        return ("login/registrationPage");
    }

    @RequestMapping(value="authentication/registration", method = RequestMethod.POST)
    public String listPost(Model model,  @ModelAttribute(value="user") @Valid User newUser, BindingResult result, WebRequest request)
    {
        if(result.hasErrors())
        {
            model.addAttribute("message", "some errors with user");
            return "login/registrationPage";
        }
        User registered = userService.saveUserRole(newUser.getName(), newUser.getPassword(), newUser.getEmail().getAddress());

        if (registered == null)
        {
            result.rejectValue("user", "User is not valid");
            return "login/registrationPage";
        }

        return "/";
    }

    @RequestMapping(value="authentication/login", method = RequestMethod.GET)
    public String loginGet(Model model)
    {
        return ("login/loginPage");
    }

    @RequestMapping(value="authentication/logout", method = RequestMethod.GET)
    public String logoutGet(Model model, Principal principal)
    {
        String userName = principal.getName();
        model.addAttribute("message", "Hi " + userName + ", would you like to log out?'");
        return ("login/logoutPage");
    }

    public void setTestService(UserService userService)
    {
        this.userService = userService;
    }
}
