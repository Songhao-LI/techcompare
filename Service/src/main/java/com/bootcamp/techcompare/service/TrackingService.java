package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.dao.TrackingDao;
import com.bootcamp.techcompare.model.Tracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrackingService {

    private final RestTemplate restTemplate;

    @Autowired
    private TrackingDao trackingDao;

    @Transactional
    public void add(Tracking tracking) {
        trackingDao.persist(tracking);
    }

    @Autowired
    public TrackingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Tracking> getAllTracking() {
        return trackingDao.getAllTracking();
    }
}

