package com.bootcamp.techcompare;

import com.bootcamp.techcompare.model.CartItem;
import com.bootcamp.techcompare.model.Review;
import com.bootcamp.techcompare.model.Store;
import com.bootcamp.techcompare.repository.CartItemRepository;
import com.bootcamp.techcompare.repository.ReviewRepository;
import com.bootcamp.techcompare.repository.StoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechcompareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechcompareApplication.class, args);
	}

//	function to generate a random number from 1 to 20 (inclusive)
	private static int generateRandomNumber() {
		return (int) (Math.random() * 20 + 1);
	}

//	function to generate a random double from 0 to 5.00 (inclusive)
	private static double generateRandomDouble() {
		return Math.random() * 5.0;
	}

	private static String generateRandomStringForComment() {
		String[] words = {"good", "bad", "average", "excellent", "poor", "awesome", "terrible", "mediocre", "satisfactory", "unsatisfactory"};
		return words[(int) (Math.random() * words.length)];
	}

	private static String generateRandomStoreAddress() {
		String[] words = {"1245 Maple Avenue, Springfield, IL 62704", "783 River Road, Boulder, CO 80302", "9503 Jefferson Blvd, Richmond, VA 23231", "338 Garden Grove Rd, Orlando, FL 32803", "1122 Main St, Salem, OR 97301", "2071 Market Street, San Francisco, CA 94114", "2552 Walnut Hill Lane, Dallas, TX 75229", "477 Marine Way, Long Beach, CA 90802", "628 East 3rd Avenue, Spokane, WA 99202", "1445 New Horizon Blvd, Atlanta, GA 30349"};
		return words[(int) (Math.random() * words.length)];
	}

	// Use this to generate random data for the database on EVERY application startup
	@Bean
	CommandLineRunner initDatabase(ReviewRepository reviewRepository, StoreRepository storeRepository, CartItemRepository cartItemRepository) {
		return args -> {
			for (int i = 0; i < 1; i++) {
				reviewRepository.save(new Review("helinyi", generateRandomNumber(), generateRandomDouble(), generateRandomStringForComment()));
				storeRepository.save(new Store(generateRandomStoreAddress()));
				cartItemRepository.save(new CartItem("helinyi", generateRandomNumber(), generateRandomNumber()));
			}
		};
	}
}
