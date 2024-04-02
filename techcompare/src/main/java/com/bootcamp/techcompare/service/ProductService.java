package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> searchProducts(String keyword) {
        String url = "https://fakestoreapi.com/products";
        Product[] products = restTemplate.getForObject(url, Product[].class);
        System.out.println(products[0].getTitle());

        if (products == null) return List.of();

        return Arrays.stream(products)
                .filter(product -> product.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        product.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                        product.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}

