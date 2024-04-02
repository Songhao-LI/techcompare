package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.Product;
import com.bootcamp.techcompare.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String searchProducts(@RequestParam(value = "keyword") String keyword, @RequestParam(value = "categories", required = false) List<String> categories, Model model) {
        model.addAttribute("products", productService.searchProducts(keyword, categories));
        model.addAttribute("categories", productService.fetchCategories());
        model.addAttribute("selectedCategories", categories != null ? categories : new ArrayList<>());
        return "result"; // Thymeleaf template name for the results page
    }
}
