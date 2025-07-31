package com.needded.main.Controller;

import com.needded.main.Entity.User;
import com.needded.main.Service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

    private final CustomUserDetailsService customUserDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(MainPageController.class);

    public MainPageController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/auth/register")
    public String register(Model model) {
        logger.info("register page.");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/auth/registerForm")
    public String registerForm(@ModelAttribute User user) {
        logger.info("registering...");
        customUserDetailsService.createUser(user);
        return "redirect:/auth/login";
    }
}
