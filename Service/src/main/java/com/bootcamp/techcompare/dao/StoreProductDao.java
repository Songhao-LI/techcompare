package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.Store;
import com.bootcamp.techcompare.model.StoreProduct;
import com.bootcamp.techcompare.model.StoreProductResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StoreProductDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(StoreProduct storeProduct) {
        em.persist(storeProduct);
    }

    public List<Store> getStoresByProductId(int productId) {
        return em.createQuery("SELECT s FROM Store s JOIN StoreProduct sp ON s.id = sp.storeId WHERE sp.productId = :productId", Store.class)
                .setParameter("productId", productId)
                .getResultList();
    }
}
