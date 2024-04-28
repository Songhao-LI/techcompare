package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ReviewDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Review review) {
        em.persist(review);
    }

    public List<Review> getReviewsByProductId(int productId) {
        return em.createQuery("SELECT r FROM Review r WHERE r.productId = :productId")
                .setParameter("productId", productId)
                .getResultList();
    }
}
