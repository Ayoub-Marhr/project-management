package com.ayoub.project_management.controller;

import com.ayoub.project_management.Repository.UserRepository;
import com.ayoub.project_management.config.JwtProvider;
import com.ayoub.project_management.model.User;
import com.ayoub.project_management.request.LoginRequest;
import com.ayoub.project_management.responce.AuthResponce;
import com.ayoub.project_management.service.CustomUserServiceImpl;
import com.ayoub.project_management.service.subscription.SubscriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceImpl userService;

    @Autowired
    private SubscriptionServiceImpl subscriptionService;



    @PostMapping("/signup")
    public ResponseEntity<AuthResponce> createUserHandler(@RequestBody User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists with another account");
            }
            System.out.println("Received user: " + user);

            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());

            User savedUser = userRepository.save(newUser);

            subscriptionService.createSubscription(savedUser);
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = JwtProvider.createToken(auth);
            AuthResponce authResponce = new AuthResponce();
            authResponce.setMessage("Signup success");
            authResponce.setJwt(jwt);

            return new ResponseEntity<>(authResponce, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error during signup: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during signup");
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponce> signing(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication auth = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.createToken(auth);
        AuthResponce authResponce = new AuthResponce();
        authResponce.setMessage(" signin success");
        authResponce.setJwt(jwt);
              return new ResponseEntity<>(authResponce, HttpStatus.OK);}

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username); // Corrected variable name
        if (userDetails == null) {
            throw new BadCredentialsException("Username not found");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}




