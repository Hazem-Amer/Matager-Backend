/*
 * @Abdullah Sallam
 */

package com.matager.app.token;

import com.matager.app.user.User;
import com.matager.app.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final UserService userService;

    public String generateToken(String uuid, String roles, Instant expiresAt) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(uuid)
                .claim("roles", roles)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateEndlessToken(String uuid, String roles) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(uuid)
                .claim("roles", roles)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateEndlessToken(User user) {
        return generateEndlessToken(user.getUuid(), String.valueOf(user.getRole()));
    }

    public String generateToken(String uuid, String roles) {
        return generateToken(uuid, roles, Instant.now().plus(7, ChronoUnit.DAYS));
    }

    public String generateToken(User user) {
        return generateToken(user.getUuid(), String.valueOf(user.getRole()));
    }

    public String generateToken(User user, Instant expiresAt) {
        return generateToken(user.getUuid(), String.valueOf(user.getRole()), expiresAt);
    }

    public Authentication getAuthentication(String token) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        return converter.convert(decoder.decode(token));
    }

    public String getUUID(Jwt jwt) {
        return jwt.getSubject();
    }

    public User getUser(Jwt jwt) {
        return userService.getUserByUuid(getUUID(jwt));
    }


}
