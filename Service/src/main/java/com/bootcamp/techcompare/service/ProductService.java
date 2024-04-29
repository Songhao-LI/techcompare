package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.dao.*;
import com.bootcamp.techcompare.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StoreProductDao storeProductDao;

    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private WishlistItemDao wishlistItemDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch all products and search for certain keyword, filter by categories
    public List<Product> searchProducts(String keyword, List<String> categories) {
//        String url = "https://fakestoreapi.com/products";
        Product[] products = productDao.getAllProducts().toArray(new Product[0]);

        if (products == null) return List.of();

        Stream<Product> productStream = Arrays.stream(products);

        if (keyword != null){
            productStream = productStream.filter(product -> product.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(keyword.toLowerCase()));
        }

        if (categories != null && !categories.isEmpty()) {
            productStream = productStream.filter(product -> categories.contains(product.getCategory().toLowerCase()));
        }

        return productStream.collect(Collectors.toList());
    }

    // Fetches a single product by ID
    public Product getProductById(int id) {
//        String url = "https://fakestoreapi.com/products/" + id;
//        return restTemplate.getForObject(url, Product.class);
        return productDao.getProductById(id);
    }

    public List<String> fetchCategories() {
//        String url = "https://fakestoreapi.com/products/categories";
//        String[] categories = restTemplate.getForObject(url, String[].class);
//        return categories != null ? Arrays.asList(categories) : new ArrayList<>();
        return productDao.getCategories();
    }

    public void addRating(Review review) {
        reviewDao.persist(review);
    }

    public List<ProductReviewResponse> getRatingsByProductId(int productId) {
        List<Review> reviews = reviewDao.getReviewsByProductId(productId);
        return reviews.stream()
                .map(review -> new ProductReviewResponse(review.getUsername(), review.getRate(), review.getComment()))
                .collect(Collectors.toList());
    }

    public List<Store> fetchStores() {
        return storeDao.getAllStores();
    }

    public List<StoreProductResponse> getProductsByStoreId(String storeId) {
//        TODO: Implement this method
        return storeDao.getProductsByStoreId(storeId);
    }

    public void addToCart(CartItem cartItem) {
        cartItemDao.persist(cartItem);
        cartItemDao.merge();
    }

    @Transactional
    public void addToWishlist(WishlistItem wishlistItem) {
        wishlistItemDao.persist(wishlistItem);
    }

    public List<CartItem> getCartItemsByUsername(String username) {
        cartItemDao.merge();
        return cartItemDao.getCartItemsByUsername(username);
    }

    public List<WishlistItem> getWishlistItemsByUsername(String username) {
        return wishlistItemDao.getWishlistItemByUsername(username);
    }

    public void updateCartItem(String username, int productId, int newQuantity) {
        cartItemDao.merge();
        List<CartItem> cartItems = cartItemDao.getCartItemsByUsername(username);
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId() == productId && cartItem.getUsername().equals(username)) {
                cartItem.setQuantity(newQuantity);
                if (newQuantity == 0) {
                    cartItemDao.remove(cartItem);
                } else {
                    cartItemDao.persist(cartItem);
                }
                break;
            }
        }
    }

    public void removeCartItem(String username, int productId) {
        List<CartItem> cartItems = cartItemDao.getCartItemsByUsername(username);
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId() == productId && cartItem.getUsername().equals(username)) {
                cartItemDao.remove(cartItem);}
        }
    }

    public void removeWishlistItem(String username, int productId) {
        List<WishlistItem> wishlistItems = wishlistItemDao.getWishlistItemByUsername(username);
        for (WishlistItem wishlistItem : wishlistItems) {
            if (wishlistItem.getProductId() == productId) {
                wishlistItemDao.remove(wishlistItem);
                break;
            }
        }
    }

    public void placeOrder(PaymentRequest paymentRequest) {
//        TODO: Implement this method
    }

    public List<Store> getStoresByProductId(int productId) {
        return storeProductDao.getStoresByProductId(productId);
    }

    public void clearCartItems(String username) {
        List<CartItem> cartItems = cartItemDao.getCartItemsByUsername(username);
        for (CartItem cartItem : cartItems) {
            cartItemDao.remove(cartItem);
        }
    }
}

