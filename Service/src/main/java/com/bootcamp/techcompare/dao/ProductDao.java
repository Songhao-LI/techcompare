package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.Product;
import com.bootcamp.techcompare.model.Store;
import com.bootcamp.techcompare.model.StoreProductResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Product product) {
        em.persist(product);
    }

    public List<Product> getAllProducts() {
        return em.createQuery("SELECT p FROM Product p").getResultList();
    }

    public Product getProductById(int id) {
        return em.find(Product.class, id);
    }

    public List<String> getCategories() {
        return em.createQuery("SELECT DISTINCT p.category FROM Product p").getResultList();
    }
}
