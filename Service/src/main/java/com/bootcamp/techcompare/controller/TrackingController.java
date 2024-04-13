package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.Product;
import com.bootcamp.techcompare.model.Tracking;
import com.bootcamp.techcompare.service.ProductService;
import com.bootcamp.techcompare.service.TrackingService;
import com.bootcamp.techcompare.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrackingController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrackingService trackingService;

    @PostMapping("/track")
    public String track(@RequestParam(value = "productId") int productId, HttpSession session) {
//        1. get username from user session and retrieve his email based on that
        String username = (String) session.getAttribute("username");
        String email = userService.getEmailByUsername(username);
//        2. get product name from productId
        Product product = productService.getProductById(productId);
//        3. get current product price
        double currentPrice = product.getPrice();
//        4. save (email, product_id, product_price) in trackings table
        trackingService.add(new Tracking(email, productId, currentPrice));
//        5. in the future when scheduled tasks is running, only one table will be
//           accessed to send email to users
        return "redirect:/search"; // Redirect back
    }
}
