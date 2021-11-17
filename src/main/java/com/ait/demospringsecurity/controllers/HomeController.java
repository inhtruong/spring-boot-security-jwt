package com.ait.demospringsecurity.controllers;

import com.ait.demospringsecurity.models.Student;
import com.ait.demospringsecurity.models.User;
import com.ait.demospringsecurity.services.student.IStudentService;
import com.ait.demospringsecurity.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }

        return userName;
    }
    
    private Optional<User> getUser() {
    	Optional<User> userOptional = userService.findByUsername(getPrincipal());
    	return userOptional;
    }

    private Optional<Student> getStudent() {
        Optional<Student> studentOptional = studentService.findByUsername(getPrincipal());
        return studentOptional;
    }

    @GetMapping("/home")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfoPage");

//        Optional<User> userOptional = getUser();
//
//        if (userOptional.isPresent()) {
//            modelAndView.addObject("user", userOptional.get());
//        } else {
//            Optional<Student> studentOptional = getStudent();
//            modelAndView.addObject("user", studentOptional.get());
//        }

        return modelAndView;
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAdmin() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("adminPage");
    	
    	Optional<User> userOptional = getUser();
    	modelAndView.addObject("user", userOptional.get());
    	modelAndView.addObject("title", "Admin Page");
    	return modelAndView;
    }
    
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView getMod() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("modPage");
    	
    	Optional<User> userOptional = getUser();
    	modelAndView.addObject("user", userOptional.get());
    	modelAndView.addObject("title", "Mod Page");
    	return modelAndView;
    }
    
    @GetMapping("/403")
    public ModelAndView get403() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("403Page");
    	
    	Optional<User> userOptional = getUser();
    	modelAndView.addObject("user", userOptional.get());

        String message = "Hi " + userOptional.get().getUsername() //
                + "<br> You do not have permission to access this page!";
        modelAndView.addObject("message", message);

    	return modelAndView;
    }
}
