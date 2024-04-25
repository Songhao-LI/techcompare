# Tech Compare

# Project Introduction

Project Name: TechCompare

Objective: Develop a web application that allows users to search, compare, and review tech products using data from the Best Buy API.

API: https://developer.bestbuy.com/ | https://fakestoreapi.com/

Suggested Key Features:

* Product Search and Filters: Functionalities for users to search products, with filters based on categories, brands, prices, etc.
* Product Comparison: Feature to compare multiple products based on specifications and prices.
* User Reviews and Ratings: Registered users can rate and review products. Set up user authentication using Firebase.
* Price Tracking: Functionality to track and alert users for price changes on desired products.
* Store Locator: Feature to find Best Buy stores and check product availability.
* E-commerce Integration: Integrate Stripe API for a simulated checkout experience.
* Wishlist: Allow users to create and manage a wishlist of products.

Suggested Timeline:

* Week 1: Setup
  * Task 0: Team formation (4-5 students)
  * Task 1: Explore Best Buy API 
* Week 2: Initial Development
  * Task 2: Set Up Development Environment
  * Task 3: Design Database Schema and Set Up Backend Framework
* Week 3-4: Backend Development
  * Task 4: Implement Product Search and Filters Backend
* Week 5: Mid-Checkpoint Presentation
  * Present the backend development progress, including product comparison and user review implementation strategy.
* Week 6: Frontend Development and Integration
  * Task 5: Develop Frontend Structure and Implement Product Search
  * Task 6: Integrate Firebase and Implement Product Comparison
* Week 7: Finalizing Features and Testing
  * Task 7: Implement Price Tracking and Store Locator
  * Task 8: Testing and Debugging
* Week 8: Deployment and Final Presentation Preparation
  * Task 9: Deployment
  * Task 10: Final Testing and Presentation Preparation
* Week 9: Final Presentation

# Build

## Docker (both frontend and backend)

`docker build -t techcompare .`

`docker run -p 3000:3000 techcompare`

access frontend: `http://localhost:3000`

access backend: `http://localhost:3000/api/`

access SpringDoc: `http://localhost:3000/swagger-ui/index.html`

## Frontend

install: `npm install`

local run: `npm run dev`

build: `npm run build`

## Backend

build: `./gradlew build`

run: `./gradlew bootRun`

## Prod instance (Google Cloud Run)

https://techcompare-obufj7hhhq-uc.a.run.app

All commits are automatically deployed to the dev instance.