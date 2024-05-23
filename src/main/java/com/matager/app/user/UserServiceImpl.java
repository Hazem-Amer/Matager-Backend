package com.matager.app.user;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.rest.RestHelper;
import com.matager.app.owner.Owner;
import com.matager.app.owner.OwnerRepository;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.model.NewUserModel;
import com.matager.app.user.model.SigninModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByOwner(Long ownerId) {
        return userRepository.findAllByOwnerId(ownerId);
    }


    @Override
    public User signinUser(SigninModel form) {

        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(() -> new IllegalStateException("Email not found: " + form.getEmail()));

        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) return user;

        throw new IllegalStateException("Wrong Email or password.");
    }


    @Override
    public User addNewUser(NewUserModel newUser) {

        System.out.println("New User: " + newUser);

        Owner owner = ownerRepository.findByUuid(newUser.getOwnerUuid()).orElseThrow(() -> new IllegalStateException("Owner not found."));

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalStateException("Email already used.");
        }

        User user = new User();
        user.setOwner(owner);
        user.setUuid(UUID.randomUUID().toString());
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));


        if (newUser.getRole() == null) {
            user.setRole(UserRole.UNDEFINED);
        } else {
            if (newUser.getRole().equals(UserRole.SERVER_ADMIN)) {
                throw new RuntimeException("Invalid user role.");
            }
            user.setRole(newUser.getRole());
        }

        if (newUser.getStoreId() != null) {
            user.setDefaultStore(storeRepository.findByOwnerIdAndId(owner.getId(), newUser.getStoreId()).orElseThrow(() -> new IllegalStateException("Store not found.")));
        }

        user.setProfileImageUrl("Default Profile Image Url");

        return userRepository.save(user);

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
