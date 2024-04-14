package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.Product;
import com.bootcamp.techcompare.model.Rating;
import com.bootcamp.techcompare.model.Tracking;
import com.bootcamp.techcompare.service.ProductService;
import com.bootcamp.techcompare.service.TrackingService;
import com.bootcamp.techcompare.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrackingService trackingService;

    @Operation(
            summary = "Search product with keyworkd and categories.",
            description = "Search product with keyworkd and categories.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
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

    @Operation(
            summary = "Add product to comparison list",
            description = "Add product to comparison list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
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

    @Operation(
            summary = "Compare all products in the comparison list",
            description = "Compare all products in the comparison list.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
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

    @Operation(
            summary = "Track product with productId.",
            description = "Track product with productId.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
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
