package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(User user) {
        em.persist(user);
    }

    public boolean exist(User user) {
//        find if the user exists in the database
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword())
                .getResultList().size() > 0;
    }

    public String getEmailByUsername(String username) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.username = :username")
                .setParameter("username", username)
                .getResultList();

        if (users.size() > 0) {
            return users.get(0).getEmail();
        } else {
            return null;
        }
    }
}
