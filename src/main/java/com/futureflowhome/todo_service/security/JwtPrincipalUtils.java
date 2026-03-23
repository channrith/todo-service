package com.futureflowhome.todo_service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

/**
 * Resolves the authenticated user's ID from the JWT.
 * Uses "sub" claim as userId (standard OIDC subject). If your user-service puts
 * the user ID in a different claim (e.g. "userId"), add a fallback here.
 */
public final class JwtPrincipalUtils {

    private static final String CLAIM_USER_ID = "userId";

    private JwtPrincipalUtils() {
    }

    public static UUID getUserId(Authentication authentication) {
        if (authentication == null || !(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return null;
        }
        Jwt jwt = jwtAuth.getToken();
        // Prefer explicit userId claim if present
        String userIdStr = jwt.getClaimAsString(CLAIM_USER_ID);
        if (userIdStr != null && !userIdStr.isBlank()) {
            return UUID.fromString(userIdStr);
        }
        // Fallback: sub is often the user id in OIDC
        String sub = jwt.getSubject();
        if (sub == null || sub.isBlank()) {
            return null;
        }
        return UUID.fromString(sub);
    }
}
