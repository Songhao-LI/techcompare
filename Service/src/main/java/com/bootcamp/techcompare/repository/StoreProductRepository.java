package com.bootcamp.techcompare.repository;

import com.bootcamp.techcompare.model.CartItem;
import com.bootcamp.techcompare.model.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Integer> {
}
