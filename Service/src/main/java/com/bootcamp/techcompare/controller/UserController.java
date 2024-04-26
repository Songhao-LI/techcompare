package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.User;
import com.bootcamp.techcompare.model.UserLoginRequest;
import com.bootcamp.techcompare.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }

    /*
    NOTE:
    This class is not actually used.
    Below API endpoints are only used in custom login and registration.
    We are using AWS Cognito hosted UI instead of custom login and registration.
     */

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User userRequest) {
//        try {
//            userService.registerUser(userRequest); // Register user with Cognito
//            return ResponseEntity.ok("User registered successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userRequest) {
//        if (userService.loginUser(userRequest)) { // Login user with Cognito
//            return ResponseEntity.ok("User logged in successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid username or password.");
//        }
//    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logoutUser() {
//        userService.logout(); // Logout user
//        return ResponseEntity.ok("User logged out successfully.");
//    }

}
