package com.red.webapp.controlers;

import com.red.persistence.exception.UsernameAlreadyExistException;
import com.red.persistence.model.Email;
import com.red.persistence.model.User;
import com.red.persistence.service.LoginAttemptBlocker;
import com.red.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private LoginAttemptBlocker loginAttemptBlocker;
    @Autowired
    private JavaMailSender mailSender;

    private final static String MAIL_FROM = "redzi44@gmail.com";

    public UserController()
    {}

    @RequestMapping(value="/users/{username}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Boolean getUsersById( @PathVariable("username") String username )
    {
        User user = userService.loadUserByName(username);
        return user != null;
    }

    @RequestMapping(value="authentication/registration", method = RequestMethod.GET)
    public String registrationGet(Model model)
    {
        User user = new User();
        model.addAttribute(user);
        return ("login/registrationPage");
    }

    @RequestMapping(value="authentication/registration", method = RequestMethod.POST)
    public String registrationPost(Model model,  @ModelAttribute(value="user") @Valid User newUser, BindingResult result, WebRequest request)
    {
        if(result.hasErrors())
        {
            model.addAttribute("message", "some errors with user");
            return "login/registrationPage";
        }
        try
        {
            User registered = userService.saveUserRole(newUser.getName(), newUser.getPassword(), newUser.getEmail().getAddress());
        }
        catch(UsernameAlreadyExistException ex)
        {
            result.rejectValue("name", "err_qna_not_blank", "Username is already taken");
            return "login/registrationPage";
        }

        model.addAttribute("user", newUser.getName());

        if(authenticate(newUser, newUser.getPassword()))
        {
            return "redirect:../";
        }

        return "redirect:login/loginPage";

    }

    @RequestMapping(value="authentication/login", method = RequestMethod.GET)
    public String loginGet(Model model)
    {
        return ("login/loginPage");
    }

    @RequestMapping(value="authentication/login", method = RequestMethod.POST)
    public String loginPost(Model model, String username, String password, HttpServletRequest request)
    {
        User user = userService.loadUserByName(username);
        if(user == null)
        {
            loginAttemptBlocker.loginFailed(request.getRemoteAddr());
            model.addAttribute("failure", "No such user!");
            return ("login/loginPage");
        }

        try
        {
            authenticate(user, password);
        }
        catch(BadCredentialsException ex)
        {
            loginAttemptBlocker.loginFailed(request.getRemoteAddr());
            model.addAttribute("failure", "Please provide valid password!");
            return "login/loginPage";
        }
        if(loginAttemptBlocker.isBlocked(request.getRemoteAddr()))
        {
            model.addAttribute("failure", "This account is blocked!");
            return "login/loginPage";
        }
        loginAttemptBlocker.loginSuccessful(request.getRemoteAddr());
        return "redirect:../";
    }

    @RequestMapping(value="authentication/logout", method = RequestMethod.GET)
    public String logoutGet(Model model, Principal principal)
    {
        String userName = principal.getName();
        model.addAttribute("user", userName);
        model.addAttribute("message", "Hi " + userName + ", would you like to log out?'");
        return ("login/logoutPage");
    }

    @RequestMapping(value="authentication/changePassword", method = RequestMethod.GET)
    public String changePasswordGet(Model model, Principal principal)
    {
        String userName = principal.getName();
        model.addAttribute("userName", userName);

        model.addAttribute("message", "Hi " + userName + ", please fill in the form to change your password");
        return ("login/changePasswordPage");
    }

    @RequestMapping(value="authentication/changePassword", method = RequestMethod.POST)
    public String changePasswordPost(Model model, String password,  String changedPassword, String passwordConfirmation, WebRequest request, Principal principal)
    {
        User user = userService.loadUserByName(principal.getName());
        if(!encoder.matches(password, user.getPassword()))
        {
            model.addAttribute("message", "please provide your valid password!");
            return "login/changePasswordPage";
        }

        if(!checkPasswordMatch(changedPassword, passwordConfirmation))
        {
            model.addAttribute("message", "password don't match - please try again");
            return "login/changePasswordPage";
        }

        if(changedPassword.length() < 6)
        {
            model.addAttribute("message", "new password too short - please provide a password at least 6 characters long");
            return "login/changePasswordPage";
        }

        user.setPassword(encoder.encode(changedPassword));
        userService.updateUser(user);

        return "redirect:../";
    }

    @RequestMapping(value="authentication/passwordReset", method = RequestMethod.GET)
    public String passwordResetGet(Model model)
    {
        return ("login/passwordResetPage");
    }

    @RequestMapping(value="authentication/passwordReset", method = RequestMethod.POST)
    public String passwordResetPost(Model model, String username, String emailAddress, HttpServletRequest request)
    {
        User user = userService.loadUserByName(username);
        if(user == null)
        {
            model.addAttribute("failure", "no such user!");
            return "login/passwordResetPage";
        }
        Email mail = user.getEmail();

        if(mail == null || !mail.getAddress().equals(emailAddress) )
        {
            model.addAttribute("failure", "Please provide your registred email address!");
            return "login/passwordResetPage";
        }

        String generatedPassword = "testtest";

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        final SimpleMailMessage email = constructResetTokenEmail(user, generatedPassword, appUrl);
        mailSender.send(email);

        user.setPassword(encoder.encode(generatedPassword));
        userService.updateUser(user);

        return "redirect:login";
    }



    //TODO refractor it out to other class
    private boolean authenticate(User newUser, String password)
    {
        // perform login authentication
        UserDetails userDetails = userService.loadUserByUsername(newUser.getName());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(auth);
        if(auth.isAuthenticated())
        {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return true;
        }
        return false;
    }

    private boolean checkPasswordMatch(String password, String confirmation)
    {
        return password.equals(confirmation);
    }

    private SimpleMailMessage constructResetTokenEmail(User user, String password, String appUrl)
    {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail().getAddress());
        email.setSubject("Reset Password");
        email.setText("Hi " + user.getName()+ "! \r\nYour temporary password is: " + password +" \r\nBye!");
        email.setFrom(MAIL_FROM);
        return email;
    }
}
