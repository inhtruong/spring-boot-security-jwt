package com.ait.demospringsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class LoginController {
    @GetMapping(value = {"/", "/welcome"})
    public ModelAndView getLoginForm() {
        ModelAndView modelAndView = new ModelAndView("welcomePage");
        modelAndView.addObject("title", "Welcome");
        modelAndView.addObject("message", "This is welcome page!");
        return modelAndView;
    }
    

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("loginPage");
    }
   
    

//    @GetMapping("/home")
//    public ModelAndView getHome() {
//        return new ModelAndView("userInfoPage");
//    }

}
