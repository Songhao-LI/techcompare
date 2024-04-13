package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            HttpSession session) {
        boolean loginSuccess = userService.login(username, password);
        if (loginSuccess) {
//            set user id in session cookie with base64 username
//            String encodedUsername = java.util.Base64.getEncoder().encodeToString(username.getBytes());
            session.setAttribute("username", username);
            return "redirect:/search.html";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout();
        session.invalidate();
        return "redirect:/";
    }
}
