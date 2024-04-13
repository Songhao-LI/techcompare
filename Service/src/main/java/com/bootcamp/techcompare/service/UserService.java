package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.dao.UserDao;
import com.bootcamp.techcompare.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void add(User user) {
        userDao.persist(user);
    }

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public boolean login(String username, String password) {
//        return username.equals("admin") && password.equals("admin");
//        TODO: Implement login with database
        return userDao.exist(new User(username, password, null));
    }

    public String getEmailByUsername(String username) {
        return userDao.getEmailByUsername(username);
    }

    // User logout
    public void logout() {
        // Do nothing
        // delete user id from session
    }
}

