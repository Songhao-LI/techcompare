package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.Tracker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackerDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Tracker tracker) {
        em.persist(tracker);
    }

    public List<Tracker> getAllTracking() {
        return em.createQuery("SELECT t FROM Tracker t").getResultList();
    }

    public List<Tracker> getTrackersByUserId(String userId) {
        return em.createQuery("SELECT t FROM Tracker t WHERE t.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }
}
