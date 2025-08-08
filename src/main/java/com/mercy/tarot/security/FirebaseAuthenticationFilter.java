package com.mercy.tarot.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mercy.tarot.models.User;
import com.mercy.tarot.service.FirebaseAuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthenticationFilter.class);

    private final FirebaseAuthService firebaseAuthService;
    private final List<String> publicEndpoints = Arrays.asList(
            "/api/auth/",
            "/api/public/",
            "/api/cards/daily",
            "/actuator/health");

    public FirebaseAuthenticationFilter(FirebaseAuthService firebaseAuthService) {
        this.firebaseAuthService = firebaseAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        log.debug("Processing {} request to: {}", method, requestURI);

        // Skip authentication for public endpoints and OPTIONS requests
        if (isPublicEndpoint(requestURI)) {
            log.debug("Skipping authentication for public endpoint: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // Skip authentication if already authenticated (e.g., by
        // JwtAuthenticationFilter)
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.debug("Already authenticated, skipping Firebase authentication");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractTokenFromRequest(request);

            if (token == null || token.isEmpty()) {
                log.warn("No authentication token provided for protected endpoint: {}", requestURI);
                sendUnauthorizedResponse(response, "Authentication token required");
                return;
            }

            User user = firebaseAuthService.verifyTokenAndGetUser(token);

            // Create Spring Security authentication with roles
            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .toList();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Successfully authenticated user: {} with roles: {}", user.getEmail(), authorities);

        } catch (Exception e) {
            log.error("Authentication failed for request {} {}: {}", method, requestURI, e.getMessage());
            SecurityContextHolder.clearContext();
            sendUnauthorizedResponse(response, "Authentication failed: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return request.getParameter("token"); // Fallback to query parameter
    }

    private boolean isPublicEndpoint(String requestURI) {
        return publicEndpoints.stream().anyMatch(requestURI::startsWith) ||
                requestURI.equals("/") ||
                requestURI.equals("/favicon.ico");
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.format(
                "{\"error\":\"unauthorized\",\"message\":\"%s\",\"timestamp\":\"%s\"}",
                message,
                java.time.Instant.now().toString()));
    }
}