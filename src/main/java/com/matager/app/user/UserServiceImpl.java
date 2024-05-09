package com.matager.app.user;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.rest.RestHelper;
import com.matager.app.owner.Owner;
import com.matager.app.owner.OwnerRepository;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.model.NewUserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class UserServiceImpl implements UserService {

    private final AuthenticationFacade authenticationFacade;
    private final RestHelper restHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    @Value("${central.server-url}")
    private String centralServerUrl;

    @Value("${central.username}")
    private String username;

    @Value("${central.password}")
    private String password;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByOwner(Long ownerId) {
        return userRepository.findAllByOwnerId(ownerId);
    }


    // Just admin can create new user
    @Override
    public void addNewUser(NewUserModel newUser) {
        User signedUser = authenticationFacade.getAuthenticatedUser();
        Owner owner = signedUser.getOwner();

        // Check if user with same email exists
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalStateException("User with same email already exists.");
        }

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Child server");
        String authHeader = restHelper.getBasicAuthHeader(username, password);
        headers.add("Authorization", authHeader);


        newUser.setOwnerUuid(owner.getUuid());
        HttpEntity<NewUserModel> createUserEntity = new HttpEntity<>(newUser, headers);

        log.info("Sending request to: " + centralServerUrl + "/v1/create_user");
        log.info("Request body: " + createUserEntity.toString());

        try {
            ResponseEntity<String> response = restTemplate.exchange(centralServerUrl + "/v1/create_user", HttpMethod.POST, createUserEntity, String.class);
            log.info(response.toString());

        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User getPOSUser(String storeUuid) {
        User signedUser = authenticationFacade.getAuthenticatedUser();
        Owner owner = signedUser.getOwner();

        Store store = storeRepository.findByOwnerIdAndUuid(signedUser.getOwner().getId(), storeUuid).orElseThrow(() -> new IllegalStateException("Store not found."));

        Optional<User> posUser = userRepository.findByOwnerIdAndDefaultStoreUuidAndAndRole(owner.getId(), store.getUuid(), UserRole.POS);

        // Create POS user if not created
        if (posUser.isEmpty()) {
            addNewUser(
                    NewUserModel.builder()
                            .name(store.getName() + " POS")
                            .email(store.getUuid() + "store@orderking.fakedmoain.com")
                            .password("s" + store.getUuid().substring(0, 11) + "S0084@#")
                            .role(UserRole.POS)
                            .defaultStoreUuid(store.getUuid())
                            .build()
            );
            return getPOSUser(storeUuid);
        }

        return posUser.get();
    }

    @Override
    @Transactional
    public User deleteUser(String uuid) {
//        User signedUser = authenticationFacade.getAuthenticatedUser();
//
//        User userToDelete = getUserByUuid(uuid);
//
//        if (userToDelete.getOwner().equals(signedUser.getOwner())) {
//            userRepository.deleteByUuid(uuid);
//            // Not working, need to fix
//
//            return userToDelete;
//        }
//        throw new IllegalStateException("User not found."); // Other owner's user found, not allowed.
//
        throw new IllegalStateException("Not implemented yet.");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // Not implemented yet
    }

    @Override
    public void changeRole(String email, UserRole role) {
        User signedUser = authenticationFacade.getAuthenticatedUser();

        if (role != null) {
            User user = getUserByEmail(email);
            user.setRole(role);
            if (user.getOwner().equals(signedUser.getOwner())) {
                userRepository.save(user);
                return;
            }
            throw new IllegalStateException("User not found."); // Other owner's user found, not allowed.
        }
        throw new IllegalStateException("Role can not be null.");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User not found."));
    }

    @Override
    public User getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new IllegalStateException("User not found."));
    }
}
