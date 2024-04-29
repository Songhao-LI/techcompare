package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.*;
import com.bootcamp.techcompare.service.ProductService;
import com.bootcamp.techcompare.service.TrackerService;
import com.bootcamp.techcompare.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrackerService trackerService;

    @Operation(
            summary = "Search product with keyword and/or categories.",
            description = "Search product with keyword and/or categories."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))})
//    categories AND/OR keyword
    @GetMapping(value="/products", produces = "application/json")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(value = "categories", required = false) List<String> categories,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<Product> products = productService.searchProducts(keyword, categories);
        return ResponseEntity.ok(products);
    }

@Operation(
            summary = "Get all categories.",
            description = "Get all categories.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class))))})
    @GetMapping(value="/product_categories", produces = "application/json")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(productService.fetchCategories());
    }

    @Operation(
            summary = "Get product by id.",
            description = "Get product by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Product.class)))})
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Product compare with a list of ids.",
            description = "Get products by ids.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))})
    @GetMapping(value="/products", params = {"ids"}, produces = "application/json")
    public ResponseEntity<List<Product>> getProductsByIds(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        List<Product> products = ids.stream()
                .map(productService::getProductById)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Add review and rating for a product.",
            description = "Add review and rating for a product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @PostMapping(value = "/reviews", produces = "application/json")
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        productService.addRating(review);
        return ResponseEntity.ok("Review added successfully.");
    }

    @Operation(
            summary = "Get reviews and ratings for a product.",
            description = "Get reviews and ratings for a product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = ProductReviewResponse.class))) })})
    @GetMapping(value = "/products/{productId}/reviews", produces = "application/json")
    public ResponseEntity<List<ProductReviewResponse>> getReviews(@PathVariable int productId) {
        List<ProductReviewResponse> productReviewResponses = productService.getRatingsByProductId(productId);
        return ResponseEntity.ok(productReviewResponses);
    }

    @Operation(
            summary = "Get stores of a product.",
            description = "Get stores of a product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Store.class))) })})
    @GetMapping(value = "/products/{productId}/stores", produces = "application/json")
    public ResponseEntity<List<Store>> getStoresByProductId(@PathVariable int productId) {
        System.out.println("productId: " + productId);
        List<Store> stores = productService.getStoresByProductId(productId);
        return ResponseEntity.ok(stores);
    }

    @Operation(
            summary = "Track product for a user at a specific price.",
            description = "Track product for a user at a specific price.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @PostMapping(value = "/price-tracker", produces = "application/json")
    public ResponseEntity<String> trackPrice(@RequestBody Tracker tracker) {
        trackerService.add(tracker);
        return ResponseEntity.ok("Price tracker set successfully.");
    }

    @Operation(
            summary = "Get price trackers of a user.",
            description = "Get price trackers of a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = UserTrackerResponse.class)) )})})
    @GetMapping(value = "/price-tracker/{username}", produces = "application/json")
    public ResponseEntity<List<UserTrackerResponse>> getPriceTrackings(@PathVariable String username) {
        List<Tracker> trackers = trackerService.getTrackersByUsername(username);
        // get current price for each product of trackers
        List<UserTrackerResponse> userTrackerResponses = trackers.stream()
                .map(tracker -> {
                    Product product = productService.getProductById(tracker.getProductId());
                    return new UserTrackerResponse(tracker.getProductId(), product.getPrice(), tracker.getTargetPrice());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(userTrackerResponses);
    }

    @Operation(
            summary = "Get all stores.",
            description = "Get all stores.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Store.class)) )})})
    @GetMapping(value = "/stores", produces = "application/json")
    public ResponseEntity<List<Store>> getStores() {
        List<Store> stores = productService.fetchStores();
        return ResponseEntity.ok(stores);
    }

    @Operation(
            summary = "Get products of a store.",
            description = "Get products of a store.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = StoreProductResponse.class))) })})
    @GetMapping(value = "/stores/{storeId}/products", produces = "application/json")
    public ResponseEntity<List<StoreProductResponse>> getProductsByStoreId(@PathVariable String storeId) {
//        TODO: Implement this method
        List<StoreProductResponse> storeProductResponses = productService.getProductsByStoreId(storeId);
        return ResponseEntity.ok(storeProductResponses);
    }

    @Operation(
            summary = "Add product to cart.",
            description = "Add product to cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @PostMapping(value = "/cart-items", produces = "application/json")
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem) {
        productService.addToCart(cartItem);
        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @Operation(
            summary = "Get cart items of a user.",
            description = "Get cart items of a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = CartItem.class))) })})
    @GetMapping(value = "/cart-items", produces = "application/json")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam(value = "username") String username) {
        List<CartItem> cartItems = productService.getCartItemsByUsername(username);
        return ResponseEntity.ok(cartItems);
    }

    @Operation(
            summary = "Update cart item quantity.",
            description = "Update cart item quantity.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @PutMapping(value = "/cart-items/{username}/{productId}", produces = "application/json")
    public ResponseEntity<String> updateCartItem(@PathVariable String username, @PathVariable int productId, @RequestBody int newQuantity) {
        productService.updateCartItem(username, productId, newQuantity);
        return ResponseEntity.ok("Cart item updated successfully.");
    }

    @Operation(
            summary = "Clear cart of a user.",
            description = "Clear cart of a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @PostMapping(value = "/clear-cart/{username}", produces = "application/json")
    public ResponseEntity<String> clearCartItems(@PathVariable String username) {
        productService.clearCartItems(username);
        return ResponseEntity.ok("Cart items updated successfully.");
    }

    @Operation(
            summary = "Remove cart item.",
            description = "Remove cart item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @DeleteMapping(value = "/cart-items/{username}/{productId}", produces = "application/json")
    public ResponseEntity<String> removeCartItem(@PathVariable String username, @PathVariable int productId) {
        productService.removeCartItem(username, productId);
        return ResponseEntity.ok("Cart item removed successfully.");
    }

    @Operation(
            summary = "Add product to wishlist.",
            description = "Add product to wishlist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @PostMapping(value = "/wishlist-items", produces = "application/json")
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistItem wishlistItem) {
//    public ResponseEntity<String> addToWishlist(@RequestParam(value = "username") String username, @RequestParam(value = "productId") int productId) {
        productService.addToWishlist(wishlistItem);
//        productService.addToWishlist(new WishlistItem(username, productId));
        return ResponseEntity.ok("Product added to wishlist successfully.");
    }

    @Operation(
            summary = "Get wishlist items of a user.",
            description = "Get wishlist items of a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = WishlistItem.class))) })})
    @GetMapping(value = "/wishlist-items", produces = "application/json")
    public ResponseEntity<List<WishlistItem>> getWishlistItems(@RequestParam(value = "username") String username) {
        List<WishlistItem> wishlistItems = productService.getWishlistItemsByUsername(username);
        return ResponseEntity.ok(wishlistItems);
    }

    @Operation(
            summary = "Remove wishlist item.",
            description = "Remove wishlist item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) })})
    @DeleteMapping(value = "/wishlist-items/{username}/{productId}", produces = "application/json")
    public ResponseEntity<String> removeWishlistItem(@PathVariable String username, @PathVariable int productId) {
        productService.removeWishlistItem(username, productId);
        return ResponseEntity.ok("Wishlist item removed successfully.");
    }
}
