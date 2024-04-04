/*
 * @Abdullah Sallam
 */

package com.matager.app.auth;

import at.orderking.bossApp.user.User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthenticationFacade {
    User getAuthenticatedUser();

    Jwt getJwt();

    String getUuid();
}
