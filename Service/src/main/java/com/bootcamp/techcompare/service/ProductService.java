package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch all products and search for certain keyword, filter by categories
    public List<Product> searchProducts(String keyword, List<String> categories) {
        String url = "https://fakestoreapi.com/products";
        Product[] products = restTemplate.getForObject(url, Product[].class);

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
        String url = "https://fakestoreapi.com/products/" + id;
        return restTemplate.getForObject(url, Product.class);
    }

    public List<String> fetchCategories() {
        String url = "https://fakestoreapi.com/products/categories";
        String[] categories = restTemplate.getForObject(url, String[].class);
        return categories != null ? Arrays.asList(categories) : new ArrayList<>();
    }

    public void trackProduct(String email, String productName, double productPrice) {

    }
}

