package com.red.webapp.controlers;

import com.red.persistence.model.User;
import com.red.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private AuthenticationManager authenticationManager;

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
            return "redirect:login/registrationPage";
        }
        User registered = userService.saveUserRole(newUser.getName(), newUser.getPassword(), newUser.getEmail().getAddress());

        if (registered == null)
        {
            result.rejectValue("name", "err_qna_not_blank", "Username is already taken");
            return "redirect:login/registrationPage";
        }
        model.addAttribute("user", newUser.getName());

        // perform login authentication
        UserDetails userDetails = userService.loadUserByUsername(newUser.getName());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails, newUser.getPassword(), userDetails.getAuthorities());
        authenticationManager.authenticate(auth);
        if(auth.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:../";
        }
        return "redirect:login/loginPage";

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
        model.addAttribute("user", userName);
        model.addAttribute("message", "Hi " + userName + ", would you like to log out?'");
        return ("login/logoutPage");
    }

}
