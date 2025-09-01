package com.needded.main.Configuration;

import com.needded.main.Security.CustomAuthenticationProvider;
import com.needded.main.Security.MainJwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class responsible to give Authorizations, provide an authenticator and a passwordEncoder to Spring.
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final MainJwtAuthFilter jwtAuthFilter;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfiguration(MainJwtAuthFilter jwtAuthFilter, CustomAuthenticationProvider customAuthenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    //This method filters every step of spring security.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                //Url addresses allowed by Spring Security. The rest will require authentication.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().authenticated()
                )
                //No shared data between sessions.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //User authentication
                .authenticationProvider(customAuthenticationProvider)
                //JWT Token generation
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

