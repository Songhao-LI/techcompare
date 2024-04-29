package com.bootcamp.techcompare.controller;

import com.bootcamp.techcompare.model.CartItem;
import com.bootcamp.techcompare.model.Order;
import com.bootcamp.techcompare.model.PaymentRequest;
import com.bootcamp.techcompare.model.Product;
import com.bootcamp.techcompare.service.ProductService;
import com.bootcamp.techcompare.utils.CustomerUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.checkout.SessionCreateParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

//stripe session object
import com.stripe.model.checkout.Session;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private ProductService productService;

    @Value("${CLIENT_BASE_URL}")
    String CLIENT_BASE_URL;

    @Value("${STRIPE_API_KEY}")
    String STRIPE_API_KEY;

    @Operation(
            summary = "Checkout from the cart.",
            description = "Checkout from the cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checkout successful.",
                    content = @Content)})
    @PostMapping("/checkout")
    public String hostedCheckout(@RequestBody PaymentRequest paymentRequest) throws StripeException {
//    public String hostedCheckout(@RequestParam("username") String username, @RequestParam("userEmail") String userEmail) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        // Start by finding an existing customer record from Stripe or creating a new one if needed
        Customer customer = CustomerUtil.findOrCreateCustomer(paymentRequest.getUserEmail(), paymentRequest.getUsername());

        // Next, create a checkout session by adding the details of the checkout
        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customer.getId())
                        .setSuccessUrl(CLIENT_BASE_URL + "/payment_success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(CLIENT_BASE_URL + "/payment_failure");

        // Add Order details
        for (CartItem cartItem : productService.getCartItemsByUsername(paymentRequest.getUsername())) {
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) cartItem.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .putMetadata("app_id", String.valueOf(cartItem.getProductId()))
                                                            .setName(productService.getProductById(cartItem.getProductId()).getTitle())
                                                            .build()
                                            )
                                            .setCurrency("USD")
                                            .setUnitAmountDecimal(BigDecimal.valueOf((int) (productService.getProductById(cartItem.getProductId()).getPrice() / 0.01)))
                                            .build())
                            .build());
        }

        Session session = Session.create(paramsBuilder.build());

        return session.getUrl();
    }

    @Operation(
            summary = "TEST Checkout from the cart.",
            description = "TEST Checkout from the cart.")
    @GetMapping("/test_checkout")
    public String testCheckout() throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        String username = "helinyi";
        String userEmail = "lh1505@nyu.edu";

        // Start by finding an existing customer record from Stripe or creating a new one if needed
        Customer customer = CustomerUtil.findOrCreateCustomer(userEmail, username);

        // Next, create a checkout session by adding the details of the checkout
        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customer.getId())
                        .setSuccessUrl(CLIENT_BASE_URL + "/payment_success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(CLIENT_BASE_URL + "/payment_failure");

        // Add Order details
        for (CartItem cartItem : productService.getCartItemsByUsername(username)) {
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) cartItem.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .putMetadata("app_id", String.valueOf(cartItem.getProductId()))
                                                            .setName(productService.getProductById(cartItem.getProductId()).getTitle())
                                                            .build()
                                            )
                                            .setCurrency("USD")
                                            .setUnitAmountDecimal(BigDecimal.valueOf((int) (productService.getProductById(cartItem.getProductId()).getPrice() / 0.01)))
                                            .build())
                            .build());
        }

        Session session = Session.create(paramsBuilder.build());

        System.out.println(session.getUrl());

        return session.getUrl();
    }

    @GetMapping("/payment_success")
    public void paymentSuccess(@RequestParam String session_id, HttpServletResponse response) throws IOException {
        System.out.println("Payment successful. Session ID: " + session_id);

        // TODO clear user's cart

        response.sendRedirect("/");
    }

    @GetMapping("/payment_failure")
    public void paymentFailure(HttpServletResponse response) throws IOException {
        System.out.println("Payment failed.");
        response.sendRedirect("/");
    }
}