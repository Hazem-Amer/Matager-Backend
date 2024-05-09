/*
 * @Abdullah Sallam
 */

package com.matager.app.auth;

import com.matager.app.common.exception.auth.NoAuthUserFoundException;
import com.matager.app.user.User;
import com.matager.app.user.UserServiceCacheImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {


    private final UserServiceCacheImpl userService;

    @Override
    public User getAuthenticatedUser() {
        String subject;
        try {
            subject = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject();
        } catch (Exception e) {
            throw new NoAuthUserFoundException();
        }

        String uuid = subject.split("\\.")[0];

        return userService.getUserByUuid(uuid);
    }

    @Override
    public Jwt getJwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getUuid() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject().split("\\.")[0];
    }
}
