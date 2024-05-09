/*
 * @Abdullah Sallam
 */

package com.matager.app.user.central;

import com.matager.app.user.User;
import com.matager.app.user.model.NewUserModel;
import com.matager.app.user.model.SigninModel;

public interface CentralUserService {
    User signNewUser(NewUserModel newUser);

    User signinUser(SigninModel form);

}
