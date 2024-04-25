package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.Store;
import com.bootcamp.techcompare.model.StoreProductResponse;
import com.bootcamp.techcompare.model.Tracker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Store store) {
        em.persist(store);
    }

    public List<Store> getAllStores() {
        return em.createQuery("SELECT s FROM Store s").getResultList();
    }

    public List<StoreProductResponse> getProductsByStoreId(String storeId) {
//        TODO: implement this method; return fake data for now
        return List.of(
                new StoreProductResponse(1, "In Stock"),
                new StoreProductResponse(2, "Out of Stock")
        );

    }
}
