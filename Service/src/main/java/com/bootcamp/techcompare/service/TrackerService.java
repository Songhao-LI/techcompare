package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.dao.TrackerDao;
import com.bootcamp.techcompare.model.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrackerService {

    private final RestTemplate restTemplate;

    @Autowired
    private TrackerDao trackerDao;

    @Transactional
    public void add(Tracker tracker) {
        trackerDao.persist(tracker);
    }

    @Autowired
    public TrackerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Tracker> getAllTracking() {
        return trackerDao.getAllTracking();
    }

    public List<Tracker> getTrackersByUsername(String username) {
        return trackerDao.getTrackersByUsername(username);
    }
}

