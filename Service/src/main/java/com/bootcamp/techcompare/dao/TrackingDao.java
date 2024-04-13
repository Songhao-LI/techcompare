package com.bootcamp.techcompare.dao;

import com.bootcamp.techcompare.model.Tracking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackingDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Tracking tracking) {
        em.persist(tracking);
    }

    public List<Tracking> getAllTracking() {
        return em.createQuery("SELECT t FROM Tracking t").getResultList();
    }
}
