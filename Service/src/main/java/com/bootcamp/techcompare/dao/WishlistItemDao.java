package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.CartItem;
import com.bootcamp.techcompare.model.WishlistItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class WishlistItemDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(WishlistItem wishlistItem) {
        em.persist(wishlistItem);
    }

    public List<WishlistItem> getWishlistItemByUsername(String username) {
        return em.createQuery("SELECT w FROM WishlistItem w WHERE w.username = :username")
                .setParameter("username", username)
                .getResultList();
    }

    @Transactional
    public void remove(WishlistItem wishlistItem) {
        em.remove(wishlistItem);
    }
}
