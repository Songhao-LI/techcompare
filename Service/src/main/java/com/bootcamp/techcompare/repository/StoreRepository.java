package com.bootcamp.techcompare.repository;

import com.bootcamp.techcompare.model.Review;
import com.bootcamp.techcompare.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
}