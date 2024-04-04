/*
 * @Abdullah Sallam
 */

package com.matager.app.user.central;

import at.orderking.bossApp.helper.req_model.user.NewUserModel;
import at.orderking.bossApp.helper.req_model.user.SigninModel;
import at.orderking.bossApp.user.User;

public interface CentralUserService {
    User signNewUser(NewUserModel newUser);

    User signinUser(SigninModel form);

}
