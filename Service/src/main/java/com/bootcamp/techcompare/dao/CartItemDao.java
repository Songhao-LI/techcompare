package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartItemDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(CartItem cartItem) {
        // If the cart item already exists, update the quantity
        for (CartItem item : getCartItemsByUsername(cartItem.getUsername())) {
            if (item.getProductId() == cartItem.getProductId()) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                em.merge(item);
                return;
            }
        }
        em.persist(cartItem);
    }

    public List<CartItem> getCartItemsByUsername(String username) {
        return em.createQuery("SELECT c FROM CartItem c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList();
    }

    @Transactional
    public void remove(CartItem cartItem) {
        em.remove(cartItem);
    }

    @Transactional
    public void merge(CartItem cartItem) {
        em.merge(cartItem);
    }

    @Transactional
    public void removeDuplicates() {
        // different cartitems may have same username and same productId, we need to merge their quantities and only keep one record
        // Retrieve all cart items
        List<CartItem> cartItems = em.createQuery("SELECT c FROM CartItem c", CartItem.class)
                .getResultList();

        // Group cart items by username and productId
        Map<Pair<String, Integer>, List<CartItem>> groupedCartItems = cartItems.stream()
                .collect(Collectors.groupingBy(c -> Pair.of(c.getUsername(), c.getProductId())));

        // For each group, sum up the quantities and create a new cart item
        List<CartItem> newCartItems = new ArrayList<>();
        for (Map.Entry<Pair<String, Integer>, List<CartItem>> entry : groupedCartItems.entrySet()) {
            int totalQuantity = entry.getValue().stream().mapToInt(CartItem::getQuantity).sum();
            CartItem newCartItem = new CartItem();
            newCartItem.setUsername(entry.getKey().getFirst());
            newCartItem.setProductId(entry.getKey().getSecond());
            newCartItem.setQuantity(totalQuantity);
            newCartItems.add(newCartItem);
        }

        // Remove old cart items
        for (CartItem cartItem : cartItems) {
            em.remove(cartItem);
        }

        // Persist new cart items
        for (CartItem newCartItem : newCartItems) {
            em.persist(newCartItem);
        }

    }
}
