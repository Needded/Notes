package com.needded.main.Service;

import com.needded.main.Entity.User;
import com.needded.main.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public MainService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void createUser(User user){

        if(usernameExists(user.getUsername())) throw new RuntimeException("Username: "+ user.getUsername()+" already exist on database.");

        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            System.out.println("User created.");
        }catch (Exception e){
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }

    }

    public boolean usernameExists(String username){
        Optional<User> user=userRepository.findByUsername(username);
        return user.isPresent();
    }
}
