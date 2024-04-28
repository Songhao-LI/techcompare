package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.CartItem;
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

    public List<CartItem> getCartItemsByUsername(String username) {
        return em.createQuery("SELECT c FROM CartItem c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList();
    }

    public void remove(CartItem cartItem) {
        em.remove(cartItem);
    }
}
