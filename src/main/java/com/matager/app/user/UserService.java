package com.matager.app.user;

import at.orderking.bossApp.helper.req_model.user.NewUserModel;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    List<User> getUsersByOwner(Long ownerId);

    void addNewUser(NewUserModel newUser);

    User getPOSUser(String storeUuid);

    User deleteUser(String uuid);

    void changePassword(String oldPassword, String newPassword);

    void changeRole(String email, UserRole role);

    User getUserByEmail(String email);

    User getUserByUuid(String uuid);
}
