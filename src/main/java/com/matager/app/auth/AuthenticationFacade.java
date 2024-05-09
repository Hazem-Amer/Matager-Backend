/*
 * @Abdullah Sallam
 */

package com.matager.app.auth;

import com.matager.app.user.User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthenticationFacade {
    User getAuthenticatedUser();

    Jwt getJwt();

    String getUuid();
}
