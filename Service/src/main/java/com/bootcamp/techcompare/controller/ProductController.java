package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.Product;
import com.bootcamp.techcompare.model.Rating;
import com.bootcamp.techcompare.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categories", required = false) List<String> categories,
            Model model) {
        model.addAttribute("products", productService.searchProducts(keyword, categories));
        model.addAttribute("categories", productService.fetchCategories());
        model.addAttribute("selectedCategories", categories != null ? categories : new ArrayList<>());
        return "result"; // Thymeleaf template name for the results page
    }

    // Endpoint to add a product to the comparison list
    @PostMapping("/add-to-comparison")
    public String addToComparison(@RequestParam(value = "productId") int productId, HttpSession session) {
        Set<Integer> comparisonList = (Set<Integer>) session.getAttribute("comparisonList");
        if (comparisonList == null) {
            comparisonList = new LinkedHashSet<>();
        }

        if (comparisonList.size() < 4) {
            comparisonList.add(productId);
        }

        session.setAttribute("comparisonList", comparisonList);
        return "redirect:/search"; // Redirect back
    }

    // Endpoint to show the comparison page
    @GetMapping("/comparison")
    public String showComparison(Model model, HttpSession session) {
        Set<Integer> comparisonList = (Set<Integer>) session.getAttribute("comparisonList");
        if (comparisonList != null && !comparisonList.isEmpty()) {
            List<Product> productsToCompare = comparisonList.stream()
                    .map(id -> productService.getProductById(id))
                    .collect(Collectors.toList());

            // Identifying highest rating and lowest price
            double highestRating = productsToCompare.stream()
                    .map(Product::getRating)
                    .mapToDouble(Rating::getRate)
                    .max()
                    .orElse(0);

            double lowestPrice = productsToCompare.stream()
                    .mapToDouble(Product::getPrice)
                    .min()
                    .orElse(Double.MAX_VALUE);

            model.addAttribute("productsToCompare", productsToCompare);
            model.addAttribute("highestRating", highestRating);
            model.addAttribute("lowestPrice", lowestPrice);
        } else { //?Code Logic here may need improvement
            model.addAttribute("productsToCompare", new ArrayList<>());
        }

        return "comparison"; // Thymeleaf template name
    }
}
