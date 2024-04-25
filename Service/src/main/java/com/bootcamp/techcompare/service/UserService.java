package com.bootcamp.techcompare.service;

import com.bootcamp.techcompare.dao.UserDao;
import com.bootcamp.techcompare.model.User;
import com.bootcamp.techcompare.model.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

//    private final RestTemplate restTemplate;

//    @Autowired
//    private CognitoIdentityProviderClient cognitoIdentityProviderClient;

    @Autowired
    private UserDao userDao;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String cognito_client_id;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String cognito_client_secret;

    private final String cognito_user_pool_id = "us-west-1_TtGfsqs3u";

    private final String cognito_user_group = "techcompare_users";

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating ");
        }
    }

    @Transactional
    public void add(User user) {
        userDao.persist(user);
    }

    // User logout
    public void logout() {
//        cognito logout
//    TODO: Implement logout
    }

    public void registerUser(User userRequest) {
        try {
            CognitoIdentityProviderClient cognitoIdentityProviderClient = CognitoIdentityProviderClient.builder()
                    .region(Region.US_WEST_1)
                    .build();

            // Prepare user's attributes
            List<AttributeType> userAttributes = new ArrayList<>();
            userAttributes.add(AttributeType.builder().name("email").value(userRequest.getEmail()).build());
            // Prepare the request
            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(cognito_user_pool_id) // use environment variable
                    .username(userRequest.getUsername())
                    .temporaryPassword(userRequest.getPassword())
                    .userAttributes(userAttributes)
                    .build();

            // Use Cognito API
            cognitoIdentityProviderClient.adminCreateUser(createUserRequest);

            // Set the user's password
            AdminSetUserPasswordRequest adminSetUserPasswordRequest = AdminSetUserPasswordRequest.builder()
                    .userPoolId(cognito_user_pool_id)
                    .username(userRequest.getUsername())
                    .password(userRequest.getPassword())
                    .permanent(true)
                    .build();

            // Use Cognito API
            cognitoIdentityProviderClient.adminSetUserPassword(adminSetUserPasswordRequest);

            // Add the user to a group
            cognitoIdentityProviderClient.adminAddUserToGroup(AdminAddUserToGroupRequest.builder()
                    .groupName(cognito_user_group)
                    .userPoolId(cognito_user_pool_id)
                    .username(userRequest.getUsername())
                    .build());

        } catch (Exception e) {
            // Handle register errors
            throw new RuntimeException("Error registering user : " + e.getMessage(), e);
        }
    }

    public boolean loginUser(UserLoginRequest userRequest) {
        try {
            CognitoIdentityProviderClient cognitoIdentityProviderClient = CognitoIdentityProviderClient.builder()
                    .region(Region.US_WEST_1)
                    .build();

            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .authFlow("ADMIN_USER_PASSWORD_AUTH")
                    .clientId(cognito_client_id)
                    .userPoolId(cognito_user_pool_id)
                    .authParameters(new HashMap<>() {{
                        put("USERNAME", userRequest.getUsername());
                        put("PASSWORD", userRequest.getPassword());
                        put("SECRET_HASH", calculateSecretHash(cognito_client_id, cognito_client_secret, userRequest.getUsername()));
                    }}
                    ).build();

            AdminInitiateAuthResponse authResponse = cognitoIdentityProviderClient.adminInitiateAuth(authRequest);
            if (authResponse.authenticationResult() != null) {
                return true;
            }

        } catch (Exception e) {
            // Handle login errors
            return false;
        }
        return false;
    }
}