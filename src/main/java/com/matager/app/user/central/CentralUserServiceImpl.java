/*
 * @Abdullah Sallam
 */

package com.matager.app.user.central;

import com.matager.app.owner.Owner;
import com.matager.app.user.User;
import com.matager.app.owner.OwnerRepository;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.UserRepository;
import com.matager.app.user.UserRole;
import com.matager.app.user.model.NewUserModel;
import com.matager.app.user.model.SigninModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CentralUserServiceImpl implements CentralUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    // called from central
    @Override
    public User signNewUser(NewUserModel newUser) {

        System.out.println("New User: " + newUser);

        Owner owner = ownerRepository.findByUuid(newUser.getOwnerUuid()).orElseThrow(() -> new IllegalStateException("Owner not found."));

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalStateException("Email already used.");
        }

        User user = new User();
        user.setOwner(owner);
        user.setUuid(newUser.getUserUuid());
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (newUser.getRole().equals(UserRole.SERVER_ADMIN)) {
            throw new RuntimeException("Invalid user role.");
        }

        if (newUser.getRole() == null) {
            user.setRole(UserRole.UNDEFINED);
        } else {
            user.setRole(newUser.getRole());
        }

        if (newUser.getDefaultStoreUuid() != null) {
            user.setDefaultStore(storeRepository.findByOwnerIdAndUuid(owner.getId(), newUser.getDefaultStoreUuid()).orElseThrow(() -> new IllegalStateException("Store not found.")));
        }

        user.setProfileImageUrl("Default Profile Image Url");

        return userRepository.save(user);
    }

    // called from central
    @Override
    public User signinUser(SigninModel form) {

        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(() -> new IllegalStateException("Email not found: " + form.getEmail()));

        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) return user;

        throw new IllegalStateException("Wrong Email or password.");
    }

}
