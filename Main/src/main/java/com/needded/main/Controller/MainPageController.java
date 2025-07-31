package com.needded.main.Controller;

import com.needded.main.Entity.User;
import com.needded.main.Service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

    private final CustomUserDetailsService customUserDetailsService;

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
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/auth/registerForm")
    public String registerForm(@ModelAttribute User user) {
        customUserDetailsService.createUser(user);
        return "redirect:/auth/login";
    }
}
