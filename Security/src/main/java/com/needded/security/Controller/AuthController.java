package com.needded.security.Controller;

import com.needded.security.Entity.User;
import com.needded.security.Repository.UserRepository;
import com.needded.security.Service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder){
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register-save")
    public String registerUser(@ModelAttribute User user) {
        customUserDetailsService.createUser(user);
        return "redirect:/auth/login";
    }
}
