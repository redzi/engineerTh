package com.red.webapp.controlers;

import com.red.persistence.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/list")
public class UserController
{
    @Autowired
    private TestService testService;

    public UserController()
    {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listGet(Model model)
    {
        model.addAttribute("tests", testService.loadAllTest());
        return ("login/index");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String listPost(Model model, @RequestParam String word)
    {
        testService.saveTest(word);
        model.addAttribute("tests", testService.loadAllTest());
        return ("redirect:list");
    }

    public void setTestService(TestService testService)
    {
        this.testService = testService;
    }
}
