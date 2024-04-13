package com.bootcamp.techcompare.task;

import com.bootcamp.techcompare.dao.TrackingDao;
import com.bootcamp.techcompare.model.Tracking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TrackingTask {
    private static final Logger log = LoggerFactory.getLogger(TrackingTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    TrackingDao trackingDao;

//    send email every minute (for debugging purpose); change to every 3 hours or more in production
    @Scheduled(fixedRate = 60000)
    public void sendTrackingEmails() {
//        1. get all (email, productId, price) pairs from the database
//        2. for each pair, get the latest price from the external API
//        3. if the price has changed:
//          1. get product name from product ID
//          2. send an email to that user
//          3. update the currentPrice in the database

        List<Tracking> trackings = trackingDao.getAllTracking();

//        TODO: implement the logic to send email to users
        for (Tracking tracking : trackings) {
            log.info(tracking.getEmail() + "is tracking " + tracking.getProductId() + " starting at " + tracking.getCurrentPrice());
        }
    }
}
