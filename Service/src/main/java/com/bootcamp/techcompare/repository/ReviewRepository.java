package com.bootcamp.techcompare.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bootcamp.techcompare.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}