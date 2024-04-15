package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.CartItem;
import com.bootcamp.techcompare.model.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartItemDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(CartItem cartItem) {
        em.persist(cartItem);
    }

    public List<CartItem> getCartItemsByUserId(String userId) {
        return em.createQuery("SELECT c FROM CartItem c WHERE c.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public void remove(CartItem cartItem) {
        em.remove(cartItem);
    }
}
