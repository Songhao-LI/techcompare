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

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User userRequest) {
        try {
            userService.registerUser(userRequest); // Register user with Cognito
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userRequest) {
        if (userService.loginUser(userRequest)) { // Login user with Cognito
            return ResponseEntity.ok("User logged in successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }

//    @Operation(
//            summary = "User login",
//            description = "Login user with username and password.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
//    @PostMapping("/login")
//    public String login(
//            @RequestParam(value = "username", required = true) String username,
//            @RequestParam(value = "password", required = true) String password,
//            HttpSession session) {
//        boolean loginSuccess = userService.login(username, password);
//        if (loginSuccess) {
////            set user id in session cookie with base64 username
////            String encodedUsername = java.util.Base64.getEncoder().encodeToString(username.getBytes());
//            session.setAttribute("username", username);
//            return "redirect:/search.html";
//        } else {
//            return "redirect:/";
//        }
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        userService.logout(); // Logout user
        return ResponseEntity.ok("User logged out successfully.");
    }

//    @Operation(
//            summary = "User logout",
//            description = "Logout user. Always returns success.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
//    @PostMapping("/logout")
//    public String logout(HttpSession session) {
//        userService.logout();
//        session.invalidate();
//        return "redirect:/";
//    }
}
