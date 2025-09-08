package com.needded.main.Controller;

import com.needded.main.Security.MainJWTService;
import com.needded.main.Entity.UserDTO;
import com.needded.main.Entity.TokenResponse;
import com.needded.main.Entity.User;
import com.needded.main.Service.CustomUserDetailsService;
import com.needded.main.Service.MainService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainPageController {

    private static final Logger logger = LoggerFactory.getLogger(MainPageController.class);
    private final MainService mainService;
    private final MainJWTService jwtUtil;
    private final AuthenticationManager authenticationManager;

    public MainPageController(MainService mainService, MainJWTService jwtUtil, AuthenticationManager authenticationManager) {
        this.mainService = mainService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@NonNull  @RequestBody UserDTO userDTO) {
        logger.info("Logging user...");

        //Authenticate teh user. If invalid, throw exception.
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password()));

        //Create the JWT token.
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername());

        //Return 200 and the JWT token.
        return ResponseEntity.ok(new TokenResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@NonNull @RequestBody UserDTO userDTO) {
        logger.info("Registering user...");

        if (mainService.usernameExists(userDTO.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!");
        }

        //Convert DTO to User and register on Service layer.
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        mainService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User register successfully!");

    }

}
